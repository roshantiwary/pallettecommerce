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

import com.pallette.commerce.contants.CommerceContants;
import com.pallette.commerce.order.purchase.OrderRepriceChain;
import com.pallette.constants.SequenceConstants;
import com.pallette.domain.Account;
import com.pallette.domain.Address;
import com.pallette.domain.CommerceItem;
import com.pallette.domain.ItemPriceInfo;
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

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

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

	private static final Logger log = LoggerFactory.getLogger(OrderService.class);
	
	/**
	 * The Order repository.
	 */
	@Autowired
	private OrderRepository orderRepository;
	
	/**
	 * The Product Repository.
	 */
	@Autowired
	private ProductRepository productRepository;
	
	/**
	 * The Account Repository.
	 */
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private SequenceDao sequenceDao;
	
	@Autowired
	OrderRepriceChain orderRepriceChain;
	
	@Autowired
	private MongoOperations mongoOperation;
	
	/**
	 * 
	 * @param productId
	 * @param quantity
	 * @param profileId
	 * @param orderId 
	 * @return
	 * @throws NoRecordsFoundException 
	 */
	public Order addItemToOrder(String productId, long quantity, String profileId, String orderId) throws NoRecordsFoundException {
		log.debug("Inside OrderService.addItemToOrder()");
		
		Order order = orderRepository.findOne(orderId);
		if(null == order)
			throw new NoRecordsFoundException("No Order Found to Update.");
		
		ProductDocument prodItem = productRepository.findOne(productId);
		if(null == prodItem)
			throw new NoRecordsFoundException("No Product Found to Update.");
		
		CommerceItem item = getItemFromOrder(productId, order);
		if (null == item) {

			// Create new CommerceItem Object an set the default values.
			CommerceItem commerceItem = createAndPopulateCommerceItem(quantity, prodItem);
			order.addCommerceItem(commerceItem);

		}
		
		orderRepriceChain.reprice(order);
		return orderRepository.save(order);
	}
	
	/**
	 * 
	 * @param orderId
	 * @param productId
	 * @return
	 * @throws NoRecordsFoundException 
	 */
	public CommerceItem removeItemFromOrder(String orderId, String productId) throws NoRecordsFoundException {
		log.debug("Inside OrderService.removeItemFromOrder()");
		
		Order order = orderRepository.findOne(orderId);
		if(null == order)
			throw new NoRecordsFoundException("No Order Found to Update.");
		
		ProductDocument prodItem = productRepository.findOne(productId);
		if(null == prodItem)
			throw new NoRecordsFoundException("No Product Found to Update.");
		
		CommerceItem itemToRemove = getItemFromOrder(productId, order);
		if (null == itemToRemove)
			throw new NoRecordsFoundException("No Commerce Item Found to Update.");
		
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(itemToRemove.getId()));

		ItemPriceInfo childItemPriceInfo = itemToRemove.getItemPriceInfo();
		if (null != childItemPriceInfo) {
			Query itemPriceRemovalQuery = new Query();
			itemPriceRemovalQuery.addCriteria(Criteria.where("_id").is(childItemPriceInfo.getId()));
			ItemPriceInfo itemPrice = mongoOperation.findAndRemove(itemPriceRemovalQuery , ItemPriceInfo.class);
			log.debug("Item Price Info Removed Successfully :" , itemPrice.getId());
		}
		
		CommerceItem commerceItem = mongoOperation.findAndRemove(query, CommerceItem.class);
		log.debug("Item Removed Successfully :" , commerceItem.getId());
		orderRepriceChain.reprice(order);
		order.removeCommerceItem(itemToRemove);
		orderRepository.save(order);
		return commerceItem;
	}

	
	/**
	 * 
	 * @param cartId
	 * @param productId
	 * @param quantity
	 * @return 
	 * @throws NoRecordsFoundException 
	 */
	public Order updateItemQuantity(String orderId, String productId, long newQuantity) throws NoRecordsFoundException {
		
		log.debug("Inside OrderService.updateItemQuantity()");
		log.debug("The Passed In Order id : " + orderId + " Product Id :" + productId + "Quantity :" + productId);
		
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
	 * Method for creating order with default values.
	 * 
	 * @param profileId
	 * @return
	 */
	public Order createDefaultOrder(String profileId) {

		log.debug("Inside OrderServices.createDefaultOrder()");
		log.debug("The profile passed is " + profileId);

		// Create new Order Object an set the default values.
		Order order = initializeOrder();

		// Create new ShippingGroup Object an set the default values.
		initializeShippingGroup(order);

		// Create new PaymentGroup Object an set the default values.
		initializePaymentGroup(order);

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
	
	
	/**
	 * 
	 * @return 
	 * @return
	 * @throws NoRecordsFoundException 
	 */
	public Order createOrder(String productId , long quantity , String profileId) throws NoRecordsFoundException {
		
		log.debug("Inside OrderServices.");
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
		order.setState(CommerceContants.INITIAL);
		order.setOrderType(CommerceContants.DEFAULT_ORDER_TYPE);
		order.setOriginOfOrder(CommerceContants.WEB);
		order.setSiteId(CommerceContants.PALLETTE);
		return order;
	}

	private void initializeShippingGroup(Order order) {
		ShippingGroup shipGrp = new ShippingGroup();
		shipGrp.setShippingGroupType(CommerceContants.HARD_GOOD_SHIPPING_GROUP);
		shipGrp.setState(CommerceContants.INITIAL);
		
		Address address = new Address();
		shipGrp.setAddress(address);
		
		order.addShippingGroup(shipGrp);
	}

	private void initializePaymentGroup(Order order) {
		PaymentGroup payGrp = new PaymentGroup();
		payGrp.setState(CommerceContants.INITIAL);
		payGrp.setPaymentGroupType(CommerceContants.CREDIT_CARD);
		payGrp.setPaymentMethod(CommerceContants.CREDIT_CARD);
		
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
		commerceItem.setCatalogId(CommerceContants.DEFAULT_CATALOG);
		commerceItem.setCatalogRefId(prodDoc.getId());
		commerceItem.setCommerceItemType(CommerceContants.DEFAULT_COMMERCE_ITEM);
		commerceItem.setQuantity(quantity);
		commerceItem.setState(CommerceContants.INITIAL);
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
