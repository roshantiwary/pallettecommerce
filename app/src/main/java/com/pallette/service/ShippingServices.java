/**
 * 
 */
package com.pallette.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pallette.beans.AddEditAddressBean;
import com.pallette.commerce.contants.CommerceConstants;
import com.pallette.commerce.order.purchase.OrderRepriceChain;
import com.pallette.domain.Address;
import com.pallette.domain.DeliveryMethod;
import com.pallette.domain.Order;
import com.pallette.domain.ShippingGroup;
import com.pallette.repository.OrderRepository;
import com.pallette.response.AddressResponse;
import com.pallette.response.CartResponse;
import com.pallette.response.DeliveryMethodResponse;
import com.pallette.user.User;
import com.pallette.user.api.ApiUser;

/**
 * <p>
 * Service class for Checkout Operations. This class includes methods that
 * perform function like adding address , editing address etc.
 * </p>
 * 
 * @author amall3
 *
 */

@Service
public class ShippingServices {

	private static final Logger log = LoggerFactory.getLogger(ShippingServices.class);

	@Autowired
	private MongoOperations mongoOperation;
	
	/**
	 * New Pipeline Chain for Pricing.
	 */
	@Autowired
	OrderRepriceChain orderRepriceChain;
	
	/**
	 * The Order repository.
	 */
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderService orderService;

