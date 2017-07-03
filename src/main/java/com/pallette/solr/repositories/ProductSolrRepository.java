package com.pallette.solr.repositories;

import com.pallette.solr.document.ProductSolrDocument;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Roshan.
 */
public interface ProductSolrRepository extends PagingAndSortingRepository<ProductSolrDocument, Long> {
}
