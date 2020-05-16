/**
 * 
 */
package com.pallette.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.pallette.beans.OrderHistoryResponse;
import com.pallette.beans.OrderResponse;
import com.pallette.browse.documents.BrandDocument;
import com.pallette.browse.documents.ImagesDocument;
import com.pallette.browse.documents.ProductDocument;
import com.pallette.browse.documents.SkuDocument;
import com.pallette.browse.repository.ProductRepository;
import com.pallette.commerce.contants.CommerceConstants;
import com.pallette.commerce.order.purchase.OrderRepriceChain;
import com.pallette.commerce.order.purchase.pipelines.processors.ValidateChain;
import com.pallette.constants.SequenceConstants;
import com.pallette.domain.Account;
import com.pallette.domain.Address;
import com.pallette.domain.CommerceItem;
import com.pallette.domain.ItemPriceInfo;
import com.pallette.domain.Order;
import com.pallette.domain.OrderPriceInfo;
import com.pallette.domain.PaymentGroup;
import com.pallette.domain.ShippingGroup;
import com.pallette.domain.ShippingPriceInfo;
import com.pallette.exception.NoRecordsFoundException;
import com.pallette.repository.AccountRepository;
import com.pallette.repository.OrderRepository;
import com.pallette.repository.SequenceDao;
import com.pallette.response.AddressResponse;
import com.pallette.response.CartItemResponse;
import com.pallette.response.CartResponse;
import com.pallette.response.OrderDetailResponse;
import com.pallette.response.OrderItemResponse;

/**
 * <p>
 * Service class for cart and order related operations. This class includes
 * methods that perform functionalities like add /update /remove operations on
 * cart.
 * </p>
 * 
 * @author amall3
 *
 */
@Service
//@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
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

	/**
	 * SequenceDAO for generating sequential order id.
	 */
	@Autowired
	private SequenceDao sequenceDao;

	/**
	 * New Pipeline Chain for Pricing.
	 */
	@Autowired
	OrderRepriceChain orderRepriceChain;

	@Autowired
	private MongoOperations mongoOperation;
	
	@Autowired
	ValidateChain validateChain;

	
	/**
	 * 
	 * @param orderId
	 * @return
	 * @throws NoRecordsFoundException
	 */
	public boolean validateForCheckout(String orderId) throws NoRecordsFoundException {

		log.debug("Inside OrderService.validateForCheckout() and Order Id passed is :" , orderId);
		Optional<Order> order = orderRepository.findById(orderId);
		if (null == order)
			throw new NoRecordsFoundException("No Order Found to Update.");
		
		boolean isValidationSuccess = Boolean.TRUE;
		
		isValidationSuccess = validateChain.validateForCheckout(order.get());

		return isValidationSuccess;
	}
	
	/**
	 * 
	 * @param productId
	 * @param quantity
	 * @param profileId
	 * @param orderId 
	 * @return
	 * @throws NoRecordsFoundException 
	 */
//	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public CartResponse addItemToOrder(String skuId, String productId, Long quantity , String orderId) throws NoRecordsFoundException {
		log.debug("Inside OrderService.addItemToOrder()");
		
		Optional<Order> order = orderRepository.findById(orderId);
		if(null == order)
			throw new NoRecordsFoundException("No Order Found to Update.");
		
		Optional<ProductDocument> prodItem = productRepository.findById(productId);
		if(null == prodItem)
			throw new NoRecordsFoundException("No Product Found to Update.");
		
		Query query = new Query();
		query.addCriteria(Criteria.where(CommerceConstants._ID).is(skuId));
		SkuDocument skuItem = mongoOperation.findOne(query, SkuDocument.class);
		if (null == skuItem)
			throw new NoRecordsFoundException("No Sku Found while creating order.");
		
		boolean isValid = validateProdSkuRelationships(prodItem.get(), skuItem);
		if (!isValid)
			throw new NoRecordsFoundException("Sku and Product Relationship is not correct.");
		
		CommerceItem item = getItemFromOrder(skuId, order.get());
		if (null == item) {

			// Create new CommerceItem Object an set the default values.
			CommerceItem commerceItem = createAndPopulateCommerceItem(quantity, prodItem.get(), skuId);
			order.get().addCommerceItem(commerceItem);
		} else {
			long qty = item.getQuantity();
			qty = qty + quantity;
			item.setQuantity(qty);
		}
		
		orderRepriceChain.reprice(order.get());
		Order orderItem =  orderRepository.save(order.get());
		return constructResponse(orderItem);
	}
	
	/**
	 * 
	 * @param orderId
	 * @param productId
	 * @return
	 * @throws NoRecordsFoundException 
	 */
