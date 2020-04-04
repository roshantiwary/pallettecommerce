package com.pallette.solr.search.products.impl;

import com.pallette.solr.products.entities.Product;
import com.pallette.solr.products.search.products.PropertyFacetResult;
import com.pallette.solr.products.search.products.Result;
import org.springframework.data.domain.Page;

import java.util.Set;

/**
 * Created by Roshan.
 */
public class ResultImpl implements Result {
    private Page<Product> productPage;

    private Set<PropertyFacetResult> facets;

    public ResultImpl() {

    }

    public ResultImpl(final Page<Product> productPage, final Set<PropertyFacetResult> facets) {
        this.productPage = productPage;
        this.facets = facets;
    }

    public Page<Product> getProducts() {
        return productPage;
    }

    public Set<PropertyFacetResult> getFacets() {
        return facets;
    }
}