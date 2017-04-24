package com.pallette.payment.payu;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pallette.browse.documents.ProductDocument;
import com.pallette.browse.repository.ProductRepository;
import com.pallette.commerce.contants.CommerceConstants;
import com.pallette.commerce.contants.PaymentConstants;
import com.pallette.domain.Address;
import com.pallette.domain.CommerceItem;
import com.pallette.domain.Order;
import com.pallette.domain.OrderPriceInfo;
import com.pallette.domain.ShippingGroup;


/**
 *
 * @author Pallette
 */
@Configuration
@Component
@PropertySource("classpath:payment.properties")
public class PaymentIntegrator {
	
	private static final Logger log = LoggerFactory.getLogger(PaymentIntegrator.class);

	@Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
	
	@Value("${payment.salt}")
	private String salt;
	
	@Value("${payment.base-url}")
	private String base_url;
	
	@Value("${payment.success-url}")
	private String success_url;
	
	@Value("${payment.failure-url}")
	private String failure_url;
	
	@Value("${payment.cancel-url}")
	private String cancel_url;
	
	@Value("${payment.service-provider}")
	private String service_provider;
	
	/**
	 * The Product Repository.
	 */
	@Autowired
	private ProductRepository productRepository;

    private Integer error;

	/**
	 * 
	 * @param s
	 * @return
	 */
    public boolean empty(String s) {
		if (s == null || s.trim().equals("")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param type
	 * @param str
	 * @return
	 */
	public String hashCal(String type, String str) {
		byte[] hashseq = str.getBytes();
		StringBuffer hexString = new StringBuffer();
		try {
			MessageDigest algorithm = MessageDigest.getInstance(type);
			algorithm.reset();
			algorithm.update(hashseq);
			byte messageDigest[] = algorithm.digest();
			for (int i = 0; i < messageDigest.length; i++) {
				String hex = Integer.toHexString(0xFF & messageDigest[i]);
				if (hex.length() == 1) {
					hexString.append("0");
				}
				hexString.append(hex);
			}

		} catch (NoSuchAlgorithmException nsae) {
		}
		return hexString.toString();
	}

	/**
	 * 
	 * @param order
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
    public Map<String, String> hashCalMethod(Order order)  throws ServletException, IOException {
    	
        String action1 = "";
        error = 0;
        String hashString = "";
        
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String> urlParams = new HashMap<String, String>();
        
		if (null == order)
			return null;
        
		populateOrderDetails(order, params);
		String productInfo = JSONObject.quote(createProductInfo(order).toString());
		params.put(PaymentConstants.PRODUCTINFO, "test");
//		params.put(PaymentConstants.PRODUCTINFO, createProductInfo(order).toString());
		
		params.put(PaymentConstants.KEY, "rjQUPktU");
		params.put(PaymentConstants.SURL, success_url);
		params.put(PaymentConstants.FURL, failure_url);
		params.put(PaymentConstants.CURL, cancel_url);
		params.put(PaymentConstants.SERVICE_PROVIDER, service_provider);
		params.put(PaymentConstants.UDF1, order.getId());
        
        String txnid = "";
        if (empty(params.get("txnid"))) {
            Random rand = new Random();
            String rndm = Integer.toString(rand.nextInt()) + (System.currentTimeMillis() / 1000L);
            txnid = rndm;
            params.remove("txnid");
            params.put("txnid", txnid);
            txnid = hashCal("SHA-256", rndm).substring(0, 20);
        } else {
            txnid = params.get("txnid");
        }
        
        String hash = "";
        String otherPostParamSeq = "phone|surl|furl|lastname|curl|address1|address2|city|state|country|zipcode|pg";
        String hashSequence = "key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5|udf6|udf7|udf8|udf9|udf10";
        if (empty(params.get("hash")) && params.size() > 0) {
            if (empty(params.get("key")) || empty(txnid) || empty(params.get("amount")) || empty(params.get("firstname")) || empty(params.get("email")) || empty(params.get("phone")) || empty(params.get(PaymentConstants.PRODUCTINFO)) || empty(params.get("surl")) || empty(params.get("furl")) || empty(params.get("service_provider"))) {
                error = 1;
            } else {
                
                String[] hashVarSeq = hashSequence.split("\\|");
                for (String part : hashVarSeq) {
                    if (part.equals("txnid")) {
                        hashString = hashString + txnid;
                        urlParams.put("txnid", txnid);
                    } else {
                        hashString = (empty(params.get(part))) ? hashString.concat("") : hashString.concat(params.get(part).trim());
                        urlParams.put(part, empty(params.get(part)) ? "" : params.get(part).trim());
                    }
                    hashString = hashString.concat("|");
                }
                hashString = hashString.concat(salt);
                hash = hashCal("SHA-512", hashString);
                action1 = base_url.concat("/_payment");
                String[] otherPostParamVarSeq = otherPostParamSeq.split("\\|");
                for (String part : otherPostParamVarSeq) {
                    urlParams.put(part, empty(params.get(part)) ? "" : params.get(part).trim());
                }

            }
        } else if (!empty(params.get("hash"))) {
            hash = params.get("hash");
            action1 = base_url.concat("/_payment");
        }
        urlParams.put("service_provider", service_provider);
        urlParams.put("hash", hash);
        urlParams.put("action", action1);
        urlParams.put("hashString", hashString);
        return urlParams;
    }

	/**
	 * @param order
	 * @param params
	 */
	private void populateOrderDetails(Order order, Map<String, String> params) {
		
		OrderPriceInfo orderPriceInfo = order.getOrderPriceInfo();
		if (null != orderPriceInfo)
			params.put(PaymentConstants.AMOUNT, String.valueOf(orderPriceInfo.getAmount()));
		
		
		List<ShippingGroup> shippingGroups = order.getShippingGroups();
		for (ShippingGroup shipGrp : shippingGroups) {
			if (CommerceConstants.HARD_GOOD_SHIPPING_GROUP.equalsIgnoreCase(shipGrp.getShippingGroupType())) {
				Address address = shipGrp.getAddress();
				if (null != address) {
					//Mandatory Parameters
					params.put(PaymentConstants.FIRSTNAME, (String) address.getFirstName());
					params.put(PaymentConstants.EMAIL, (String) address.getEmailAddress());
					params.put(PaymentConstants.PHONE, (String) address.getPhoneNumber());
					
					//Set the additional Optional Parameters.
					params.put(PaymentConstants.LASTNAME, (String) address.getLastName());
					params.put(PaymentConstants.ADDRESS1, (String) address.getAddress1());
					params.put(PaymentConstants.ADDRESS2, (String) address.getAddress2());
					params.put(PaymentConstants.CITY, (String) address.getCity());
					params.put(PaymentConstants.STATE, (String) address.getState());
					params.put(PaymentConstants.ZIPCODE, (String) address.getZipcode());
					
					if (!StringUtils.isEmpty(address.getCountry())) {
						params.put(PaymentConstants.COUNTRY, (String) address.getCountry());
					} else {
						params.put(PaymentConstants.COUNTRY, PaymentConstants.INDIA);
					}
				}
			}
		}
	}

	/**
	 * @param order
	 * @throws JSONException
	 */
	private JSONObject createProductInfo(Order order) throws JSONException {
		
		log.debug("Inside JavaIntegrationKit.createProductInfo() and Order Passed is " + order.getId());
		JSONObject productInfo = new JSONObject();
		JSONArray paymentIdentifiers = new JSONArray();
		JSONArray paymentParts = new JSONArray();

		JSONObject completionDate = new JSONObject();
		completionDate.put(PaymentConstants.FIELD, PaymentConstants.COMPLETION_DATE);
		completionDate.put(PaymentConstants.VALUE2, new Date());
		paymentIdentifiers.put(completionDate);
		
		JSONObject transactionData = new JSONObject();
		transactionData.put(PaymentConstants.FIELD, PaymentConstants.TXN_ID);
		transactionData.put(PaymentConstants.VALUE2,"");
		paymentIdentifiers.put(transactionData);
		
		productInfo.put(PaymentConstants.PAYMENT_IDENTIFIERS, paymentIdentifiers);
		
		List<CommerceItem> commerceItemList = order.getCommerceItems();
		if (null != commerceItemList && !commerceItemList.isEmpty()) {

			for (CommerceItem item : commerceItemList) {
				String productId = item.getCatalogRefId();
				if (StringUtils.isEmpty(productId)) {
					ProductDocument prodItem = productRepository.findOne(productId);
					if (null != prodItem) {

						JSONObject itemJson = new JSONObject();
						itemJson.put(PaymentConstants.NAME, prodItem.getProductTitle());
						itemJson.put(PaymentConstants.DESCRIPTION, prodItem.getProductDescription());
						itemJson.put(PaymentConstants.VALUE, item.getItemPriceInfo().getAmount());
						itemJson.put(PaymentConstants.IS_REQUIRED, Boolean.TRUE);
						itemJson.put(PaymentConstants.SETTLEMENT_EVENT, PaymentConstants.EMAIL_CONFIRMATION);
						paymentParts.put(itemJson);

					}
				}
			}
		}
		productInfo.put(PaymentConstants.PAYMENT_PARTS, paymentParts);
		return productInfo;
	}

	public static void trustSelfSignedSSL() {
		try {
			final SSLContext ctx = SSLContext.getInstance("TLS");
			final X509TrustManager tm = new X509TrustManager() {
				@Override
				public void checkClientTrusted(final X509Certificate[] xcs,
						final String string) throws CertificateException {
					// do nothing
				}

				@Override
				public void checkServerTrusted(final X509Certificate[] xcs,
						final String string) throws CertificateException {
					// do nothing
				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLContext.setDefault(ctx);
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
	}
}
