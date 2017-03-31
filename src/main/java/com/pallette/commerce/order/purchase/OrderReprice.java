package com.pallette.commerce.order.purchase;

import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.pallette.commerce.contants.CommerceContants;
import com.pallette.domain.CommerceItem;
import com.pallette.domain.Order;
import com.pallette.domain.OrderPriceInfo;

@Component
public class OrderReprice implements RepriceChain, Ordered{
	
	private RepriceChain repriceChain;
	
	@Override
	public void setNextChain(RepriceChain nextChain) {
		this.repriceChain = nextChain;
	}

	@Override
	public void reprice(Order order) {
		//calculate Order price
		
		OrderPriceInfo orderPriceInfo = new OrderPriceInfo();
		orderPriceInfo.setCurrencyCode(CommerceContants.INR);
		orderPriceInfo.setDiscounted(Boolean.FALSE);
		
		double orderTotal = 0.0;
		
		List<CommerceItem> items = order.getCommerceItems();
		if (null != items && !items.isEmpty()) {
			for (CommerceItem item : order.getCommerceItems()) {
				orderTotal = orderTotal + item.getItemPriceInfo().getAmount();
			}
		} else {
			orderTotal = 0.0;
		}
	
		orderPriceInfo.setAmount(orderTotal);
		orderPriceInfo.setRawSubTotal(orderTotal);
		orderPriceInfo.setShipping(0.0);
		orderPriceInfo.setTax(0.0);
		
		order.setOrderPriceInfo(orderPriceInfo);
		
		//invoke next chain
//		this.repriceChain.reprice(order);
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return Ordered.LOWEST_PRECEDENCE;
	}
}
