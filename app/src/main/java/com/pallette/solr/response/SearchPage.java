package com.pallette.solr.response;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.pallette.response.Response;
import com.pallette.solr.document.ProductSolrDocument;
import com.pallette.solr.products.search.products.PropertyFacetResult;
import com.pallette.solr.products.search.products.SortOrder;

public class SearchPage extends Response {

	private String urlPath;
	private String search;
	private Integer page;
	private String orderString;
	private SortOrder[] orders;
	private Set<PropertyFacetResult> facets;
	private List<String> filters;
	private Page<ProductSolrDocument> products;

	public SortOrder[] getOrders() {
		return orders;
	}

	public void setOrders(SortOrder[] orders) {
		this.orders = orders;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getOrderString() {
		return orderString;
	}

	public void setOrderString(String orderString) {
		this.orderString = orderString;
	}

	public Set<PropertyFacetResult> getFacets() {
		return facets;
	}

	public void setFacets(Set<PropertyFacetResult> facets) {
		this.facets = facets;
	}

	public List<String> getFilters() {
		return filters;
	}

	public void setFilters(List<String> filters) {
		this.filters = filters;
	}

	public Page<ProductSolrDocument> getProducts() {
		return products;
	}

	public void setProducts(Page<ProductSolrDocument> products) {
		this.products = products;
	}
}
