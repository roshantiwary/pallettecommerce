package com.pallette.solr.repository;

import java.util.List;

import org.springframework.data.solr.repository.SolrCrudRepository;

import com.pallette.solr.domain.SolrProduct;

public interface SolrProductRepository extends SolrCrudRepository<SolrProduct, String>{

	List<SolrProduct> findByProductTitle(String title);
}
