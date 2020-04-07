package com.pallette.solr.products.search.products;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * Created by Roshan.
 */
public interface SearchService {
    Result search(QueryOptions queryOptions, Pageable pageable, List<String> currentRefinementURL) throws JsonParseException, JsonMappingException, IOException;

    default QueryOptions getQueryOptions() {
        return new QueryOptions(this);
    }

    SortOrder[] getAwailableSortOrders();
}
