package com.pallette.commerce.order.purchase;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pallette.domain.Order;

@Component
public class OrderRepriceChain {
	
	@Autowired
	private List<RepriceChain> repricingChains;
	
	public void reprice(Order order) {
		 for (RepriceChain chain : repricingChains) {
             chain.reprice(order);
     }
	}
}