	/**
	 * Method responsible for creating a new Address Document and saving it to
	 * database. It also associates the address to a shipping group.
	 * 
	 * @param orderId 
	 * 
	 * @param address
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public AddressResponse saveNewAddress(AddEditAddressBean addressBean, String orderId) throws IllegalAccessException, InvocationTargetException {

		log.debug("Inside ShippingServices.saveNewAddress()");
		log.debug("Order for which address to be saved is :" , orderId);
		AddressResponse addressResponse = null;
		
		if(StringUtils.isEmpty(orderId))
			return addressResponse;
			
		Address addressItem = new Address();
		Query query = new Query(Criteria.where(CommerceConstants._ID).is(orderId));
		log.debug("Query to be executed is :" , query);
		Order orderItem = mongoOperation.findOne(query, Order.class);

		if (null == orderItem)
			return addressResponse;
		
		// Bean Utils copyProperties method is responsible for copying properties across two beans.
		BeanUtils.copyProperties(addressItem, addressBean);
			
		List<ShippingGroup> shippingGroups = orderItem.getShippingGroups();
		for (ShippingGroup shipGrp : shippingGroups) {
			if (CommerceConstants.HARD_GOOD_SHIPPING_GROUP.equalsIgnoreCase(shipGrp.getShippingGroupType())) {
				Address address = shipGrp.getAddress();
				if (null == address) {
					shipGrp.setAddress(addressItem);
				} else {
					//If Address exists , remove the address first and then set the new address.
					removeAddress(address);
					shipGrp.setAddress(addressItem);
				}
			}
		}

		// Invoke mongo operations to save order dbobject.
		mongoOperation.save(orderItem, CommerceConstants.ORDER);
		
		// Bean Utils copyProperties method is responsible for copying properties across two beans.
		addressResponse = new AddressResponse();
		BeanUtils.copyProperties(addressResponse, addressItem);
		addressResponse.setAddressId(addressItem.getId().toString());
		
		return addressResponse;
	}


	/**
	 * Method that removes address from mongo db.
	 * 
	 * @param address
	 */
	private void removeAddress(Address address) {

		Query addressRemovalQuery = new Query();
		addressRemovalQuery.addCriteria(Criteria.where(CommerceConstants._ID).is(address.getId()));
		Address oldAddress = mongoOperation.findAndRemove(addressRemovalQuery, Address.class);
		log.debug("Address Document Removed Successfully :", oldAddress.getId());
	}

	
	/**
	 * Method responsible for editing the Address Document that is saved in the
	 * shipping group.
	 * 
	 * @param orderId 
	 * 
	 * @param address
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public AddressResponse editAddress(AddEditAddressBean addressBean, String orderId) throws IllegalAccessException, InvocationTargetException {

		log.debug("Inside ShippingServices.editAddress()");
		log.debug("Order for which address to be edited is :" , orderId);
		AddressResponse addressResponse = null;
		
		if(StringUtils.isEmpty(orderId))
			return addressResponse;
		
		Query query = new Query(Criteria.where(CommerceConstants._ID).is(orderId));
		log.debug("Query to be executed is :" , query);
		Order orderItem = mongoOperation.findOne(query, Order.class);
		
		if (null == orderItem)
			return addressResponse;
			
		List<ShippingGroup> shippingGroups = orderItem.getShippingGroups();
		for (ShippingGroup shipGrp : shippingGroups) {
			if (CommerceConstants.HARD_GOOD_SHIPPING_GROUP.equalsIgnoreCase(shipGrp.getShippingGroupType())) {
				Address addressItem = shipGrp.getAddress();
				// Bean Utils copyProperties method is responsible for copying properties across two beans.
				BeanUtils.copyProperties(addressItem, addressBean);
			}
		}

		// Invoke mongo operations to save order dbobject.
		mongoOperation.save(orderItem, CommerceConstants.ORDER);
		
		addressResponse = new AddressResponse();
		// Bean Utils copyProperties method is responsible for copying properties across two beans.
		BeanUtils.copyProperties(addressResponse, addressBean);
		
		return addressResponse;
	}
	
	
	/**
	 * Method responsible for returning the Address saved in the order's
	 * shipping group.
	 * 
	 * @param orderId
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public AddressResponse getShipmentAddressFromOrder(String orderId) throws IllegalAccessException, InvocationTargetException {

		log.debug("Inside ShippingServices.getShipmentAddressFromOrder()");
		log.debug("Order for which address to be fetched is :", orderId);

		if (StringUtils.isEmpty(orderId))
			return null;

		Query query = new Query(Criteria.where(CommerceConstants._ID).is(orderId));
		log.debug("Query to be executed is :", query);
		Order orderItem = mongoOperation.findOne(query, Order.class);

		if (null != orderItem) {

			return getOrderShipmentAddress(orderItem);
		}
		return null;
	}


	/**
	 * @param addressResponse
	 * @param orderItem
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public AddressResponse getOrderShipmentAddress(Order orderItem) throws IllegalAccessException, InvocationTargetException {
		
		AddressResponse addressResponse = new AddressResponse();
		List<ShippingGroup> shippingGroups = orderItem.getShippingGroups();
		if (null != shippingGroups && !shippingGroups.isEmpty()) {
			for (ShippingGroup shipGrp : shippingGroups) {
				if (CommerceConstants.HARD_GOOD_SHIPPING_GROUP.equalsIgnoreCase(shipGrp.getShippingGroupType())) {
					Address addressItem = shipGrp.getAddress();

					if (null != addressItem) {
						// Bean Utils copyProperties method is responsible for copying properties across two beans.
						BeanUtils.copyProperties(addressResponse, addressItem);
						addressResponse.setAddressId(addressItem.getId().toString());
						return addressResponse;
					}
				}
			}
		}
		return addressResponse;
	}


	/**
	 * Method responsible for returning the Address saved in the user's profile.
	 * 
	 * @param orderId
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public List<AddressResponse> getSavedAddressFromOrder(Long orderId, ApiUser user) throws IllegalAccessException, InvocationTargetException {

		log.debug("Inside ShippingServices.getSavedAddressFromOrder()");
		log.debug("Order for which address to be fetched is :", orderId);
		List<AddressResponse> savedAddress = new ArrayList<AddressResponse>();

		if (StringUtils.isEmpty(orderId))
			return savedAddress;

		Query findOrderQuery = new Query(Criteria.where(CommerceConstants._ID).is(orderId));
		log.debug("Find Order Query to be executed is :", findOrderQuery);
		Order orderItem = mongoOperation.findOne(findOrderQuery , Order.class);

		if (null == orderItem)
			return savedAddress;
		
		Long profileId = orderItem.getProfileId();
		
		if(user != null && user.getId() != profileId) {
			profileId = user.getId();
			updateOrderWithProfileId(orderId, profileId);
		}
		
		if(StringUtils.isEmpty(profileId))
			return savedAddress;
		
		log.debug("Profile Id from Order is" , profileId);
		
		Query findProfileQuery = new Query(Criteria.where(CommerceConstants._ID).is(profileId));
		log.debug("Find Profile Query to be executed is :", findProfileQuery);
		User accountItem = mongoOperation.findOne(findProfileQuery, User.class);
		
		if(null == accountItem)
			return savedAddress;
		
		List<Address> addresses = accountItem.getShippingAddress();
		if(addresses.isEmpty())
			return savedAddress;
		
		for (Address address : addresses) {
			AddressResponse addressBean = new AddressResponse();
			// Bean Utils copyProperties method is responsible for copying properties across two beans.
			addressBean.setAddressId(address.getId().toString());
			BeanUtils.copyProperties(addressBean, address);
			addressBean.setAddressId(address.getId().toString());
			savedAddress.add(addressBean);
		}
		return savedAddress;
	}


	private void updateOrderWithProfileId(Long orderId, Long profileId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(orderId));
		Update update = new Update();
		update.set("profileId", profileId);
		mongoOperation.upsert(query, update, Order.class);
	}

	/**
	 * Method that sets the profile address selected by the end user to the
	 * shipping group.
	 * 
	 * @param orderId
	 * @param addressId
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public AddressResponse setShipmentAddressToOrder(String orderId, String addressId) throws IllegalAccessException, InvocationTargetException {

		log.debug("Inside ShippingServices.setShipmentAddressToOrder()");
		AddressResponse addressResponse = null;

		Query orderQuery = new Query(Criteria.where(CommerceConstants._ID).is(orderId));
		log.debug("Query to be executed is :", orderQuery);
		Order orderItem = mongoOperation.findOne(orderQuery, Order.class);

		if (null == orderItem)
			return addressResponse;

		Query addressQuery = new Query(Criteria.where(CommerceConstants._ID).is(addressId));
		log.debug("Query to be executed is :", addressQuery);
		Address addressItem = mongoOperation.findOne(addressQuery, Address.class);

		if (null == addressItem)
			return addressResponse;
		
		if (!validateIfAddressBelongsToProfile(addressId, orderItem))
			return addressResponse;

		List<ShippingGroup> shippingGroups = orderItem.getShippingGroups();
		for (ShippingGroup shipGrp : shippingGroups) {
			if (CommerceConstants.HARD_GOOD_SHIPPING_GROUP.equalsIgnoreCase(shipGrp.getShippingGroupType())) {

				Address addr = shipGrp.getAddress();
				if (null != addr) {
					removeAddress(addr);
				}
				Query shippingQuery = new Query(Criteria.where(CommerceConstants._ID).is(shipGrp.getId()));
				Update update = new Update();
				Address newAddress = new Address();
				BeanUtils.copyProperties(newAddress, addressItem);
				
				//Code to set email address in address Obj from profile Starts.
				Long profileId = orderItem.getProfileId();
				log.debug("Profile Id from Order is" , profileId);
				
				Query findProfileQuery = new Query(Criteria.where(CommerceConstants._ID).is(profileId));
				log.debug("Find Profile Query to be executed is :", findProfileQuery);
				User accountItem = mongoOperation.findOne(findProfileQuery, User.class);
				newAddress.setEmailAddress(accountItem.getUsername());
				
				//Code to set email address in address Obj from profile Ends.
				
				update.set(CommerceConstants.ADDRESS, newAddress);
				mongoOperation.upsert(shippingQuery, update, ShippingGroup.class);
				
				addressResponse = new AddressResponse();
				addressResponse.setAddressId(addressItem.getId().toString());
				BeanUtils.copyProperties(addressResponse, newAddress);
			}
		}
		return addressResponse;
	}


	/**
	 * Method that validates if the address belongs to the user who is placing
	 * the order.
	 * 
	 * @param addressId
	 * @param orderItem
	 */
	private boolean validateIfAddressBelongsToProfile(String addressId, Order orderItem) {

		log.debug("Inside ShippingServices.validateIfAddressBelongsToProfile()");
		boolean isValid = Boolean.FALSE;

		// Check if this address belongs to this profile.
		Long profileId = orderItem.getProfileId();
		if (StringUtils.isEmpty(profileId))
			return isValid;

		log.debug("Profile Id from Order is", profileId);

		Query findProfileQuery = new Query(Criteria.where(CommerceConstants._ID).is(profileId));
		log.debug("Find Profile Query to be executed is :", findProfileQuery);
		User accountItem = mongoOperation.findOne(findProfileQuery, User.class);

		if (null == accountItem)
			return isValid;

		List<Address> addresses = accountItem.getShippingAddress();
		if (addresses.isEmpty())
			return isValid ;

		for (Address address : addresses) {

			if (address.getId().toString().equalsIgnoreCase(addressId)) {
				return Boolean.TRUE;
			}
		}
		return isValid;
	}


