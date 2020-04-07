package com.pallette.solr.products.search.products;

import org.springframework.data.domain.Page;
import com.pallette.solr.document.ProductSolrDocument;

import java.util.Set;

/**
 * Created by Roshan.
 */
public interface Result {
    Page<ProductSolrDocument> getProducts();

    Set<PropertyFacetResult> getFacets();
}
