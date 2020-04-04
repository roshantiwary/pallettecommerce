package com.pallette.web.security;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.pallette.constants.SequenceConstants;
import com.pallette.domain.Order;
import com.pallette.repository.SequenceDao;
import com.pallette.service.OrderService;

public class CustomTokenEnhancer implements TokenEnhancer {

	@Autowired
	private SequenceDao sequenceDao;

	@Autowired
	private OrderService orderService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		Map<String, Object> additionalInfo = new HashMap<>();

		Long profileId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
		additionalInfo.put("profileId", profileId);
		Order order = orderService.createDefaultOrder(profileId);
		additionalInfo.put("orderId", order.getId());

		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
		return accessToken;
	}
}
