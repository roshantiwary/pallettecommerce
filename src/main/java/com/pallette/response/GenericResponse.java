/**
 * 
 */
package com.pallette.response;

import java.util.Collection;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * <p>
 * A simple POJO to send JSON response to HTTP requests. This POJO enables us to
 * send messages and error codes with the actual objects in the application.
 * </p>
 * 
 * @author amall3
 *
 */

@Component
public class GenericResponse {

	/**
	 * An array that contains the actual objects
	 */
	private Collection items;

	/**
	 * An Map that contains the actual objects
	 */
	private Map<String, Object> mapData;

	/**
	 * A HttpStatus containing error code.
	 */
	private int statusCode;

	/**
	 * A String containing error message.
	 */
	private String message;

	/**
	 * A integer containing error message.
	 */
	private int itemCount;
	
	/**
	 * An Map that contains the Objects with one to many Mapping Data
	 */
	private Map<Object, Collection> itemMapData;


	/**
	 * @return the itemCount
	 */
	public int getItemCount() {
		return itemCount;
	}

	/**
	 * @param itemCount the itemCount to set
	 */
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	/**
	 * An array that contains the actual objects
	 * 
	 * @return the rows
	 */
	public Collection getItems() {
		return items;
	}

	/**
	 * An array that contains the actual objects
	 * 
	 * @param rows
	 *            the rows to set
	 */
	public void setItems(Collection items) {
		this.items = items;
	}

	/**
	 * A String containing error code.
	 * 
	 * @return the errorCode
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * A String containing error code.
	 * 
	 * @param status
	 *            the errorCode to set
	 */
	public void setStatusCode(int status) {
		this.statusCode = status;
	}

	/**
	 * A String containing error message.
	 * 
	 * @return the errorMessage
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * A String containing error message.
	 * 
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setMessage(String errorMessage) {
		this.message = errorMessage;
	}

	/**
	 * @return the mapData
	 */
	public Map<String, Object> getMapData() {
		return mapData;
	}

	/**
	 * @param mapData the mapData to set
	 */
	public void setMapData(Map<String, Object> mapData) {
		this.mapData = mapData;
	}

	/**
	 * @return the itemMapData
	 */
	public Map<Object, Collection> getItemMapData() {
		return itemMapData;
	}

	/**
	 * @param mapData the itemMapData to set
	 */
	public void setItemMapData(Map<Object, Collection> itemMapData) {
		this.itemMapData = itemMapData;
	}


}
