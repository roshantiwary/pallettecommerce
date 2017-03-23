package com.pallette.commerce.order.purchase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pallette.commerce.contants.CommerceContants;
import com.pallette.domain.CommerceItem;
import com.pallette.domain.ItemPriceInfo;
import com.pallette.domain.Order;
import com.pallette.domain.ProductDocument;
import com.pallette.repository.ProductRepository;

@Component
public class ItemReprice implements RepriceChain{

	private RepriceChain repriceChain;
	
	/**
	 * The Product repository.
	 */
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public void setNextChain(RepriceChain nextChain) {
		this.repriceChain = nextChain;
	}

	@Override
	public void reprice(Order order) {
		//calculate item price
		
		List<CommerceItem> items = order.getCommerceItems();
		for (CommerceItem commerceItem : items) {
			ItemPriceInfo itemPriceInfo = new ItemPriceInfo();
			itemPriceInfo.setOnSale(Boolean.FALSE);
			itemPriceInfo.setDiscounted(Boolean.FALSE);
			itemPriceInfo.setCurrencyCode(CommerceContants.INR);
			String productId = commerceItem.getCatalogRefId();
			long quantity = commerceItem.getQuantity();
			
			ProductDocument prodItem = productRepository.findOne(productId);
			
			itemPriceInfo.setListPrice(prodItem.getPriceDocument().getListPrice());
			itemPriceInfo.setSalePrice(prodItem.getPriceDocument().getSalePrice());
			
			double amount = prodItem.getPriceDocument().getSalePrice() * quantity;
			itemPriceInfo.setAmount(amount);
			itemPriceInfo.setRawTotalPrice(amount);
			
			commerceItem.setItemPriceInfo(itemPriceInfo);
		}
		
		//invoke next chain
		this.repriceChain.reprice(order);
	}

	
}
