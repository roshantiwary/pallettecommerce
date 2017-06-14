package com.pallette.solr.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.Boost;
import org.springframework.data.solr.repository.Facet;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import com.pallette.solr.domain.SolrProduct;

public interface SolrProductRepository extends SolrCrudRepository<SolrProduct, String> {

	/**
	 * 
	 * @param title
	 * @return
	 */
	List<SolrProduct> findByProductTitle(String title);

	
	/**
	 * This query method will not work if the search term contains more than one
	 * word.To support pagination we added the Pageable parameter and changed
	 * the return type from List<SolrProduct> to Page<SolrProduct>. By adding
	 * the @Boost annotation to the title parameter we are boosting SolrProducts
	 * whose name matches the search parameter. This makes sense because those
	 * SolrProduct are typically at higher Interest for the user.
	 * 
	 * @param title
	 * @param description
	 * @return
	 */
	Page<SolrProduct> findByProductTitleContainsOrProductDescriptionContains(@Boost(2) String title, String description , Pageable pageable);
	
	
	/**
	 * With the @Facet annotation we tell Spring Data Solr to facet Products by
	 * product_status and return the first five facets.
	 * 
	 * @param name
	 * @param page
	 * @return
	 */
	@Query("product_title:*?0*")
	@Facet(fields = { "product_status" }, limit = 5)
	FacetPage<SolrProduct> findByProductTitleAndFacetOnProductStatus(String productTitle, Pageable page);
	
	
	/**
	 * The @Highlight annotation tells Solr to highlight to occurrences of the
	 * searched description.
	 * 
	 * @param description
	 * @param pageable
	 * @return
	 */
	@Highlight(prefix = "<highlight>", postfix = "</highlight>")
	HighlightPage<SolrProduct> findByProductDescription(String productDescription, Pageable pageable);

}
