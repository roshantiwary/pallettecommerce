/**
 * 
 */
package com.pallette.response;

import java.util.Map;

/**
 * @author amall3
 *
 */
public class AddEditAddressResponse extends Response {

	private Map<String, AddressResponse> dataMap;

	/**
	 * @return the dataMap
	 */
	public Map<String, AddressResponse> getDataMap() {
		return dataMap;
	}

	/**
	 * @param dataMap
	 *            the dataMap to set
	 */
	public void setDataMap(Map<String, AddressResponse> dataMap) {
		this.dataMap = dataMap;
	}

}
