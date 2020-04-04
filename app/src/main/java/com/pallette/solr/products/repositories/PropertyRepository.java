package com.pallette.solr.products.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.pallette.solr.products.entities.Property;

public interface PropertyRepository extends PagingAndSortingRepository<Property, String> {

	/**
	 * Method that queries the Mongo DB to fetch all the Property documents.
	 */
	public List<Property> findAll();
}
