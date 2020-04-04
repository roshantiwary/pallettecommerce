package com.pallette.solr.products.search.products;

import org.springframework.data.domain.Page;
import com.pallette.solr.products.entities.Product;

import java.util.Set;

/**
 * Created by Roshan.
 */
public interface Result {
	Page<Product> getProducts();

	Set<PropertyFacetResult> getFacets();
}