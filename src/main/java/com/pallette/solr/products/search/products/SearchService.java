package com.pallette.solr.products.search.products;

import org.springframework.data.domain.Pageable;

/**
 * Created by Roshan.
 */
public interface SearchService {
    Result search(QueryOptions queryOptions, Pageable pageable);

    default QueryOptions getQueryOptions() {
        return new QueryOptions(this);
    }

    SortOrder[] getAwailableSortOrders();
}
