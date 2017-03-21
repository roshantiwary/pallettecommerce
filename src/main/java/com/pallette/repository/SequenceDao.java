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
	
	String getNextSequenceId(String key) throws SequenceException;

}
