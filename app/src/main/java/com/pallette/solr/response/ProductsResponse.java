package com.pallette.solr.response;

import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.SolrResultPage;

import com.pallette.response.Response;
import com.pallette.solr.document.ProductSolrDocument;

public class ProductsResponse extends Response {

	private SolrResultPage<ProductSolrDocument> products;

	private Page<ProductSolrDocument> productsPage;

	private FacetPage<ProductSolrDocument> productsFacetPage;

	private HighlightPage<ProductSolrDocument> productsHighlightPage;

	/**
	 * @return the productsFacetPage
	 */
	public FacetPage<ProductSolrDocument> getProductsFacetPage() {
		return productsFacetPage;
	}

	/**
	 * @param productsFacetPage
	 *            the productsFacetPage to set
	 */
	public void setProductsFacetPage(FacetPage<ProductSolrDocument> productsFacetPage) {
		this.productsFacetPage = productsFacetPage;
	}

	public SolrResultPage<ProductSolrDocument> getProducts() {
		return products;
	}

	public void setProducts(SolrResultPage<ProductSolrDocument> products) {
		this.products = products;
	}

	/**
	 * @return the productsPage
	 */
	public Page<ProductSolrDocument> getProductsPage() {
		return productsPage;
	}

	/**
	 * @param productsPage
	 *            the productsPage to set
	 */
	public void setProductsPage(Page<ProductSolrDocument> productsPage) {
		this.productsPage = productsPage;
	}

	/**
	 * @return the productsHighlightPage
	 */
	public HighlightPage<ProductSolrDocument> getProductsHighlightPage() {
		return productsHighlightPage;
	}

	/**
	 * @param productsHighlightPage
	 *            the productsHighlightPage to set
	 */
	public void setProductsHighlightPage(
			HighlightPage<ProductSolrDocument> productsHighlightPage) {
		this.productsHighlightPage = productsHighlightPage;
	}

}
