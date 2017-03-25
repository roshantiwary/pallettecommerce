/**
 * 
 */
package com.pallette.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pallette.commerce.order.purchase.OrderRepriceChain;
import com.pallette.constants.SequenceConstants;
import com.pallette.domain.Account;
import com.pallette.domain.Address;
import com.pallette.domain.CommerceItem;
import com.pallette.domain.Order;
import com.pallette.domain.PaymentGroup;
import com.pallette.domain.PaymentStatus;
import com.pallette.domain.ProductDocument;
import com.pallette.domain.ShippingGroup;
import com.pallette.exception.NoRecordsFoundException;
import com.pallette.repository.AccountRepository;
import com.pallette.repository.OrderRepository;
import com.pallette.repository.ProductRepository;
import com.pallette.repository.SequenceDao;

/**
 * <p>
 * Service class for product repository operations. This class includes methods
 * that perform database operations related to product.
 * </p>
 * 
 * @author amall3
 *
 */

@Service
public class OrderService {

	private static final String CREDIT_CARD = "creditCard";

	private static final String HARD_GOOD_SHIPPING_GROUP = "HardGoodShippingGroup";

	private static final String PALLETTE = "Pallette";

	private static final String WEB = "web";

	private static final String DEFAULT_ORDER_TYPE = "defaultOrderType";

	private static final String DEFAULT_CATALOG = "defaultCatalog";

	private static final String INITIAL = "initial";

	private static final String DEFAULT_COMMERCE_ITEM = "defaultCommerceItem";

	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
	
	/**
	 * The Order repository.
	 */
	@Autowired
	private OrderRepository orderRepository;
	
	/**
	 * The Product repository.
	 */
	@Autowired
	private ProductRepository productRepository;
	
	/**
	 * The Product repository.
	 */
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private SequenceDao sequenceDao;
	
	@Autowired
	OrderRepriceChain orderRepriceChain;

	
	/**
	 * 
	 * @param cartId
	 * @param productId
	 * @param quantity
	 * @return 
	 * @throws NoRecordsFoundException 
	 */
	public Order updateItemQuantity(String orderId, String productId, long newQuantity) throws NoRecordsFoundException {
		
		logger.debug("Inside OrderService.updateItemQuantity()");
		logger.debug("The Passed In Order id : " + orderId + " Product Id :" + productId + "Quantity :" + productId);
		
		Order order = orderRepository.findOne(orderId);
		if(null == order)
			throw new NoRecordsFoundException("No Order Found to Update.");
		
		ProductDocument prodItem = productRepository.findOne(productId);
		if(null == prodItem)
			throw new NoRecordsFoundException("No Product Found to Update.");
		
		CommerceItem itemToUpdate = getItemFromOrder(productId, order);
		if (null == itemToUpdate)
			throw new NoRecordsFoundException("No Commerce Item Found to Update.");
		
		itemToUpdate.setQuantity(newQuantity);
		
		orderRepriceChain.reprice(order);
		
		return orderRepository.save(order);
	}

	/**
	 * @param productId
	 * @param order
	 * @return
	 */
	private CommerceItem getItemFromOrder(String productId, Order order) {
		List<CommerceItem> items = order.getCommerceItems();
		for (CommerceItem item : items) {
			if (productId.equalsIgnoreCase(item.getCatalogRefId())) {
				return item;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @return 
	 * @return
	 * @throws NoRecordsFoundException 
	 */
	public Order createOrder(String productId , long quantity , String profileId) throws NoRecordsFoundException {
		
		logger.debug("Inside OrderServices.");
		ProductDocument prodItem = productRepository.findOne(productId);
		if(null == prodItem)
			throw new NoRecordsFoundException("No Product Found while creating order.");
		
		//Create new Order Object an set the default values.
		Order order = initializeOrder();
		
		//Create new ShippingGroup Object an set the default values.
		initializeShippingGroup(order);
		
		//Create new PaymentGroup Object an set the default values.
		initializePaymentGroup(order);
		
		//Create new CommerceItem Object an set the default values.
		CommerceItem commerceItem = createAndPopulateCommerceItem(quantity, prodItem);
		order.addCommerceItem(commerceItem);
		
		//Need to add the encrypt/decrpt logic.
		Account profile = accountRepository.findOne(profileId);
		order.setProfileId(profileId);
		if (null == profile) {
			order.setTransient(Boolean.TRUE);
		} else {
			order.setTransient(Boolean.FALSE);
		}
		
		orderRepriceChain.reprice(order);
		
		return orderRepository.save(order);

	}

	private Order initializeOrder() {
		Order order = new Order();
		order.setId(sequenceDao.getNextOrderSequenceId(SequenceConstants.SEQ_KEY));
		order.setCreatedDate(new Date());
		order.setState(INITIAL);
		order.setOrderType(DEFAULT_ORDER_TYPE);
		order.setOriginOfOrder(WEB);
		order.setSiteId(PALLETTE);
		return order;
	}

	private void initializeShippingGroup(Order order) {
		ShippingGroup shipGrp = new ShippingGroup();
		shipGrp.setShippingGroupType(HARD_GOOD_SHIPPING_GROUP);
		shipGrp.setState(INITIAL);
		
		Address address = new Address();
		shipGrp.setAddress(address);
		
		order.addShippingGroup(shipGrp);
	}

	private void initializePaymentGroup(Order order) {
		PaymentGroup payGrp = new PaymentGroup();
		payGrp.setState(INITIAL);
		payGrp.setPaymentGroupType(CREDIT_CARD);
		payGrp.setPaymentMethod(CREDIT_CARD);
		
		PaymentStatus paymentStatus = new PaymentStatus();
		List<PaymentStatus> paymentStatusList = new ArrayList<PaymentStatus>();
		payGrp.setDebitStatus(paymentStatusList);
		payGrp.setCreditStatus(paymentStatusList);
		payGrp.setAuthorizationStatus(paymentStatusList);
		order.addPaymentGroup(payGrp);
	}

	/**
	 * @param productId
	 * @param quantity
	 * @param prodDoc
	 * @return
	 */
	private CommerceItem createAndPopulateCommerceItem(long quantity, ProductDocument prodDoc) {
		
		CommerceItem commerceItem = new CommerceItem();
		commerceItem.setCatalogId(DEFAULT_CATALOG);
		commerceItem.setCatalogRefId(prodDoc.getId());
		commerceItem.setCommerceItemType(DEFAULT_COMMERCE_ITEM);
		commerceItem.setQuantity(quantity);
		commerceItem.setState(INITIAL);
		commerceItem.setDescription(prodDoc.getProductDescription());
		
		return commerceItem;
	}

	/**
	 * @return the productRepository
	 */
	public ProductRepository getProductRepository() {
		return productRepository;
	}

	/**
	 * @param productRepository the productRepository to set
	 */
	public void setProductRepository(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

}
