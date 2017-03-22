package com.pallette.commerce.order.purchase;

import com.pallette.domain.Order;

public interface RepriceChain {
	
	void setNextChain(RepriceChain nextChain);
	
	void reprice(Order order);

}
