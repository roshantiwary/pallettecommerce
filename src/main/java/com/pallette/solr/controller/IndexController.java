package com.pallette.solr.controller;

import com.pallette.exception.NoRecordsFoundException;
import com.pallette.solr.products.search.products.QueryOptions;
import com.pallette.solr.products.search.products.Result;
import com.pallette.solr.products.search.products.SearchService;
import com.pallette.solr.products.search.products.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Roshan.
 */
@Controller
@RequestMapping("/rest/api/v1/")
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    private static final Integer PAGE_SIZE = 10;

    @Autowired
    private SearchService searchService;

    @RequestMapping(value="search", method = RequestMethod.GET)
    public String index(
            @RequestParam(value = "p", required = false) Integer page,
            @RequestParam(value = "s", required = false) String orderString,
            @RequestParam(value = "search", required = false) final String search,
            @RequestParam(value = "f", required = false) final List<Long> filters,
            final ModelMap model) throws NoRecordsFoundException{

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

//        if (filters != null && !filters.isEmpty()) {
//            filters.forEach(valueId -> {
//                final PropertyValue value = valueRepository.findOne(valueId);
//
//                if (null == value) {
//                    try {
//						throw new NoRecordsFoundException("No records found");
//					} catch (NoRecordsFoundException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//                }
//
//                queryOptions.addFilterValue(value.getProperty().getId(), valueId);
//            });
//        }
        
        queryOptions.addFilterValue(Long.valueOf(1234), Long.valueOf(1234));

        final Result result = queryOptions.search(new PageRequest(page - 1, PAGE_SIZE, new Sort(order)));

        model.addAttribute("urlPath", "/");
        model.addAttribute("search", search);
        model.addAttribute("page", page);
        model.addAttribute("orderString", orderString);
        model.addAttribute("orders", searchService.getAwailableSortOrders());
        model.addAttribute("facets", result.getFacets());
        model.addAttribute("filters", filters);

        model.addAttribute("products", result.getProducts());

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
