package com.pallette.commerce.order.purchase;

import java.util.List;

import org.springframework.stereotype.Component;

import com.pallette.commerce.contants.CommerceContants;
import com.pallette.domain.Order;
import com.pallette.domain.ShippingGroup;
import com.pallette.domain.ShippingPriceInfo;

@Component
public class ShippingReprice implements RepriceChain{
	
	private RepriceChain repriceChain;
	
	@Override
	public void setNextChain(RepriceChain nextChain) {
		this.repriceChain = nextChain;
	}

	@Override
	public void reprice(Order order) {
		//calculate shipping price
		List<ShippingGroup> shippingGroups = order.getShippingGroups();
		for (ShippingGroup shippingGroup : shippingGroups) {
			if(shippingGroup.getShippingGroupType().equalsIgnoreCase("HardGoodShippingGroup")) {
				ShippingPriceInfo shipPriceInfo = new ShippingPriceInfo();
				shipPriceInfo.setAmount(0.0);
				shipPriceInfo.setRawShipping(0.0);
				shipPriceInfo.setCurrencyCode(CommerceContants.INR);
				shippingGroup.setShippingPriceInfo(shipPriceInfo);
			}
		}
		
		//invoke next chain
		this.repriceChain.reprice(order);
	}
}