	/**
	 * 
	 * @param orderId 
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public List<DeliveryMethodResponse> getDeliveryMethodDetails(String orderId) throws IllegalAccessException, InvocationTargetException {

		log.info("Inside ShippingServices.getDeliveryMethodDetails()");
		List<DeliveryMethodResponse> deliveryMethodResponseList = new ArrayList<DeliveryMethodResponse>();
		String shippingMethod = "";

		Query orderQuery = new Query(Criteria.where(CommerceConstants._ID).is(orderId));
		log.debug("Query to be executed is :", orderQuery);
		Order orderItem = mongoOperation.findOne(orderQuery, Order.class);

		if (null == orderItem)
			return deliveryMethodResponseList;

		List<ShippingGroup> shippingGroups = orderItem.getShippingGroups();
		for (ShippingGroup shipGrp : shippingGroups) {
			if (CommerceConstants.HARD_GOOD_SHIPPING_GROUP.equalsIgnoreCase(shipGrp.getShippingGroupType())) {
				shippingMethod = shipGrp.getShippingGroupMethod();
			}

			List<DeliveryMethod> deliveryMethods = mongoOperation.findAll(DeliveryMethod.class);

			if (deliveryMethods.isEmpty())
				return deliveryMethodResponseList;

			for (DeliveryMethod dm : deliveryMethods) {

				if (!dm.isActive())
					continue;

				DeliveryMethodResponse deliveryMethodResponse = new DeliveryMethodResponse();
				log.debug("Delivery Method is :: " + dm.getId());
				BeanUtils.copyProperties(deliveryMethodResponse, dm);

				if (!StringUtils.isEmpty(shippingMethod) && dm.getId().equals(shippingMethod)) {
					deliveryMethodResponse.setSelected(Boolean.TRUE);
				} else {
					deliveryMethodResponse.setSelected(Boolean.FALSE);
				}
				deliveryMethodResponseList.add(deliveryMethodResponse);
			}
		}
		return deliveryMethodResponseList;
	}


	/**
	 * Method that sets the passed in delivery method in shipping group's
	 * shipping method. Also invokes the reprice chain to set the convenience
	 * charge.
	 * 
	 * @param orderId
	 * @param deliveryMethod
	 * @return
	 * @return
	 */
	public CartResponse setDeliveryMethod(String orderId, String deliveryMethod) {

		log.info("Inside ShippingServices.setDeliveryMethod()");
		boolean isUpdated = Boolean.FALSE;

		Query orderQuery = new Query(Criteria.where(CommerceConstants._ID).is(orderId));
		log.debug("Query to be executed is :", orderQuery);
		Order orderItem = mongoOperation.findOne(orderQuery, Order.class);

		if (null == orderItem)
			return null;

		Query deliveryMethodQuery = new Query(Criteria.where(CommerceConstants._ID).is(deliveryMethod));
		log.debug("Query to be executed is :", deliveryMethodQuery);
		DeliveryMethod deliveryMethodItem = mongoOperation.findOne(deliveryMethodQuery, DeliveryMethod.class);

		if (null == deliveryMethod)
			return null;

		List<ShippingGroup> shippingGroups = orderItem.getShippingGroups();
		for (ShippingGroup shipGrp : shippingGroups) {
			if (CommerceConstants.HARD_GOOD_SHIPPING_GROUP.equalsIgnoreCase(shipGrp.getShippingGroupType())) {
				shipGrp.setShippingGroupMethod(deliveryMethod);
				isUpdated = Boolean.TRUE;
			}
		}

		if (isUpdated) {
			orderRepriceChain.reprice(orderItem);
			Order order = orderRepository.save(orderItem);
			return orderService.constructResponse(order);
		}
		return orderService.constructResponse(orderItem);
	}

}
