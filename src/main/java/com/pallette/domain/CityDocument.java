package com.pallette.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author vdwiv3
 *
 */

@Document(collection = "region")
public class CityDocument {

	@Id
	private String id;
	
	private String name;
	
	@DBRef
	private ImagesDocument imagesDocument;
	
	@DBRef
	private List<BrandDocument> brandDocuments;

	public CityDocument() {
		super();
	}

	public CityDocument(String id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the imagesDocument
	 */
	public ImagesDocument getImagesDocument() {
		return imagesDocument;
	}

	/**
	 * @param imagesDocument
	 *            the imagesDocument to set
	 */
	public void setImagesDocument(ImagesDocument imagesDocument) {
		this.imagesDocument = imagesDocument;
	}

	/**
	 * @return the brandDocuments
	 */
	public List<BrandDocument> getBrandDocuments() {
		if(null == brandDocuments)
			brandDocuments = new ArrayList<BrandDocument>();
		
		return brandDocuments;
	}

	/**
	 * @param brandDocuments
	 *            the brandDocuments to set
	 */
	public void setBrandDocuments(List<BrandDocument> brandDocuments) {
		this.brandDocuments = brandDocuments;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
