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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pallette.beans.AddEditAddressBean;
import com.pallette.beans.AddressBean;
import com.pallette.commerce.contants.CommerceContants;
import com.pallette.domain.Account;
import com.pallette.domain.Address;
import com.pallette.domain.Order;
import com.pallette.domain.ShippingGroup;
import com.pallette.response.AddressResponse;

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
public class CheckoutServices {

	private static final Logger log = LoggerFactory.getLogger(CheckoutServices.class);

	@Autowired
	private MongoOperations mongoOperation;

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
	public boolean saveNewAddress(AddEditAddressBean addressBean, String orderId) throws IllegalAccessException, InvocationTargetException {

		log.debug("Inside OrderService.saveNewAddress()");
		log.debug("Order for which address to be saved is :" , orderId);
		boolean isSuccess = Boolean.FALSE;
		
		if(StringUtils.isEmpty(orderId))
			return isSuccess;
			
		Address addressItem = new Address();
		Query query = new Query(Criteria.where(CommerceContants._ID).is(orderId));
		log.debug("Query to be executed is :" , query);
		Order orderItem = mongoOperation.findOne(query, Order.class);

		if (null != orderItem) {

			// Bean Utils copyProperties method is responsible for copying properties across two beans.
			BeanUtils.copyProperties(addressItem, addressBean);
			
			List<ShippingGroup> shippingGroups = orderItem.getShippingGroups();
			for (ShippingGroup shipGrp : shippingGroups) {
				if (CommerceContants.HARD_GOOD_SHIPPING_GROUP.equalsIgnoreCase(shipGrp.getShippingGroupType())) {
					Address address = shipGrp.getAddress();
					if (null == address) {
						shipGrp.setAddress(addressItem);
					} else {
						//If Address exists , remove the address first and then set the new address.
						Query addressRemovalQuery = new Query();
						addressRemovalQuery.addCriteria(Criteria.where(CommerceContants._ID).is(address.getId()));
						Address oldAddress = mongoOperation.findAndRemove(addressRemovalQuery , Address.class);
						log.debug("Address Document Removed Successfully :" , oldAddress.getId());
						shipGrp.setAddress(addressItem);
					}
				}
			}

			// Invoke mongo operations to save order dbobject.
			mongoOperation.save(orderItem, CommerceContants.ORDER);
			isSuccess = Boolean.TRUE;
		}
		return isSuccess;
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
	public boolean editAddress(AddEditAddressBean addressBean, String orderId) throws IllegalAccessException, InvocationTargetException {

		log.debug("Inside OrderService.editAddress()");
		log.debug("Order for which address to be edited is :" , orderId);
		boolean isSuccess = Boolean.FALSE;
		
		if(StringUtils.isEmpty(orderId))
			return isSuccess;
		
		Query query = new Query(Criteria.where(CommerceContants._ID).is(orderId));
		log.debug("Query to be executed is :" , query);
		Order orderItem = mongoOperation.findOne(query, Order.class);
		
		if (null != orderItem) {
			
			List<ShippingGroup> shippingGroups = orderItem.getShippingGroups();
			for (ShippingGroup shipGrp : shippingGroups) {
				if (CommerceContants.HARD_GOOD_SHIPPING_GROUP.equalsIgnoreCase(shipGrp.getShippingGroupType())) {
					Address addressItem = shipGrp.getAddress();
					// Bean Utils copyProperties method is responsible for copying properties across two beans.
					BeanUtils.copyProperties(addressItem, addressBean);
				}
			}

			// Invoke mongo operations to save order dbobject.
			mongoOperation.save(orderItem, CommerceContants.ORDER);
			isSuccess = Boolean.TRUE;
		}
		return isSuccess;
	}
	
	
	/**
	 * Method responsible for removing the Address Document that is saved in the
	 * shipping group.Also updates the order object.
	 * 
	 * @param orderId 
	 * 
	 * @param address
	 * @return
	 */
	public boolean removeAddress(String orderId) {

		log.debug("Inside OrderService.editAddress()");
		log.debug("Order for which address to be edited is :" , orderId);
		boolean isSuccess = Boolean.FALSE;
		if(StringUtils.isEmpty(orderId))
			return isSuccess;
		
		Query query = new Query(Criteria.where(CommerceContants._ID).is(orderId));
		log.debug("Query to be executed is :" , query);
		Order orderItem = mongoOperation.findOne(query, Order.class);
		
		if (null != orderItem) {
			
			List<ShippingGroup> shippingGroups = orderItem.getShippingGroups();
			for (ShippingGroup shipGrp : shippingGroups) {
				if (CommerceContants.HARD_GOOD_SHIPPING_GROUP.equalsIgnoreCase(shipGrp.getShippingGroupType())) {
					Address addressItem = shipGrp.getAddress();
					
					if (null != addressItem) {
						//Remove the address document first.
						Query addressRemovalQuery = new Query();
						addressRemovalQuery.addCriteria(Criteria.where(CommerceContants._ID).is(addressItem.getId()));
						Address address = mongoOperation.findAndRemove(addressRemovalQuery , Address.class);
						log.debug("Address Document Removed Successfully :" , address.getId());
						//Set the address to null in shipping group.
						shipGrp.setAddress(null);
					}
				}
			}

			// Invoke mongo operations to save order dbobject.
			mongoOperation.save(orderItem, CommerceContants.ORDER);
			isSuccess = Boolean.TRUE;
		}
		return isSuccess;
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

		log.debug("Inside OrderService.getShipmentAddressFromOrder()");
		log.debug("Order for which address to be fetched is :", orderId);

		if (StringUtils.isEmpty(orderId))
			return null;

		AddressResponse addressResponse = new AddressResponse();
		Query query = new Query(Criteria.where(CommerceContants._ID).is(orderId));
		log.debug("Query to be executed is :", query);
		Order orderItem = mongoOperation.findOne(query, Order.class);

		if (null != orderItem) {

			List<ShippingGroup> shippingGroups = orderItem.getShippingGroups();
			if (null != shippingGroups && !shippingGroups.isEmpty()) {
				for (ShippingGroup shipGrp : shippingGroups) {
					if (CommerceContants.HARD_GOOD_SHIPPING_GROUP.equalsIgnoreCase(shipGrp.getShippingGroupType())) {
						Address addressItem = shipGrp.getAddress();
	
						if (null != addressItem) {
							// Bean Utils copyProperties method is responsible for copying properties across two beans.
							BeanUtils.copyProperties(addressResponse, addressItem);
							return addressResponse;
						}
					}
				}
			}
		}
		return null;
	}


	/**
	 * Method responsible for returning the Address saved in the user's profile.
	 * 
	 * @param orderId
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public List<AddressResponse> getSavedAddressFromOrder(String orderId) throws IllegalAccessException, InvocationTargetException {

		log.debug("Inside OrderService.getSavedAddressFromOrder()");
		log.debug("Order for which address to be fetched is :", orderId);
		List<AddressResponse> savedAddress = new ArrayList<AddressResponse>();

		if (StringUtils.isEmpty(orderId))
			return savedAddress;

		Query findOrderQuery = new Query(Criteria.where(CommerceContants._ID).is(orderId));
		log.debug("Find Order Query to be executed is :", findOrderQuery);
		Order orderItem = mongoOperation.findOne(findOrderQuery , Order.class);

		if (null == orderItem)
			return savedAddress;
		
		String profileId = orderItem.getProfileId();
		if(StringUtils.isEmpty(profileId))
			return savedAddress;
		
		log.debug("Profile Id from Order is" , profileId);
		
		Query findProfileQuery = new Query(Criteria.where(CommerceContants._ID).is(profileId));
		log.debug("Find Profile Query to be executed is :", findProfileQuery);
		Account accountItem = mongoOperation.findOne(findProfileQuery, Account.class);
		
		if(null == accountItem)
			return savedAddress;
		
		List<Address> addresses = accountItem.getAddresses();
		if(addresses.isEmpty())
			return savedAddress;
		
		for (Address address : addresses) {
			AddressResponse addressBean = new AddressResponse();
			// Bean Utils copyProperties method is responsible for copying properties across two beans.
			BeanUtils.copyProperties(addressBean, address);
			savedAddress.add(addressBean);
		}
		return savedAddress;
	}

}
