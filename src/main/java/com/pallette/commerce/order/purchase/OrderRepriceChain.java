package com.pallette.commerce.order.purchase;

import org.springframework.stereotype.Component;

import com.pallette.domain.Order;

@Component
public class OrderRepriceChain {

	private RepriceChain chain1;
	
	public OrderRepriceChain() {
		// initialize chain
		this.chain1 = new ItemReprice();
		RepriceChain chain2 = new ShippingReprice();
		RepriceChain chain3 = new OrderReprice();
		
		//set the chain of responsibility
		chain1.setNextChain(chain2);
		chain2.setNextChain(chain3);
	}
	
	public void reprice(Order order) {
		OrderRepriceChain orderReprice = new OrderRepriceChain();
		orderReprice.chain1.reprice(order);
	}
}
