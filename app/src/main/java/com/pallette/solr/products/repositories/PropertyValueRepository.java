package com.pallette.solr.products.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.pallette.solr.products.entities.PropertyValue;

public interface PropertyValueRepository extends PagingAndSortingRepository<PropertyValue, Long> {

}