//	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public CartResponse removeItemFromOrder(String orderId, String productId, String skuId) throws NoRecordsFoundException {
		log.debug("Inside OrderService.removeItemFromOrder()");
		
		Optional<Order> order = orderRepository.findById(orderId);
		if(null == order)
			throw new NoRecordsFoundException("No Order Found to Update.");
		
		Optional<ProductDocument> prodItem = productRepository.findById(productId);
		if(null == prodItem)
			throw new NoRecordsFoundException("No Product Found to Update.");
		
		CommerceItem itemToRemove = getItemFromOrder(skuId, order.get());
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
		order.get().removeCommerceItem(itemToRemove);
		orderRepriceChain.reprice(order.get());
		Order orderItem = orderRepository.save(order.get());
		return constructResponse(orderItem);
	}

	
	/**
	 * 
	 * @param cartId
	 * @param productId
	 * @param quantity
	 * @return 
	 * @throws NoRecordsFoundException 
	 */
//	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public CartResponse updateItemQuantity(String orderId, String productId, String skuId, long newQuantity) throws NoRecordsFoundException {
		
		log.debug("Inside OrderService.updateItemQuantity()");
		log.debug("The Passed In Order id : " + orderId + " Product Id :" + productId + "Quantity :" + productId);
		
		Optional<Order> order = orderRepository.findById(orderId);
		if(null == order)
			throw new NoRecordsFoundException("No Order Found to Update.");
		
		Optional<ProductDocument> prodItem = productRepository.findById(productId);
		if(null == prodItem)
			throw new NoRecordsFoundException("No Product Found to Update.");
		
		CommerceItem itemToUpdate = getItemFromOrder(skuId, order.get());
		if (null == itemToUpdate) {
			// Create new CommerceItem Object an set the default values.
			CommerceItem commerceItem = createAndPopulateCommerceItem(newQuantity, prodItem.get(), skuId);
			order.get().addCommerceItem(commerceItem);
		} else {
			itemToUpdate.setQuantity(newQuantity);
		} 
		//Reprice after updating items.
		orderRepriceChain.reprice(order.get());
		Order orderItem = orderRepository.save(order.get());
		return constructResponse(orderItem);
	}

	/**
	 * @param productId
	 * @param order
	 * @return
	 */
	private CommerceItem getItemFromOrder(String skuId, Order order) {
		List<CommerceItem> items = order.getCommerceItems();
		for (CommerceItem item : items) {
			if (item != null && skuId.equals(item.getCatalogRefId())) {
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
//	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Order createDefaultOrder(String profileId) {

		log.debug("Inside OrderServices.createDefaultOrder()");
		log.debug("The profile passed is " + profileId);

		// Create new Order Object an set the default values.
		Order order = initializeOrder();

		// Create new ShippingGroup Object an set the default values.
		initializeShippingGroup(order);

		// Create new PaymentGroup Object an set the default values.
		initializePaymentGroup(order);

		Optional<Account> profile = accountRepository.findById(profileId);
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
//	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public CartResponse createAndAddItemToOrder(String skuId, String productId , long quantity , String profileId) throws NoRecordsFoundException {
		
		log.debug("Inside OrderServices.createAndAddItemToOrder()");
		Optional<ProductDocument> prodItem = productRepository.findById(productId);
		if(null == prodItem)
			throw new NoRecordsFoundException("No Product Found while creating order.");
		
		Query query = new Query();
		query.addCriteria(Criteria.where(CommerceConstants._ID).is(skuId));
		SkuDocument skuItem = mongoOperation.findOne(query, SkuDocument.class);
		if (null == skuItem)
			throw new NoRecordsFoundException("No Sku Found while creating order.");
		
		boolean isValid = validateProdSkuRelationships(prodItem.get(), skuItem);
		if (!isValid)
			throw new NoRecordsFoundException("Sku and Product Relationship is not correct.");
		
		//Create new Order Object an set the default values.
		Order order = initializeOrder();
		
		//Create new ShippingGroup Object an set the default values.
		initializeShippingGroup(order);
		
		//Create new PaymentGroup Object an set the default values.
		initializePaymentGroup(order);
		
		//Create new CommerceItem Object an set the default values.
		CommerceItem commerceItem = createAndPopulateCommerceItem(quantity, prodItem.get(), skuId);
		order.addCommerceItem(commerceItem);
		
		//Need to add the encrypt/decrpt logic.
		Optional<Account> profile = accountRepository.findById(profileId);
		order.setProfileId(profileId);
		if (profile.isPresent()) {
			order.setTransient(Boolean.TRUE);
		} else {
			order.setTransient(Boolean.FALSE);
		}
		
		orderRepriceChain.reprice(order);
		
		Order orderItem= orderRepository.save(order);
		
		return constructResponse(orderItem);
	}

	/**
	 * @param prodItem
	 * @param skuItem
	 * @return
	 * @throws NoRecordsFoundException
	 */
	private boolean validateProdSkuRelationships(ProductDocument prodItem, SkuDocument skuItem) throws NoRecordsFoundException {

		log.debug("Inside OrderService.validateProdSkuRelationships()");
		boolean isValid = Boolean.FALSE;

		List<SkuDocument> skuList = prodItem.getSkuDocument();
		if (null != skuList && !skuList.isEmpty()) {

			for (SkuDocument skuDoc : skuList) {
				if (skuDoc.getId().equals(skuItem.getId())) {
					return Boolean.TRUE;
				}
			}

		} else
			return isValid;
		
		return isValid;
	}

	/**
	 * 
	 * @param orderId
	 * @return
	 * @throws NoRecordsFoundException 
	 */
	public CartResponse getCartDetails(String orderId) throws NoRecordsFoundException {
		log.debug("Inside OrderService.getCartDetails()");
		log.debug("The Passed In Order id : " + orderId);

		Optional<Order> order = orderRepository.findById(orderId);
		if (null == order)
			throw new NoRecordsFoundException("No Order Found to Update.");
		
		return constructResponse(order.get());
	}
	
	/**
	 * 
	 * @param orderId
	 * @return
	 * @throws NoRecordsFoundException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public OrderDetailResponse getOrderDetails(String orderId) 
			throws NoRecordsFoundException, IllegalAccessException, InvocationTargetException {
		log.debug("Inside OrderService.getCartDetails()");
		log.debug("The Passed In Order id : " + orderId);

		Optional<Order> order = orderRepository.findById(orderId);
		if (null == order)
			throw new NoRecordsFoundException("No Order Found to Update.");
		
		return constructOrderDetailResponse(order.get());
	}

	private OrderDetailResponse constructOrderDetailResponse(Order order) throws IllegalAccessException, InvocationTargetException {
		OrderDetailResponse response = new OrderDetailResponse();
		response.setOrderId(order.getId());
		OrderPriceInfo orderPriceInfo = order.getOrderPriceInfo();
		response.setOrderSubTotal(orderPriceInfo.getAmount());
		
		List<OrderItemResponse> responseItemList = populateOrderItemDetails(order,response);
		response.setOrderItems(responseItemList);
		response.setSubmittedDate(order.getSubmittedDate());
		response.setOrderStatus(order.getState());
		log.debug("Response Item sent is :", response.toString());
		return response;
	}

	/**
	 * Method that is responsible for constructing response for cart operations.
	 * 
	 * @param order
	 * @return
	 */
	public CartResponse constructResponse(Order order) {
	
		log.debug("Inside OrderService.constructResponse()");
		CartResponse cartResponse = new CartResponse();
		
		cartResponse.setOrderId(order.getId());
		OrderPriceInfo orderPriceInfo = order.getOrderPriceInfo();
		cartResponse.setOrderSubTotal(orderPriceInfo.getAmount());
		
		List<ShippingGroup> shippingGroups = order.getShippingGroups();
		for (ShippingGroup shippingGroup : shippingGroups) {
			if (shippingGroup.getShippingGroupType().equalsIgnoreCase("HardGoodShippingGroup")) {
				ShippingPriceInfo shipPriceInfo = shippingGroup.getShippingPriceInfo();
				if (null != shipPriceInfo) {
					cartResponse.setConvenienceFee(shipPriceInfo.getAmount());
				}
			}
		}
		
		List<CartItemResponse> responseItemList = populateItemDetails(order);
		cartResponse.setCartItems(responseItemList);
		log.debug("Response Item sent is :", cartResponse.toString());
		return cartResponse;
	}

	/**
	 * @param order
	 * @param responseItemList
	 */
	public List<CartItemResponse> populateItemDetails(Order order) {
		
		List<CartItemResponse> responseItemList = new ArrayList<CartItemResponse>();
		
		List<CommerceItem> items = order.getCommerceItems();
		if (null != items && !items.isEmpty()) {
			for (CommerceItem itm : items) {
				if(itm != null) {
				CartItemResponse cartItemResponse = new CartItemResponse();
				cartItemResponse.setQuantity(itm.getQuantity());
				cartItemResponse.setProductId(itm.getProductId());
				cartItemResponse.setCatalogRefId(itm.getCatalogRefId());
				
				Optional<ProductDocument> product = productRepository.findById(itm.getProductId());
				Query query = new Query();
				query.addCriteria(Criteria.where("_id").is(itm.getCatalogRefId()));
				SkuDocument skuItem = mongoOperation.findOne(query, SkuDocument.class);
				if (product.isPresent()) {
					ProductDocument productItem = product.get();
					BrandDocument brandDocument = productItem.getProductBrand();
					if (null != brandDocument) {
						cartItemResponse.setProductBrand(brandDocument.getStoreName());
					}
					cartItemResponse.setDescription(productItem.getProductDescription());
					cartItemResponse.setProductTitle(productItem.getProductTitle());
					cartItemResponse.setProductSlug(productItem.getProductSlug());

					ImagesDocument imageDocument = productItem.getImagesDocument();
					cartItemResponse.setProductImage(imageDocument.getThumbnailImageUrl());

					ItemPriceInfo itemPriceInfo = itm.getItemPriceInfo();
					if (null != itemPriceInfo) {
						cartItemResponse.setAmount(itemPriceInfo.getAmount());
					}
				}
				responseItemList.add(cartItemResponse);
			}
			}
		}
		
		
		return responseItemList;
	}
	
	/**
	 * @param order
	 * @param response 
	 * @param responseItemList
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public List<OrderItemResponse> populateOrderItemDetails(Order order, OrderDetailResponse response) 
			throws IllegalAccessException, InvocationTargetException {
		
		List<OrderItemResponse> responseItemList = new ArrayList<OrderItemResponse>();
		AddressResponse addressResponse = new AddressResponse();
		List<CommerceItem> items = order.getCommerceItems();
		if (null != items && !items.isEmpty()) {
			for (CommerceItem itm : items) {
				
				OrderItemResponse orderItemResponse = new OrderItemResponse();
				orderItemResponse.setQuantity(itm.getQuantity());
				orderItemResponse.setProductId(itm.getProductId());
				orderItemResponse.setCatalogRefId(itm.getCatalogRefId());
				orderItemResponse.setItemAmount(itm.getItemPriceInfo().getListPrice());
				Optional<ProductDocument> product = productRepository.findById(itm.getProductId());
				Query query = new Query();
				query.addCriteria(Criteria.where("_id").is(itm.getCatalogRefId()));
				SkuDocument skuItem = mongoOperation.findOne(query, SkuDocument.class);
				if (product.isPresent()) {
					ProductDocument productItem = product.get();
					BrandDocument brandDocument = productItem.getProductBrand();
					if (null != brandDocument) {
						orderItemResponse.setProductBrand(brandDocument.getStoreName());
					}
					orderItemResponse.setDescription(productItem.getProductDescription());
					orderItemResponse.setProductTitle(productItem.getProductTitle());
					orderItemResponse.setProductSlug(productItem.getProductSlug());

					ImagesDocument imageDocument = productItem.getImagesDocument();
					orderItemResponse.setProductImage(imageDocument.getThumbnailImageUrl());

					ItemPriceInfo itemPriceInfo = itm.getItemPriceInfo();
					if (null != itemPriceInfo) {
						orderItemResponse.setAmount(itemPriceInfo.getAmount());
					}
				}
				responseItemList.add(orderItemResponse);
			}
		}
		
		List<ShippingGroup> shippingGroups = order.getShippingGroups();
		if(null != shippingGroups && !shippingGroups.isEmpty()){
			for(ShippingGroup shippingGroup : shippingGroups){
				Address shippingAddress = shippingGroup.getAddress();
				BeanUtils.copyProperties(addressResponse, shippingAddress);
				break;
			}
		}
		response.setAddressResponse(addressResponse);
		return responseItemList;
	}
	

	private Order initializeOrder() {
		Order order = new Order();
		order.setId(sequenceDao.getNextOrderSequenceId(SequenceConstants.SEQ_KEY));
		order.setCreatedDate(new Date());
		order.setState(CommerceConstants.INITIAL);
		order.setOrderType(CommerceConstants.DEFAULT_ORDER_TYPE);
		order.setOriginOfOrder(CommerceConstants.WEB);
		order.setSiteId(CommerceConstants.PALLETTE);
		return order;
	}

	private void initializeShippingGroup(Order order) {
		ShippingGroup shipGrp = new ShippingGroup();
		shipGrp.setShippingGroupType(CommerceConstants.HARD_GOOD_SHIPPING_GROUP);
		shipGrp.setState(CommerceConstants.INITIAL);
		shipGrp.setId(sequenceDao.getNextOrderSequenceId(SequenceConstants.SEQ_KEY));
		order.addShippingGroup(shipGrp);
	}

	private void initializePaymentGroup(Order order) {
		PaymentGroup payGrp = new PaymentGroup();
		payGrp.setState(CommerceConstants.INITIAL);
		payGrp.setPaymentGroupType(CommerceConstants.CREDIT_CARD);
		payGrp.setPaymentMethod(CommerceConstants.CREDIT_CARD);
		payGrp.setId(sequenceDao.getNextOrderSequenceId(SequenceConstants.SEQ_KEY));
		order.addPaymentGroup(payGrp);
	}

	/**
	 * @param productId
	 * @param quantity
	 * @param prodDoc
	 * @return
	 */
	private CommerceItem createAndPopulateCommerceItem(Long quantity, ProductDocument prodDoc, String skuId) {
		
		CommerceItem commerceItem = new CommerceItem();
		commerceItem.setCatalogId(CommerceConstants.DEFAULT_CATALOG);
		commerceItem.setProductId(prodDoc.getId());
		commerceItem.setCatalogRefId(skuId);
		commerceItem.setId(skuId);
		commerceItem.setCommerceItemType(CommerceConstants.DEFAULT_COMMERCE_ITEM);
		commerceItem.setQuantity(quantity);
		commerceItem.setState(CommerceConstants.INITIAL);
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

	public OrderResponse getOrderHistory(String profileId) throws NoRecordsFoundException{
		log.debug("Inside OrderService.getOrderHistory()");
		log.debug("The Passed In Order id : " + profileId);

		List<Order> orders = orderRepository.findOrderByStateAndProfileId(CommerceConstants.SUBMITTED, profileId);

		if (orders.isEmpty()) {
			
			Query query = new Query();
			query.addCriteria(Criteria.where("profile_id").is(profileId));
			List<Order> orderList = mongoOperation.find(query, Order.class);

			for (Order order : orderList) {
				String state = order.getState();
				if (!StringUtils.isEmpty(state) && CommerceConstants.SUBMITTED.equalsIgnoreCase(state)) {
					orders.add(order);
				}
			}
		}
		
		if (null == orders || orders.isEmpty())
			throw new NoRecordsFoundException("No Order Found to Update.");
		
		return constructOrderResponse(orders);
	}

	private OrderResponse constructOrderResponse(List<Order> orders) {
		OrderResponse orderResponse = new OrderResponse();
		List<OrderHistoryResponse> orderHistoryList = new ArrayList<OrderHistoryResponse>();
		
		if(null != orders && !orders.isEmpty()){
			for(Order order : orders){
				OrderHistoryResponse response = new OrderHistoryResponse();
				response.setOrderId(order.getId());
				response.setSubmittedDate(order.getSubmittedDate());
				response.setState(order.getState());
				orderHistoryList.add(response);
			}
		}
		orderResponse.setOrderHistory(orderHistoryList);
		return orderResponse;
	}
}
