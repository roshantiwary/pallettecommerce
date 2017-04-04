package com.pallette.commerce.order.purchase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.pallette.commerce.contants.CommerceConstants;
import com.pallette.domain.CommerceItem;
import com.pallette.domain.ItemPriceInfo;
import com.pallette.domain.Order;
import com.pallette.domain.SkuDocument;
import com.pallette.repository.ProductRepository;

@Component
public class ItemReprice implements RepriceChain, Ordered{

	private RepriceChain repriceChain;
	
	/**
	 * The Product repository.
	 */
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private MongoOperations mongoOperation;
	
	@Override
	public void setNextChain(RepriceChain nextChain) {
		this.repriceChain = nextChain;
	}

	@Override
	public void reprice(Order order) {
		//calculate item price
		
		List<CommerceItem> items = order.getCommerceItems();
		if (null == items)
			return;
		
		for (CommerceItem commerceItem : items) {
			Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(commerceItem.getCatalogRefId()));
			SkuDocument skuItem = mongoOperation.findOne(query, SkuDocument.class);
			ItemPriceInfo itemPriceInfo = new ItemPriceInfo();
			itemPriceInfo.setOnSale(Boolean.FALSE);
			itemPriceInfo.setDiscounted(Boolean.FALSE);
			itemPriceInfo.setCurrencyCode(CommerceConstants.INR);
			long quantity = commerceItem.getQuantity();
			
			itemPriceInfo.setListPrice(skuItem.getPriceDocument().getListPrice());
			itemPriceInfo.setSalePrice(skuItem.getPriceDocument().getSalePrice());
			
			double amount = skuItem.getPriceDocument().getSalePrice() * quantity;
			itemPriceInfo.setAmount(amount);
			itemPriceInfo.setRawTotalPrice(amount);
			
			commerceItem.setItemPriceInfo(itemPriceInfo);
		}
		
		//invoke next chain
		//this.repriceChain.reprice(order);
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return Ordered.HIGHEST_PRECEDENCE;
	}

	
}
