/**
 * 
 */
package com.pallette.response;

import java.util.List;
import java.util.Map;

/**
 * @author amall3
 *
 */
public class GetAddressResponse extends Response {

	private Map<String, List<AddressResponse>> dataMap;

	/**
	 * @return the dataMap
	 */
	public Map<String, List<AddressResponse>> getDataMap() {
		return dataMap;
	}

	/**
	 * @param dataMap
	 *            the dataMap to set
	 */
	public void setDataMap(Map<String, List<AddressResponse>> dataMap) {
		this.dataMap = dataMap;
	}

}
