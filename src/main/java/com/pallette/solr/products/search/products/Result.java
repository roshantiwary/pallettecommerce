package com.pallette.solr.products.search.products;

import org.springframework.data.domain.Page;

import com.pallette.browse.documents.ProductDocument;

import java.util.Set;

/**
 * Created by Roshan.
 */
public interface Result {
    Page<ProductDocument> getProducts();

    Set<PropertyFacetResult> getFacets();
}
