package com.pallette.solr.controller;

import com.pallette.exception.NoRecordsFoundException;
import com.pallette.solr.products.entities.PropertyValue;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

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

    @RequestMapping(value="search", method = RequestMethod.GET)
    public ResponseEntity<SearchPage> index(
            @RequestParam(value = "p", required = false) Integer page,
            @RequestParam(value = "s", required = false) String orderString,
            @RequestParam(value = "search", required = false) final String search,
            @RequestParam(value = "f", required = false) final List<String> filters) throws NoRecordsFoundException{

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
        
        StringBuilder existingRefinementURL = new StringBuilder();
        existingRefinementURL.append("?");

        if (filters != null && !filters.isEmpty()) {
	        filters.forEach(filter -> {
	    		existingRefinementURL.append("f=");
	    		existingRefinementURL.append(getEncodedValue(filter));
	    		int index = filter.indexOf("|");  // Gets the first index where a space occours
	    		String propertyId = filter.substring(0,index); // Gets the first part
	    		String valueId = filter.substring(index + 1, filter.length());
	    		valueId = getEncodedValue(valueId);
	    		queryOptions.addFilterValue(propertyId, valueId);
	    	});
        }
      
        SearchPage searchPage = new SearchPage();
        searchPage.setUrlPath("/");
        searchPage.setSearch(search);
        searchPage.setPage(page);
        searchPage.setOrderString(orderString);
        searchPage.setOrders(searchService.getAwailableSortOrders());
        searchPage.setFilters(filters);
        
        Result result;
		try {
			result = queryOptions.search(PageRequest.of(page - 1, PAGE_SIZE, Sort.by(order)), filters);
//			result = queryOptions.search(new PageRequest(page - 1, PAGE_SIZE, new Sort(order)), filters);
			searchPage.setFacets(result.getFacets());
	        searchPage.setProducts(result.getProducts());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return new ResponseEntity<SearchPage>(searchPage, new HttpHeaders(), HttpStatus.OK);
    }

	private String getEncodedValue(String valueId) {
		try {
			valueId = URLEncoder.encode(valueId, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return valueId;
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
