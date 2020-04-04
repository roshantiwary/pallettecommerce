package com.pallette.browse.documents;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.pallette.persistence.BaseEntity;

/**
 * @author vdwiv3
 *
 */

@Document(collection = "region")
public class CityDocument extends BaseEntity {

	private String name;

	@DBRef
	private ImagesDocument imagesDocument;

	@DBRef
	private List<BrandDocument> brandDocuments;

	public CityDocument() {
		super();
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
		if (null == brandDocuments)
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
