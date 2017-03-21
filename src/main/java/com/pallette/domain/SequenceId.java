/**
 * 
 */
package com.pallette.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>
 * Sequence document created for maintaining sequential order id.
 * </p>
 * 
 * @author amall3
 *
 */
@Document(collection = "sequence")
public class SequenceId {

	@Id
	private String id;

	private long seq;

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
	 * @return the seq
	 */
	public long getSeq() {
		return seq;
	}

	/**
	 * @param seq
	 *            the seq to set
	 */
	public void setSeq(long seq) {
		this.seq = seq;
	}

}