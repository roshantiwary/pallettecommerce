/**
 * 
 */
package com.pallette.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

/**
 * <p>
 * A custom constraint validator created to validate the domain model objects
 * sent via post service calls. It only validates Phone fields which has an
 * annotation @Phone
 * </p>
 * 
 * @author amall3
 *
 */
public class PhoneValidator implements ConstraintValidator<Phone, String> {

	@Override
	public boolean isValid(String phoneNo, ConstraintValidatorContext context) {

		if (StringUtils.isEmpty(phoneNo))
			return Boolean.FALSE;

		// validate phone numbers of format "1234567890"
		if (phoneNo.matches("\\d{10}"))
			return Boolean.TRUE;

		// validating phone number with -, . or spaces
		else if (phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}"))
			return Boolean.TRUE;

		// validating phone number with extension length from 3 to 5
		else if (phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}"))
			return Boolean.TRUE;

		// validating phone number where area code is in braces ()
		else if (phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}"))
			return Boolean.TRUE;

		// return false if nothing matches the input
		else
			return Boolean.FALSE;
	}

	@Override
	public void initialize(Phone param) {

	}

}
