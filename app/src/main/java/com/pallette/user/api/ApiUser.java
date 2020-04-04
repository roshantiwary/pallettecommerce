package com.pallette.user.api;

import java.security.Principal;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import static org.springframework.util.Assert.notNull;
import org.hibernate.validator.constraints.Email;

import com.pallette.user.User;

@XmlRootElement
public class ApiUser implements Principal{

	@Email
    @NotNull
    private String emailAddress;

	private String firstName;
    private String lastName;
    private Integer age;
    private Long id;
    
    public ApiUser() {
	}

    public ApiUser(User user) {
        this.emailAddress = user.getEmailAddress();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.id = user.getId();
        this.age = user.getAge();
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		notNull(id, "Mandatory argument 'id missing.'");
		this.id = id;
	}
    
    public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		notNull(emailAddress, "Mandatory argument 'emailAddress missing.'");
		this.emailAddress = emailAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
        notNull(firstName, "Mandatory argument 'firstName missing.'");
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		notNull(lastName, "Mandatory argument 'lastName missing.'");
		this.lastName = lastName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
    
	@Override
	public String getName() {
		return this.getEmailAddress();
	}
}
