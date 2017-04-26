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

@Document(collection = "inventory")
public class InventoryDocument extends BaseEntity {

	@Field(value = "available_stock_level")
	private long availableStockLevel;

	@Field(value = "reserved_stock_level")
	private long reservedStockLevel;

	@Field(value = "stock_status")
	private String stockStatus;

	public InventoryDocument() {
		super();
	}

	public InventoryDocument(String id) {
		super(id);
	}
	
	/**
	 * @return the availableStockLevel
	 */
	public long getAvailableStockLevel() {
		return availableStockLevel;
	}

	/**
	 * @param availableStockLevel
	 *            the availableStockLevel to set
	 */
	public void setAvailableStockLevel(long availableStockLevel) {
		this.availableStockLevel = availableStockLevel;
	}

	/**
	 * @return the reservedStockLevel
	 */
	public long getReservedStockLevel() {
		return reservedStockLevel;
	}

	/**
	 * @param reservedStockLevel
	 *            the reservedStockLevel to set
	 */
	public void setReservedStockLevel(long reservedStockLevel) {
		this.reservedStockLevel = reservedStockLevel;
	}

	/**
	 * @return the stockStatus
	 */
	public String getStockStatus() {
		return stockStatus;
	}

	/**
	 * @param stockStatus
	 *            the stockStatus to set
	 */
	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}

}
