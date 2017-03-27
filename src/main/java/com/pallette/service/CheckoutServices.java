/**
 * 
 */
package com.pallette.service;

import java.lang.reflect.InvocationTargetException;
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

import com.pallette.beans.AddressBean;
import com.pallette.commerce.contants.CommerceContants;
import com.pallette.domain.Address;
import com.pallette.domain.Order;
import com.pallette.domain.ShippingGroup;

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
	 * @param profileId 
	 * @param orderId 
	 * 
	 * @param address
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public boolean saveNewAddress(AddressBean addressBean, String orderId, String profileId) throws IllegalAccessException, InvocationTargetException {

		log.debug("Inside OrderService.saveNewAddress()");
		log.debug("Order for which address to be saved is :" , orderId);
		boolean isSuccess = Boolean.FALSE;
		
		if(StringUtils.isEmpty(orderId) || StringUtils.isEmpty(profileId))
			return isSuccess;
			
		Address addressItem = new Address();
		Query query = new Query(Criteria.where(CommerceContants._ID).is(orderId));
		log.debug("Query to be executed is :" , query);
		Order orderItem = mongoOperation.findOne(query, Order.class);

		if (null != orderItem) {

			if (!profileId.equalsIgnoreCase(orderItem.getProfileId())) {
				return isSuccess;
			}
			
			// Bean Utils copyProperties method is responsible for copying properties across two beans.
			BeanUtils.copyProperties(addressItem, addressBean);
			
			List<ShippingGroup> shippingGroups = orderItem.getShippingGroups();
			for (ShippingGroup shipGrp : shippingGroups) {
				if (CommerceContants.HARD_GOOD_SHIPPING_GROUP.equalsIgnoreCase(shipGrp.getShippingGroupType())) {
					shipGrp.setAddress(addressItem);
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
	 * @param profileId 
	 * @param orderId 
	 * 
	 * @param address
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public boolean editAddress(AddressBean addressBean, String orderId, String profileId) throws IllegalAccessException, InvocationTargetException {

		log.debug("Inside OrderService.editAddress()");
		log.debug("Order for which address to be edited is :" , orderId);
		boolean isSuccess = Boolean.FALSE;
		
		if(StringUtils.isEmpty(orderId) || StringUtils.isEmpty(profileId))
			return isSuccess;
		
		Query query = new Query(Criteria.where(CommerceContants._ID).is(orderId));
		log.debug("Query to be executed is :" , query);
		Order orderItem = mongoOperation.findOne(query, Order.class);
		
		if (null != orderItem) {
			
			if (!profileId.equalsIgnoreCase(orderItem.getProfileId())) {
				return isSuccess;
			}
			
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
	 * @param profileId 
	 * @param orderId 
	 * 
	 * @param address
	 * @return
	 */
	public boolean removeAddress(AddressBean addressBean, String orderId, String profileId) {

		log.debug("Inside OrderService.editAddress()");
		log.debug("Order for which address to be edited is :" , orderId);
		boolean isSuccess = Boolean.FALSE;
		if(StringUtils.isEmpty(orderId) || StringUtils.isEmpty(profileId))
			return isSuccess;
		
		Query query = new Query(Criteria.where(CommerceContants._ID).is(orderId));
		log.debug("Query to be executed is :" , query);
		Order orderItem = mongoOperation.findOne(query, Order.class);
		
		if (null != orderItem) {
			
			if (!profileId.equalsIgnoreCase(orderItem.getProfileId())) {
				return isSuccess;
			}
			
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

}
