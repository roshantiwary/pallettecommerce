package com.pallette.browse.response;

import java.util.List;

import com.pallette.response.Response;

/**
 * 
 * @author amall3
 *
 */
public class CategoryResponseBean extends Response {

	private List<CategoryResponse> categoryResponseList;

	/**
	 * @return the categoryResponseList
	 */
	public List<CategoryResponse> getCategoryResponseList() {
		return categoryResponseList;
	}

	/**
	 * @param categoryResponseList
	 *            the categoryResponseList to set
	 */
	public void setCategoryResponseList(
			List<CategoryResponse> categoryResponseList) {
		this.categoryResponseList = categoryResponseList;
	}

}
