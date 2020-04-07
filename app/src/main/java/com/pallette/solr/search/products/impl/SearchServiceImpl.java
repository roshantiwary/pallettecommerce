package com.pallette.solr.search.products.impl;

import com.pallette.solr.products.entities.Property;
import com.pallette.solr.products.entities.PropertyValue;
import com.pallette.solr.products.search.products.FacetConfig;
import com.pallette.solr.products.search.products.FacetConfig.Facet;
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
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.FacetOptions;
import org.springframework.data.solr.core.query.FacetQuery;
import org.springframework.data.solr.core.query.SimpleFacetQuery;
import org.springframework.data.solr.core.query.SimpleFilterQuery;
import org.springframework.data.solr.core.query.SimpleStringCriteria;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
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

	private static final String FIELD_NAME = "product_title";

	private static final String FIELD_PRICE = "price";

	private static final String FIELD_DESCRIPTION = "product_description";

	private static final Float EXACT_BOOST = 100f;

	private static final Float FIELD_NAME_CONTAINS_BOOST = 10f;
	
	private static final Float DESCRIPTION_CONTAINS_BOOST = 10f;

	private static final Float WILDCARD_BOOST = 1f;

	private static final Float FUZZY_BOOST = 0.1f;

	private static final Float FUZZY_DISTANCE = 0.2f;
	
	private static final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

	@Autowired
	private FacetConfig facetConfig;

	@Autowired
	private SolrTemplate productsTemplate;

	private SortOrder[] awailableSortOrders = new SortOrder[] { new SortOrder("default", DESC, "score"),
			new SortOrder(FIELD_NAME + " " + ASC.toString(), ASC, FIELD_NAME),
			new SortOrder(FIELD_PRICE + " " + ASC.toString(), ASC, FIELD_PRICE),
			new SortOrder(FIELD_PRICE + " " + DESC.toString(), DESC, FIELD_PRICE), };

	
	public Result search(QueryOptions queryOptions, Pageable pageable, List<String> filters) throws IOException {
        final FacetQuery facetQuery = new SimpleFacetQuery(buildSearchCriteria(queryOptions), pageable);

        final Long priceMin = queryOptions.getMinPrice();
        final Long priceMax = queryOptions.getMaxPrice();

        if (priceMin != null) {
            facetQuery.addFilterQuery(new SimpleFilterQuery(new Criteria(FIELD_PRICE).greaterThanEqual(priceMin)));
        }

        if (priceMax != null) {
            facetQuery.addFilterQuery(new SimpleFilterQuery(new Criteria(FIELD_PRICE).lessThanEqual(priceMax)));
        }

        final Map<String, Set<String>> filterValues = queryOptions.getFilterValues();

        filterValues.forEach((propertyId, values) -> {
            facetQuery.addFilterQuery(new SimpleFilterQuery(buildFilterValuesCriteria(propertyId, values)));
        });

        final FacetOptions facetOptions = new FacetOptions();

        facetOptions.setFacetMinCount(0);

        List<Facet> facetsList = facetConfig.getFacets();
 
      	facetConfig.getFacets().forEach(facet -> {
    			String fieldName = facet.getValue();

    			if (filterValues.containsKey(facet.getValue())) {
    				fieldName = String.format("{!ex=%1$s}%1$s", fieldName);
    			}

    			facetOptions.addFacetOnField(fieldName);
    		});
        
        facetQuery.setFacetOptions(facetOptions);
        
        final FacetPage<ProductSolrDocument> facetPage = productsTemplate.queryForFacetPage("mycore", facetQuery, ProductSolrDocument.class);
        List<ProductSolrDocument> recordList = facetPage.getContent();
        
//      FacetsList Iterator logic can be improved further by using the below commented loop
//        Iterator<Page<FacetFieldEntry>> facetFieldIterator = facetPage.getFacetResultPages().iterator();
//        while(facetFieldIterator.hasNext()) {
//        	Page<FacetFieldEntry> fieldEntry = facetFieldIterator.next();
//        	List<FacetFieldEntry> fieldList = fieldEntry.getContent();
//        	fieldList.forEach(field -> {
//        		if(field.getValueCount() > 0) {
//        			System.out.println(field.getKey() + " *** " + field.getValue() + " %%% " + field.getValueCount());
//        		}
//        	});
//        }
       Set<PropertyFacetResult> facets = new LinkedHashSet<>();
       
        Iterator<Facet> facetIterator = facetsList.iterator();
        while(facetIterator.hasNext()) {
        	Facet facet = facetIterator.next();
        	Property dimension = new Property();
            dimension.setId(facet.getValue());
    		dimension.setName(facet.getValue());
    		Set<PropertyValueFacetResult> dimensionValues = new HashSet<PropertyValueFacetResult>();
			Boolean propertySelected = new Boolean(false);
			
			facetPage.getFacetResultPage(dimension.getId()).forEach(entry -> {
				if(entry.getValueCount() > 0) {
					boolean isSelected = false;
					Map<String, Set<String>> filValues = queryOptions.getFilterValues();
					Set<String> appliedRefinements = (HashSet<String>) filValues.get(dimension.getId());
					String encodedValue;
					
					PropertyValue propertyValue = new PropertyValue();
					propertyValue.setId(entry.getValue());
					propertyValue.setName(entry.getValue());
					propertyValue.setCount(entry.getValueCount());
					encodedValue = encode(entry.getValue());
					String filter = dimension.getId() + "|" + encodedValue;
			    	
					if(null != appliedRefinements && appliedRefinements.contains(encodedValue)) {
						dimension.setSelected(true);
						isSelected = true;
						propertyValue.setRefinementURL(getRefinementURL(filters, filter, null).toString());
					} else {
						propertyValue.setRefinementURL(getRefinementURL(filters, null, filter).toString());
					}

					dimensionValues.add(new PropertyValueFacetResult(propertyValue, propertyValue.getCount(), isSelected));
				}
              }); 
			
			final PropertyFacetResult propertyFacetResult = new PropertyFacetResult(dimension, propertySelected, dimensionValues) ;
			facets.add(propertyFacetResult);
        }
        
        final Page<ProductSolrDocument> productPage = new PageImpl<>(recordList, pageable, facetPage.getTotalElements());

        Result result = new ResultImpl(productPage, facets);

        return result;
    }

	private String encode(String refinementUrl) {
		String encodedUrl = null;
		try{
			encodedUrl = URLEncoder.encode(refinementUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("Error encoding refinement url");
		}
		return encodedUrl;
	}
	
	private StringBuilder getRefinementURL(List<String> filters, String removeFilter, String addFilter) {
	    StringBuilder sb = new StringBuilder();
	    
	    if(null != filters) {
			filters.forEach(filter -> {
				if(null == removeFilter) {
					sb.append("&f=").append(encode(filter));
					sb.append("&f=").append(encode(addFilter));
				} else if (removeFilter.equalsIgnoreCase(filter)) {
//					sb.append("&f=").append(encode(filter));
				} else {
					sb.append("&f=").append(encode(filter));
					String appendFilter = (addFilter==null) ? removeFilter : addFilter;
					if(!appendFilter.equalsIgnoreCase(removeFilter))
					sb.append("&f=").append(encode(appendFilter));					
				}
			});
	    } else if(null != addFilter) {
	    	sb.append("?f=").append(encode(addFilter));
	    }
		
		return sb;
	}

	// This might be useful in future for PLP facets
	/*
	private static Property getProperty(String fieldName, String fieldValue) throws JsonParseException, JsonMappingException, IOException {
		Property property = new Property();
		if(fieldName.equals("product_category")) {
			CategoryDocument category;
				category = new ObjectMapper().readValue(fieldValue, CategoryDocument.class);
				property.setId(category.getId());
				property.setName(category.getCategoryTitle());
		} else if(fieldName.equals("product_brand")) {
				BrandDocument brand = new ObjectMapper().readValue(fieldValue, BrandDocument.class);
				property.setId(brand.getId());
				property.setName(brand.getStoreName());
		} else if(fieldName.equals("product_sku")) {
				SkuDocument sku = new ObjectMapper().readValue(fieldValue, SkuDocument.class);
				property.setId(sku.getId());
				property.setName(sku.getId());
		}
		return property;
	}
	*/
	
	public SolrTemplate getProductsTemplate() {
		return productsTemplate;
	}

	public void setProductsTemplate(SolrTemplate productsTemplate) {
		this.productsTemplate = productsTemplate;
	}

	private static Criteria buildSearchCriteria(QueryOptions queryOptions) {
		final String search = queryOptions.getQuery();

		if (null != search && !search.isEmpty()) {
			final List<String> words = Arrays.asList(search.split("\\s+"));

			if (words.size() > 0) {
				final Criteria criteria = new Criteria(FIELD_NAME).contains(words).boost(FIELD_NAME_CONTAINS_BOOST).or(
						new Criteria(FIELD_DESCRIPTION).contains(words).boost(DESCRIPTION_CONTAINS_BOOST)).or(
								new Criteria(FIELD_NAME).startsWith(words).boost(WILDCARD_BOOST));
								words.forEach(
				word -> criteria.or(new Criteria(FIELD_NAME).fuzzy(word, FUZZY_DISTANCE).boost(FUZZY_BOOST)));

				logger.debug(criteria.toString());
				return criteria;
			}
		}

		return new SimpleStringCriteria("*:*");
	}

	private static Criteria buildFilterValuesCriteria(final String propertyId, final Set<String> values) {
		final String fieldName = String.valueOf(propertyId);

		final String taggedFieldName = String.format("{!tag=%1$s}%1$s", fieldName);

		final String fq = new StringBuilder(taggedFieldName).append(":(")
				.append(values.stream().map(String::valueOf).collect(Collectors.joining(" OR "))).append(")")
				.toString();

		logger.debug(fq);

		return new SimpleStringCriteria(fq);
	}

	public SortOrder[] getAwailableSortOrders() {
		return awailableSortOrders;
	}
}
