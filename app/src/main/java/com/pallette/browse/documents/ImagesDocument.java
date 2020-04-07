/**
 * 
 */
package com.pallette.browse.documents;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.pallette.persistence.BaseEntity;

/**
 * @author amall3
 *
 */

@Document(collection = "image")
public class ImagesDocument extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2035297835744215133L;


	@Field(value = "thumbnail_image_url")
	private String thumbnailImageUrl;

	
	@Field(value = "small_image_url")
	private String smallImageUrl;

	@Field(value = "large_image_url")
	private String largeImageUrl;

	@Field(value = "image_availablity")
	private boolean imageAvailablity;

	public ImagesDocument(String id) {
		super(id);
	}

	public ImagesDocument() {
		super();
	}

	/**
	 * @return the thumbnailImageUrl
	 */
	public String getThumbnailImageUrl() {
		return thumbnailImageUrl;
	}

	/**
	 * @param thumbnailImageUrl
	 *            the thumbnailImageUrl to set
	 */
	public void setThumbnailImageUrl(String thumbnailImageUrl) {
		this.thumbnailImageUrl = thumbnailImageUrl;
	}

	/**
	 * @return the smallImageUrl
	 */
	public String getSmallImageUrl() {
		return smallImageUrl;
	}

	/**
	 * @param smallImageUrl
	 *            the smallImageUrl to set
	 */
	public void setSmallImageUrl(String smallImageUrl) {
		this.smallImageUrl = smallImageUrl;
	}

	/**
	 * @return the largeImageUrl
	 */
	public String getLargeImageUrl() {
		return largeImageUrl;
	}

	/**
	 * @param largeImageUrl
	 *            the largeImageUrl to set
	 */
	public void setLargeImageUrl(String largeImageUrl) {
		this.largeImageUrl = largeImageUrl;
	}

	/**
	 * @return the imageAvailablity
	 */
	public boolean isImageAvailablity() {
		return imageAvailablity;
	}

	/**
	 * @param imageAvailablity
	 *            the imageAvailablity to set
	 */
	public void setImageAvailablity(boolean imageAvailablity) {
		this.imageAvailablity = imageAvailablity;
	}

}
