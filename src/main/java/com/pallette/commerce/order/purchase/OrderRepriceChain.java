package com.pallette.commerce.order.purchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pallette.domain.Order;

@Component
public class OrderRepriceChain {
	
	@Autowired
	private ItemReprice chain1;
	
	@Autowired
	private ShippingReprice chain2;
	
	@Autowired
	private OrderReprice chain3;
	
	@Autowired
	OrderRepriceChain orderReprice;
	
//	public OrderRepriceChain() {
//		// initialize chain
////		this.chain1 = new ItemReprice();
////		RepriceChain chain2 = new ShippingReprice();
////		RepriceChain chain3 = new OrderReprice();
//		
//		//set the chain of responsibility
//		chain1.setNextChain(chain2);
//		chain2.setNextChain(chain3);
//	}
	
	public void reprice(Order order) {
		orderReprice.chain1.reprice(order);
	}
}
