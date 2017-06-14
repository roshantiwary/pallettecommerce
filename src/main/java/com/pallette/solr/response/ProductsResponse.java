package com.pallette.solr.response;

import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.SolrResultPage;

import com.pallette.response.Response;
import com.pallette.solr.domain.SolrProduct;

public class ProductsResponse extends Response {

	private SolrResultPage<SolrProduct> products;

	private Page<SolrProduct> productsPage;

	private FacetPage<SolrProduct> productsFacetPage;

	private HighlightPage<SolrProduct> productsHighlightPage;

	/**
	 * @return the productsFacetPage
	 */
	public FacetPage<SolrProduct> getProductsFacetPage() {
		return productsFacetPage;
	}

	/**
	 * @param productsFacetPage
	 *            the productsFacetPage to set
	 */
	public void setProductsFacetPage(FacetPage<SolrProduct> productsFacetPage) {
		this.productsFacetPage = productsFacetPage;
	}

	public SolrResultPage<SolrProduct> getProducts() {
		return products;
	}

	public void setProducts(SolrResultPage<SolrProduct> products) {
		this.products = products;
	}

	/**
	 * @return the productsPage
	 */
	public Page<SolrProduct> getProductsPage() {
		return productsPage;
	}

	/**
	 * @param productsPage
	 *            the productsPage to set
	 */
	public void setProductsPage(Page<SolrProduct> productsPage) {
		this.productsPage = productsPage;
	}

	/**
	 * @return the productsHighlightPage
	 */
	public HighlightPage<SolrProduct> getProductsHighlightPage() {
		return productsHighlightPage;
	}

	/**
	 * @param productsHighlightPage
	 *            the productsHighlightPage to set
	 */
	public void setProductsHighlightPage(
			HighlightPage<SolrProduct> productsHighlightPage) {
		this.productsHighlightPage = productsHighlightPage;
	}

}
