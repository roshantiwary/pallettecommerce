/**
 * 
 */
package com.pallette.repository;

import com.pallette.exception.SequenceException;

/**
 * @author amall3
 *
 */
public interface SequenceDao {

	/**
	 * 
	 * @param key
	 * @return
	 * @throws SequenceException
	 */
	public String getNextOrderSequenceId(String key) throws SequenceException;

	/**
	 * 
	 * @param key
	 * @return
	 * @throws SequenceException
	 */
	public String getNextProfileSequenceId(String key) throws SequenceException;
	
	/**
	 * 
	 * @param key
	 * @return
	 * @throws SequenceException
	 */
	public String getNextAddressSequenceId(String key) throws SequenceException;

}
