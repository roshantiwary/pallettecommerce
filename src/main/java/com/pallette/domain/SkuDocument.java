/**
 * 
 */
package com.pallette.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


/**
 * @author vdwiv3
 *
 */
@Document(collection = "sku")
public class SkuDocument {

	@Id
	private String id;

	@Field(value = "name")
	private String name;
	
	@Field(value="default_display_name")
	private String defaultDisplayName;
	
	@Field(value="active")
	private boolean active;
	
	@Field(value="unit_of_measure")
	private String unitOfMeasure;
	
	@Field(value="returnable")
	private boolean returnable;
	
	@DBRef
	private PriceDocument priceDocument;
	
	@DBRef
	private InventoryDocument inventoryDocument;

	public SkuDocument() {
		super();
	}
	
	public SkuDocument(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDefaultDisplayName() {
		return defaultDisplayName;
	}

	public void setDefaultDisplayName(String defaultDisplayName) {
		this.defaultDisplayName = defaultDisplayName;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public boolean isReturnable() {
		return returnable;
	}

	public void setReturnable(boolean returnable) {
		this.returnable = returnable;
	}

	public PriceDocument getPriceDocument() {
		return priceDocument;
	}

	public void setPriceDocument(PriceDocument priceDocument) {
		this.priceDocument = priceDocument;
	}

	public InventoryDocument getInventoryDocument() {
		return inventoryDocument;
	}

	public void setInventoryDocument(InventoryDocument inventoryDocument) {
		this.inventoryDocument = inventoryDocument;
	}
	
}
