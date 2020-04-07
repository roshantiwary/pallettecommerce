/**
 * 
 */
package com.pallette.browse.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.pallette.browse.documents.SkuDocument;

/**
 * @author amall3
 *
 */
public interface SkuRepository extends PagingAndSortingRepository<SkuDocument, String> {

	
}
