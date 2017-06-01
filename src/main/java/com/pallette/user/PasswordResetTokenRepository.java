package com.pallette.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class that performs repository operations on PasswordResetToken document.
 * 
 * @author amall3
 *
 */
@Transactional
public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetToken, String> {

	/**
	 * Method that returns PasswordReset document based on the passed in token.
	 * 
	 * @param token
	 * @return
	 */
	public PasswordResetToken findByToken(String token);

	/**
	 * Method that returns PasswordReset document based on the passed in profile
	 * Id.
	 * 
	 * @param userId
	 * @return
	 */
	public PasswordResetToken findByUserId(String userId);
	
	/**
	 * Method that returns PasswordReset document based on the passed in profile
	 * Id and Token.
	 * 
	 * @param userId
	 * @return
	 */
	public PasswordResetToken findByUserIdAndToken(String userId , String tokenId);

}
