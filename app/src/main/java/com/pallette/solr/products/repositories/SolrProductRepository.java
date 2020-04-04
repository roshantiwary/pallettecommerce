package com.pallette.solr.products.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.pallette.solr.products.entities.Product;

/**
 * Created by Roshan.
 */
public interface SolrProductRepository extends  PagingAndSortingRepository<Product, Long>{

}
