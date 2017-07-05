package com.pallette.solr.search.products.impl;

import com.pallette.solr.products.entities.Property;
import com.pallette.solr.products.entities.PropertyValue;
import com.pallette.browse.documents.ProductDocument;
import com.pallette.browse.repository.ProductRepository;
import com.pallette.solr.products.search.products.PropertyFacetResult;
import com.pallette.solr.products.search.products.PropertyValueFacetResult;
import com.pallette.solr.products.search.products.QueryOptions;
import com.pallette.solr.products.search.products.Result;
import com.pallette.solr.products.search.products.SearchService;
import com.pallette.solr.products.search.products.SortOrder;
import com.pallette.solr.document.ProductSolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.FacetOptions;
import org.springframework.data.solr.core.query.FacetQuery;
import org.springframework.data.solr.core.query.SimpleFacetQuery;
import org.springframework.data.solr.core.query.SimpleFilterQuery;
import org.springframework.data.solr.core.query.SimpleStringCriteria;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Roshan.
 */
@Service
public class SearchServiceImpl implements SearchService {

    private static final String FIELD_ID = "id";

    private static final String FIELD_NAME = "name";

    private static final String FIELD_PRICE = "price";

    private static final String FIELD_DESCRIPTION = "description";

    private static final String FIELD_DYNAMIC_PROPERTY_PATTERN = "prop_%d";

    private static final Float EXACT_BOOST = 100f;

    private static final Float DESCRIPTION_CONTAINS_BOOST = 10f;

    private static final Float WILDCARD_BOOST = 1f;

    private static final Float FUZZY_BOOST = 0.1f;

    private static final Float FUZZY_DISTANCE = 0.2f;

    private static final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

    @Autowired
    private SolrOperations productsTemplate;

    @Autowired
    private ProductRepository productRepository;
    
    private SortOrder[] awailableSortOrders = new SortOrder[] {
        new SortOrder("default", DESC, "score"),
        new SortOrder(FIELD_NAME + " " + ASC.toString(), ASC, FIELD_NAME),
        new SortOrder(FIELD_PRICE + " " + ASC.toString(), ASC, FIELD_PRICE),
        new SortOrder(FIELD_PRICE + " " + DESC.toString(), DESC, FIELD_PRICE),
    };

    public Result search(QueryOptions queryOptions, Pageable pageable) {
        final FacetQuery facetQuery = new SimpleFacetQuery(buildSearchCriteria(queryOptions), pageable);

        final Long priceMin = queryOptions.getMinPrice();
        final Long priceMax = queryOptions.getMaxPrice();

        if (priceMin != null) {
            facetQuery.addFilterQuery(new SimpleFilterQuery(new Criteria(FIELD_PRICE).greaterThanEqual(priceMin)));
        }

        if (priceMax != null) {
            facetQuery.addFilterQuery(new SimpleFilterQuery(new Criteria(FIELD_PRICE).lessThanEqual(priceMax)));
        }

        final Map<Long, Set<Long>> filterValues = queryOptions.getFilterValues();

        filterValues.forEach((propertyId, values) -> {
            facetQuery.addFilterQuery(new SimpleFilterQuery(buildFilterValuesCriteria(propertyId, values)));
        });

        final FacetOptions facetOptions = new FacetOptions();

        facetOptions.setFacetMinCount(0);

        final List<Property> properties = new ArrayList<Property>();
//        properties.add(new Property(1, "category"));
//        properties.add(new Property(2, "brand"));
//        properties.add(new Property(3, "price"));
//        properties.add(new Property(4, "size"));
        
        properties.forEach(property -> {
            String fieldName = String.format(FIELD_DYNAMIC_PROPERTY_PATTERN, property.getId());

            if (filterValues.containsKey(property.getId())) {
                fieldName = String.format("{!ex=%1$s}%1$s", fieldName);
            }

            facetOptions.addFacetOnField(fieldName);
        });
//        String fieldName = String.format(FIELD_DYNAMIC_PROPERTY_PATTERN, property.getId());
        facetOptions.addFacetOnField("product_category");
        facetOptions.addFacetOnField("product_brand");
        facetQuery.setFacetOptions(facetOptions);

        final FacetPage<ProductSolrDocument> facetPage = productsTemplate.queryForFacetPage(facetQuery, ProductSolrDocument.class);

        
        final List<ProductDocument> products = (List<ProductDocument>) productRepository.findAll(
        			facetPage.getContent().stream().map(ProductSolrDocument::getId).collect(Collectors.toList())
        		);

        Set<PropertyFacetResult> facets = new LinkedHashSet<>();

        properties.forEach(property -> {
            final Boolean propertySelected = filterValues.containsKey(property.getId());

            final PropertyFacetResult propertyFacetResult = new PropertyFacetResult(property, propertySelected);

            facets.add(propertyFacetResult);

            facetPage.getFacetResultPage(String.format(FIELD_DYNAMIC_PROPERTY_PATTERN, property.getId())).forEach(entry -> {
                Long valueId = null;

                try {
                    valueId = Long.parseLong(entry.getValue());
                } catch (NumberFormatException e) {
                    logger.error("parse filter error", e);
                }

                if (null == valueId) {
                    return;
                }

                final PropertyValue value = property.getValue(valueId);

                if (null == value) {
                    return;
                }

                final Long count = entry.getValueCount();

                final Boolean selected = propertySelected && filterValues.get(property.getId()).contains(valueId) && count > 0;

                propertyFacetResult.addPropertyValueFacetResult(new PropertyValueFacetResult(value, count, selected));
            });
        });

        final Page<ProductDocument> productPage = new PageImpl<>(products, pageable, facetPage.getTotalElements());

        Result result = new ResultImpl(productPage, facets);

        return result;
    }


    public SolrOperations getProductsTemplate() {
        return productsTemplate;
    }

    public void setProductsTemplate(SolrOperations productsTemplate) {
        this.productsTemplate = productsTemplate;
    }

    private static Criteria buildSearchCriteria(QueryOptions queryOptions) {
        final String search = queryOptions.getQuery();

        if (null != search && !search.isEmpty()) {
            final List<String> words = Arrays.asList(search.split("\\s+"));

            if (words.size() > 0) {
                final Criteria criteria = new Criteria(FIELD_NAME).is(search).boost(EXACT_BOOST);

                criteria.or(new Criteria(FIELD_DESCRIPTION).contains(words).boost(DESCRIPTION_CONTAINS_BOOST));
                criteria.or(new Criteria(FIELD_NAME).startsWith(words).boost(WILDCARD_BOOST));

                words.forEach(word -> criteria.or(new Criteria(FIELD_NAME).fuzzy(word, FUZZY_DISTANCE).boost(FUZZY_BOOST)));

                logger.debug(criteria.toString());

                return criteria;
            }
        }

        return new SimpleStringCriteria("*:*");
    }

    private static Criteria buildFilterValuesCriteria(final Long propertyId, final Set<Long> values) {
        final String fieldName = String.format(FIELD_DYNAMIC_PROPERTY_PATTERN, propertyId);

        final String taggedFieldName = String.format("{!tag=%1$s}%1$s", fieldName);

        final String fq = new StringBuilder(taggedFieldName)
                .append(":(")
                .append(values.stream().map(String::valueOf).collect(Collectors.joining(" OR ")))
                .append(")")
                .toString();

        logger.debug(fq);

        return new SimpleStringCriteria(fq);
    }

    public SortOrder[] getAwailableSortOrders() {
        return awailableSortOrders;
    }
}
