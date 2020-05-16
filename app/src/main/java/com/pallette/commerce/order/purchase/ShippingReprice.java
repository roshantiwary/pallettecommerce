package com.pallette.commerce.order.purchase;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.pallette.commerce.contants.CommerceConstants;
import com.pallette.domain.DeliveryMethod;
import com.pallette.domain.Order;
import com.pallette.domain.ShippingGroup;
import com.pallette.domain.ShippingPriceInfo;

@Component
public class ShippingReprice implements RepriceChain, Ordered{
	
	private RepriceChain repriceChain;
	
	@Autowired
	private MongoOperations mongoOperation;
	
	private static final Logger log = LoggerFactory.getLogger(ShippingReprice.class);
	
	@Override
	public void setNextChain(RepriceChain nextChain) {
		this.repriceChain = nextChain;
	}

	@Override
	public void reprice(Order order) {
		//calculate shipping price
		List<ShippingGroup> shippingGroups = order.getShippingGroups();
		if(shippingGroups != null) {
		for (ShippingGroup shippingGroup : shippingGroups) {
			if(shippingGroup != null && shippingGroup.getShippingGroupType().equalsIgnoreCase("HardGoodShippingGroup")) {
				
				ShippingPriceInfo shipPriceInfo = new ShippingPriceInfo();
				
				String deliveryMethod = shippingGroup.getShippingGroupMethod();
				Query deliveryMethodQuery = new Query(Criteria.where(CommerceConstants._ID).is(deliveryMethod));
				log.debug("Query to be executed is :", deliveryMethodQuery);
				DeliveryMethod deliveryMethodItem = mongoOperation.findOne(deliveryMethodQuery, DeliveryMethod.class);

				if (null == deliveryMethod) {
					shipPriceInfo.setAmount(0.0);
					shipPriceInfo.setRawShipping(0.0);
				} else {
					shipPriceInfo.setAmount(deliveryMethodItem.getConvenienceFee());
					shipPriceInfo.setRawShipping(deliveryMethodItem.getConvenienceFee());
				}

				shipPriceInfo.setCurrencyCode(CommerceConstants.INR);
				shippingGroup.setShippingPriceInfo(shipPriceInfo);
			}
		}
	}
		//invoke next chain
		//this.repriceChain.reprice(order);
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 1;
	}
}
