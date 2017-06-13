package com.pallette.solr.response;


import java.util.List;

import org.springframework.data.solr.core.query.result.SolrResultPage;

import com.pallette.response.Response;
import com.pallette.solr.domain.SolrProduct;

public class ProductsResponse extends Response{

	private SolrResultPage<SolrProduct> products;

	public SolrResultPage<SolrProduct> getProducts() {
		return products;
	}

	public void setProducts(SolrResultPage<SolrProduct> products) {
		this.products = products;
	}

	
}
