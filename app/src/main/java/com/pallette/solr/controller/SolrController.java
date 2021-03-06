package com.pallette.solr.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.result.FacetEntry;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightEntry.Highlight;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.SolrResultPage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pallette.browse.documents.ProductDocument;
import com.pallette.browse.repository.ProductRepository;
import com.pallette.solr.document.ProductSolrDocument;
import com.pallette.solr.domain.ProductSearchRequest;
import com.pallette.solr.response.ProductsResponse;

@RestController
@RequestMapping("/rest/api/v1")
public class SolrController {

	private static final String PRODUCT_DESCRIPTION = "productDescription";

	private static final String PRODUCT_TITLE = "productTitle";

	private static final Logger logger = LoggerFactory.getLogger(SolrController.class);

//	@Autowired
//	SolrProductRepository solrProductRepository;
//	
	/**
	 * The Product repository.
	 */
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private SolrTemplate productsTemplate;

	/**
	 * 
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@PostMapping("/index/solr/products")
	public ResponseEntity<?> handleIndexProducts() throws IllegalAccessException, InvocationTargetException {

		logger.debug("Inside SolrController.indexProducts()");
		List<ProductDocument> products = productRepository.findAll();
		if (null != products && !products.isEmpty()) {
			for (ProductDocument product : products) {

				ProductSolrDocument solrProduct = new ProductSolrDocument();
				solrProduct.setId(product.getId());
				solrProduct.setProductDescription(product.getProductDescription());
				solrProduct.setProductSlug(product.getProductSlug());
				solrProduct.setProductStatus(product.getProductStatus());
				solrProduct.setProductTitle(product.getProductTitle());
				if(null != product.getCategoryDocument()) {
					solrProduct.setCategoryDocument(product.getCategoryDocument().getCategoryTitle());
				}
				if(null != product.getProductBrand()) {
					solrProduct.setBrandDocument(product.getProductBrand().getStoreName());	
				}
				
				List<String> skuList= product.getSkuDocument().stream()
					.map(sku -> sku.getId())
					.collect(Collectors.toList());
				
				solrProduct.setSkuDocument(skuList);
//				solrProductRepository.save(solrProduct);
				productsTemplate.saveBean("mycore", solrProduct);
			}
		}
		return new ResponseEntity("Successfully uploaded", new HttpHeaders(), HttpStatus.OK);
	}

//	/**
//	 * 
//	 * @return
//	 */
//	@RequestMapping(value = "/search/all/products", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ProductsResponse handleSearchAllProducts() {
//		
//		logger.debug("Inside SolrController.getProducts()");
//		SolrResultPage<ProductSolrDocument> products = (SolrResultPage<ProductSolrDocument>) solrProductRepository.findAll();
//		ProductsResponse response = new ProductsResponse();
//		response.setProducts(products);
//		return response;
//	}
//	
//	
//	/**
//	 * 
//	 * @return
//	 */
//	@RequestMapping(value = "/search/products/customQuery/{searchTerm}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ProductsResponse handleSearchUsingCustomQuery(@PathVariable("searchTerm") String searchTerm) {
//
//		logger.debug("Inside SolrController.handleSearchUsingCustomQuery()");
//		Page<ProductSolrDocument> products = solrProductRepository.findByCustomQuery(searchTerm, PageRequest.of(0, 10, Sort.unsorted()));
//		ProductsResponse response = new ProductsResponse();
//		response.setProductsPage(products);
//		return response;
//	}
//	
//	
//	/**
//	 * 
//	 * @return
//	 */
//	@RequestMapping(value = "/search/products/title", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ProductsResponse handleSearchProductsBasedOnTitleAndDescription(@Valid @RequestBody ProductSearchRequest productSearchRequest , Errors errors) {
//
//		logger.debug("Inside SolrController.handleSearchProductsBasedOnTitleAndDescription()");
//		Page<ProductSolrDocument> productsPage = solrProductRepository.findByProductTitleContainsOrProductDescriptionContains(
//				productSearchRequest.getProductTitle(), productSearchRequest.getProductDescription(), PageRequest.of(productSearchRequest.getStartPage(), productSearchRequest.getEndPage(), Sort.unsorted()));
//
//		logger.debug("Content" + productsPage.getContent()); // get a list of  (max) 10 books.
//		logger.debug("Total Elements" + productsPage.getTotalElements()); // total number of elements (can be  >10).
//		logger.debug("Total pages" + productsPage.getTotalPages()); // total number of pages.
//		logger.debug("Get Number" + productsPage.getContent()); // current page number.
//		logger.debug("is First Page" + productsPage.getContent()); // true if  this is the first page.
//		logger.debug("has Next Page" + productsPage.getContent()); // true if another page is  available.
//		logger.debug("next Pageable" + productsPage.getContent()); // the pageable for requesting  the next page.
//		ProductsResponse response = new ProductsResponse();
//		response.setProductsPage(productsPage);
//		return response;
//	}
//	
//	
//	/**
//	 * 
//	 * @return
//	 */
//	@RequestMapping(value = "/facet/products/title/{productTitle}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ProductsResponse handleFindByTitleAndFacetOnStatus(@PathVariable(PRODUCT_TITLE) String productTitle) {
//
//		logger.debug("Inside SolrController.handleFindByTitleAndFacetOnStatus()");
//		FacetPage<ProductSolrDocument> productsFacetPage = solrProductRepository.findByProductTitleAndFacetOnProductStatus(productTitle, PageRequest.of(0, 10, Sort.unsorted()));
//
//		logger.debug("Content" + productsFacetPage.getContent()); // the first 10 books
//		/*
//		 * booksFacetPage.getAllFacets() returns a Collection of FacetEntry
//		 * pages. This is because the @Facet annotation allows you to facet
//		 * multiple fields at once. Each FacetPage contains max. five
//		 * FacetEntries (defined by the limit attribute of @Facet).
//		 */
//		for (Page<? extends FacetEntry> page : productsFacetPage.getAllFacets()) {
//			for (FacetEntry facetEntry : page.getContent()) {
//				String status = facetEntry.getValue(); // name of the category
//				logger.debug("status ::" + status);
//				long count = facetEntry.getValueCount(); // number of books in this category
//				logger.debug("count ::" + count);
//			}
//		}
//		ProductsResponse response = new ProductsResponse();
//		response.setProductsFacetPage(productsFacetPage);
//		return response;
//	}
//	
//	/**
//	 * 
//	 * @return
//	 */
//	@RequestMapping(value = "/highlight/products/description/{productDescription}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ProductsResponse handleHighlightSearch(@PathVariable(PRODUCT_DESCRIPTION) String productDescription) {
//
//		logger.debug("Inside SolrController.handleHighlightSearch()");
//		HighlightPage<ProductSolrDocument> productsHighlightPage = solrProductRepository.findByProductDescription(productDescription, PageRequest.of(0, 10, Sort.unsorted()));
//
//		logger.debug("Content" + productsHighlightPage.getContent()); // the  first 10 books
//		
//		for (HighlightEntry<ProductSolrDocument> he : productsHighlightPage.getHighlighted()) {
//			// A HighlightEntry belongs to an Entity (SolrProduct) and may have multiple highlighted fields (description).
//			
//			for (Highlight highlight : he.getHighlights()) {
//				// Each highlight might have multiple occurrences within the  description.
//				for (String snipplet : highlight.getSnipplets()) {
//					// snipplet contains the highlighted text.
//					logger.debug("Snipplet ::" + snipplet);
//				}
//			}
//		}
//		ProductsResponse response = new ProductsResponse();
//		response.setProductsHighlightPage(productsHighlightPage);
//		return response;
//	}

}
