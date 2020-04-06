package com.pallette.domain;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pallette.config.CascadeSave;
import com.pallette.persistence.BaseEntity;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document
public class Account implements Principal{

	@Id
	private Long id;

	@Email
	private String username;
	
	private String password;

	private String firstName;
	
	private String lastName;

	@DBRef
	private List<Role> roles = new ArrayList<Role>();
	
	@DateTimeFormat(style = "LL")
	@JsonProperty("creationdate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date creationdate;
	
    private String authtoken;
    
    private String phoneNumber;
    
    @DBRef
    @CascadeSave
    private List<Address> addresses;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}

	public String getAuthtoken() {
		return authtoken;
	}

	public void setAuthtoken(String authtoken) {
		this.authtoken = authtoken;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public List<Role> getRoles() {
		return roles;
	}

	public void addRole(List<Role> roles) {
		this.roles.addAll(roles);
	}
	
	public void removeRole(Role role) {
		//use iterator to avoid java.util.ConcurrentModificationException with foreach
		for (Iterator<Role> iter = this.roles.iterator(); iter.hasNext(); )
		{
		   if (iter.next().equals(role))
		      iter.remove();
		}
	}
	
	public String getRolesCSV() {
		StringBuilder sb = new StringBuilder();
		for (Iterator<Role> iter = this.roles.iterator(); iter.hasNext(); )
		{
		   sb.append(iter.next().getId());
		   if (iter.hasNext()) {
			   sb.append(',');
		   }
		}
		return sb.toString();
	}	
	
 public Account(org.bson.Document document) {
        this((String)document.get("_id"));
        this.username = (String)document.get("username");
        this.firstName = (String)document.get("firstName");
        this.lastName = (String)document.get("lastName");
        this.password = (String)document.get("password");
        this.authtoken = (String)document.get("authtoken");
        List<String> roles = new ArrayList<String>();
        roles =	(List<String>)document.get("roles");
        deSerializeRoles(roles);
 }
 
 public void addRole(Role role) {
     this.roles.add(role);
 }
 
 private void deSerializeRoles(List<String> roles) {
	 if(null!= roles && !roles.isEmpty()) {
	     for(String role : roles) {
	    	 Role userRole = new Role();
	    	 userRole.setId("ROLE003");
	    	 userRole.setName("USER");
	         this.addRole(userRole);
	     }
	 }
}
	
	 public Account(String id) {
	     super();
	 }
	 
	 public Account() {
		 super();
	 }
	@Override
	public String toString() {
		return "Account{" +
                "id='" + id + '\'' +
                ", username=" + username + '\'' +
                ", password=" + password + '\'' +
                ", firstName=" + firstName + '\'' +
                ", lastName=" + lastName + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
	}

	public List<Address> getAddresses() {
		if(null == addresses)
			addresses = new ArrayList<Address>();
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	
	public void removeAddress(Address address) {
		if (null != this.addresses && !this.addresses.isEmpty()) {
			addresses.remove(address);
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.username;
	} 
}
