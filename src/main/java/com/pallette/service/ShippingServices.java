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
public class ShippingServices {

	private static final Logger log = LoggerFactory.getLogger(ShippingServices.class);

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
	public List<AddressResponse> getSavedAddressFromOrder(String orderId) throws IllegalAccessException, InvocationTargetException {

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
		
		String profileId = orderItem.getProfileId();
		if(StringUtils.isEmpty(profileId))
			return savedAddress;
		
		log.debug("Profile Id from Order is" , profileId);
		
		Query findProfileQuery = new Query(Criteria.where(CommerceConstants._ID).is(profileId));
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
			addressBean.setAddressId(address.getId().toString());
			BeanUtils.copyProperties(addressBean, address);
			savedAddress.add(addressBean);
		}
		return savedAddress;
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
		String profileId = orderItem.getProfileId();
		if (StringUtils.isEmpty(profileId))
			return isValid;

		log.debug("Profile Id from Order is", profileId);

		Query findProfileQuery = new Query(Criteria.where(CommerceConstants._ID).is(profileId));
		log.debug("Find Profile Query to be executed is :", findProfileQuery);
		Account accountItem = mongoOperation.findOne(findProfileQuery, Account.class);

		if (null == accountItem)
			return isValid;

		List<Address> addresses = accountItem.getAddresses();
		if (addresses.isEmpty())
			return isValid ;

		for (Address address : addresses) {

			if (address.getId().toString().equalsIgnoreCase(addressId)) {
				return Boolean.TRUE;
			}
		}
		return isValid;
	}

}
