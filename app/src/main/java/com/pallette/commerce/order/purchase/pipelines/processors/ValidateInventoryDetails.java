/**
 * 
 */
package com.pallette.commerce.order.purchase.pipelines.processors;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pallette.browse.documents.InventoryDocument;
import com.pallette.browse.documents.SkuDocument;
import com.pallette.commerce.contants.CommerceConstants;
import com.pallette.domain.CommerceItem;
import com.pallette.domain.Order;

/**
 * @author amall3
 *
 */
@Component
public class ValidateInventoryDetails extends ValidateOrderForCheckout {

	private static final Logger log = LoggerFactory.getLogger(ValidateInventoryDetails.class);
	
	@Autowired
	private MongoOperations mongoOperation;

	@Override
	public int getOrder() {
		return 2;
	}

	@Override
	public boolean validate(Order order) {
		
		log.debug("Inside ValidateInventoryDetails.validate()");
		boolean isValidationSuccess=  Boolean.FALSE;
		
		List<CommerceItem> commerceItemList = order.getCommerceItems();

		if (null == commerceItemList)
			return isValidationSuccess;

		if (commerceItemList.isEmpty())
			return isValidationSuccess;
			
		for (CommerceItem commerceItem : commerceItemList) {
			
			String skuId = commerceItem.getCatalogRefId();
			Query findSkuQuery = new Query();
			findSkuQuery.addCriteria(Criteria.where(CommerceConstants._ID).is(skuId));
			log.debug("Find Sku Query : ", findSkuQuery);
			
			SkuDocument skuItem = mongoOperation.findById(findSkuQuery, SkuDocument.class);
			if (null == skuItem)
				return isValidationSuccess;
			
			InventoryDocument inventory = skuItem.getInventoryDocument();
			if (null == inventory)
				return isValidationSuccess;
			
			if(!StringUtils.isEmpty(inventory.getStockStatus()) && (inventory.getStockStatus()).equalsIgnoreCase(CommerceConstants.STRING_TRUE)){
				
				long availableStock = inventory.getAvailableStockLevel();
				log.debug("Available Stock for sku :" + skuId + " is :" + availableStock );
				long reservedStock = inventory.getReservedStockLevel();
				log.debug("Reserved Stock for sku :" + skuId + " is :" + reservedStock );
				long remainingStock = Math.subtractExact(availableStock, reservedStock);
				log.debug("Remaining Stock for sku :" + skuId + " is :" + remainingStock );
				log.debug("Commerce Item Quantity is : " + skuId + commerceItem.getQuantity() );
				if (remainingStock > commerceItem.getQuantity()) {
					isValidationSuccess = Boolean.TRUE;
				}
			}
		}
		return isValidationSuccess;
	}

}
