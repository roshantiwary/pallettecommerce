/**
 * 
 */
package com.pallette.commerce.order.purchase.pipelines.processors;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pallette.domain.CommerceItem;
import com.pallette.domain.ItemPriceInfo;
import com.pallette.domain.Order;

/**
 * @author amall3
 *
 */
@Component
public class ValidateCartItems extends ValidateOrderForCheckout {
	
	private static final Logger log = LoggerFactory.getLogger(ValidateCartItems.class);

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}

	@Override
	public boolean validate(Order order) {
		
		log.debug("Inside ValidateCartItems.validate()");
		boolean isValidationSuccess=  Boolean.FALSE;
		
		List<CommerceItem> commerceItemList = order.getCommerceItems();

		if (null == commerceItemList)
			return isValidationSuccess;

		if (commerceItemList.isEmpty())
			return isValidationSuccess;
			
		for (CommerceItem commerceItem : commerceItemList) {
			
			double quantity = commerceItem.getQuantity();
			if (quantity < 1) 
				return isValidationSuccess;
			
			Long skuId = commerceItem.getCatalogRefId();
			if (StringUtils.isEmpty(skuId)) {
				return isValidationSuccess;
			}
			
			Long productId = commerceItem.getProductId();
			if (StringUtils.isEmpty(productId)) {
				return isValidationSuccess;
			}
			
			ItemPriceInfo itemPriceInfo = commerceItem.getItemPriceInfo();
			if (null == itemPriceInfo)
				return isValidationSuccess;
			
			if(itemPriceInfo.getAmount() < 0.0){
				return isValidationSuccess;
			}
				
		}
		isValidationSuccess = Boolean.TRUE;
		return isValidationSuccess;
	}

	
}
