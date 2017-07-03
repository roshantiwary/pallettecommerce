package com.pallette.solr.search.products.impl;

import com.pallette.browse.documents.ProductDocument;
import com.pallette.solr.products.search.products.PropertyFacetResult;
import com.pallette.solr.products.search.products.Result;
import org.springframework.data.domain.Page;

import java.util.Set;

/**
 * Created by Roshan.
 */
public class ResultImpl implements Result {
    private Page<ProductDocument> productPage;

    private Set<PropertyFacetResult> facets;

    public ResultImpl() {

    }

    public ResultImpl(final Page<ProductDocument> productPage, final Set<PropertyFacetResult> facets) {
        this.productPage = productPage;
        this.facets = facets;
    }

    public Page<ProductDocument> getProducts() {
        return productPage;
    }

    public Set<PropertyFacetResult> getFacets() {
        return facets;
    }
}
