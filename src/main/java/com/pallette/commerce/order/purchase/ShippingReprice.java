package com.pallette.commerce.order.purchase;

import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.pallette.commerce.contants.CommerceConstants;
import com.pallette.domain.Order;
import com.pallette.domain.ShippingGroup;
import com.pallette.domain.ShippingPriceInfo;

@Component
public class ShippingReprice implements RepriceChain, Ordered{
	
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
				shipPriceInfo.setCurrencyCode(CommerceConstants.INR);
				shippingGroup.setShippingPriceInfo(shipPriceInfo);
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
