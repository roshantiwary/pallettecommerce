package com.pallette.solr.controller;

import com.pallette.constants.RestURLConstants;
import com.pallette.exception.NoRecordsFoundException;
import com.pallette.solr.products.entities.PropertyValue;
import com.pallette.solr.products.repositories.PropertyValueRepository;
import com.pallette.solr.products.search.products.QueryOptions;
import com.pallette.solr.products.search.products.Result;
import com.pallette.solr.products.search.products.SearchService;
import com.pallette.solr.products.search.products.SortOrder;
import com.pallette.solr.response.SearchPage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Created by Roshan.
 */
@RestController
@RequestMapping("/rest/api/v1/")
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    private static final Integer PAGE_SIZE = 10;

    @Autowired
    private SearchService searchService;

    @Autowired
    private PropertyValueRepository valueRepository;

    @RequestMapping(value = RestURLConstants.SEARCH_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String index(
            @RequestParam(value = "p", required = false) Integer page,
            @RequestParam(value = "s", required = false) String orderString,
            @RequestParam(value = "search", required = false) final String search,
            @RequestParam(value = "f", required = false) final List<Long> filters) throws NoRecordsFoundException{

        final SortOrder order = parseSortOrder(orderString);

        logger.debug("order: {}, {}", orderString, order);

        if (null == order) {
            throw new NoRecordsFoundException("No records found");
        }

        orderString = order.toString();

        if (null == page) {
            page = 1;
        } else if (page < 1) {
            throw new NoRecordsFoundException("No records found");
        }

        final QueryOptions queryOptions = searchService.getQueryOptions();

        queryOptions.setQuery(search);

        if (filters != null && !filters.isEmpty()) {
            filters.forEach(valueId -> {
                final Optional<PropertyValue> value = valueRepository.findById(valueId);

//                if (null == value) {
//                    throw new NoRecordsFoundException("No records found");
//                }

                queryOptions.addFilterValue(value.get().getProperty().getId(), valueId);
            });
        }

        final Result result = queryOptions.search(PageRequest.of(page - 1, PAGE_SIZE, Sort.unsorted()));
        
        SearchPage searchPage = new SearchPage();
        searchPage.setUrlPath("/");
        searchPage.setSearch(search);
        searchPage.setPage(page);
        searchPage.setOrderString(orderString);
        searchPage.setOrders(searchService.getAwailableSortOrders());
        searchPage.setFacets(result.getFacets());
        searchPage.setFilters(filters);
        searchPage.setProducts(result.getProducts());

        return "index";
    }

    private SortOrder parseSortOrder(final String s) {
        SortOrder[] orders = searchService.getAwailableSortOrders();

        assert orders.length > 0;

        for(SortOrder order: orders) {
            if (order.toString().equals(s)) {
                return order;
            }
        }

        return null == s || s.isEmpty() ? orders[0] : null;
    }
}
