package com.pallette.solr.response;

import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.SolrResultPage;

import com.pallette.response.Response;
import com.pallette.solr.products.entities.Product;

public class ProductsResponse extends Response {

	private SolrResultPage<Product> products;

	private Page<Product> productsPage;

	private FacetPage<Product> productsFacetPage;

	private HighlightPage<Product> productsHighlightPage;

	/**
	 * @return the productsFacetPage
	 */
	public FacetPage<Product> getProductsFacetPage() {
		return productsFacetPage;
	}

	/**
	 * @param productsFacetPage
	 *            the productsFacetPage to set
	 */
	public void setProductsFacetPage(FacetPage<Product> productsFacetPage) {
		this.productsFacetPage = productsFacetPage;
	}

	public SolrResultPage<Product> getProducts() {
		return products;
	}

	public void setProducts(SolrResultPage<Product> products) {
		this.products = products;
	}

	/**
	 * @return the productsPage
	 */
	public Page<Product> getProductsPage() {
		return productsPage;
	}

	/**
	 * @param productsPage
	 *            the productsPage to set
	 */
	public void setProductsPage(Page<Product> productsPage) {
		this.productsPage = productsPage;
	}

	/**
	 * @return the productsHighlightPage
	 */
	public HighlightPage<Product> getProductsHighlightPage() {
		return productsHighlightPage;
	}

	/**
	 * @param productsHighlightPage
	 *            the productsHighlightPage to set
	 */
	public void setProductsHighlightPage(
			HighlightPage<Product> productsHighlightPage) {
		this.productsHighlightPage = productsHighlightPage;
	}

}
