package com.pallette.commerce.order.purchase;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;

import com.pallette.domain.Order;

@Component
public class OrderRepriceChain {
	
	@Autowired
	private List<RepriceChain> repricingChains;
	
	@PostConstruct
	public void init() {
         Collections.sort(repricingChains, AnnotationAwareOrderComparator.INSTANCE);
	}
	
	public void reprice(Order order) {
		 for (RepriceChain chain : repricingChains) {
//			 System.out.println(chain.getClass().getName());
             chain.reprice(order);
     }
	}
}
