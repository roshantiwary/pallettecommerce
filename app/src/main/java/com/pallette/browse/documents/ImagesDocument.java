/**
 * 
 */
package com.pallette.browse.documents;

import org.springframework.data.annotation.Id;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author amall3
 *
 */

@Document(collection = "image")
public class ImagesDocument implements Serializable {
	
	
	private int version;

    @Id
    private String id;

    private Date timeCreated;

    public ImagesDocument() {
        this(UUID.randomUUID());
    }

    public ImagesDocument(UUID guid) {
        Assert.notNull(guid, "UUID is required");
        id = guid.toString();
        this.timeCreated = new Date();
    }

    public ImagesDocument(String guid) {
        Assert.notNull(guid, "UUID is required");
        id = guid;
        this.timeCreated = new Date();
    }

    public String getId() {
        return id;
    }

    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImagesDocument that = (ImagesDocument) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    public int getVersion() {
        return version;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }
    
	@Field(value = "thumbnail_image_url")
	private String thumbnailImageUrl;

	
	@Field(value = "small_image_url")
	private String smallImageUrl;

	@Field(value = "large_image_url")
	private String largeImageUrl;

	@Field(value = "image_availablity")
	private boolean imageAvailablity;

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
