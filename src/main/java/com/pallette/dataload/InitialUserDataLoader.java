package com.pallette.dataload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pallette.domain.Account;
import com.pallette.domain.Role;
import com.pallette.repository.AccountRepository;
import com.pallette.repository.RoleRepository;

@Component
public class InitialUserDataLoader implements ApplicationListener<ContextRefreshedEvent>{

	boolean alreadySetup = false;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private AccountRepository acctRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Value("${pallette.default-password}")
	private String defaultPassword;
	
	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(alreadySetup) return;
		
		// Create Roles
		Role administrator  = createRoleIfNotFound("ROLE001", "ADMINISTRATOR");
		Role admin = createRoleIfNotFound("ROLE002", "ADMIN");
		Role user = createRoleIfNotFound("ROLE003", "USER");
		Role storeUser = createRoleIfNotFound("ROLE004", "STORE_USER");
		
		// Create store user
		createStoreUser(storeUser);
		// Create admin
		createAdmin(admin);
		// Create administrator
		createAdministor(administrator);
		// Create user
		createUser(user);
		
		alreadySetup = true;
	}

	private Account createStoreUser(Role role) {
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		Account account = createUserIfNotFound("store", "store", "store", "1234567890", roles);
		return account;
	}
	
	private Account createAdmin(Role role) {
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		Account account = createUserIfNotFound("admin", "admin", "admin", "1234567890", roles);
		return account;
	}
	
	private Account createAdministor(Role role) {
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		Account account = createUserIfNotFound("administrator", "administrator", "administrator", "1234567890", roles);
		return account;
	}
	
	private Account createUser(Role role) {
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		Account account = createUserIfNotFound("user", "user", "user", "1234567890", roles);
		return account;
	}
	
	@Transactional
    private Role createRoleIfNotFound(
      String id, String name) {
  
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setId(id);
            role.setName(name);
            roleRepository.save(role);
        }
        return role;
    }
	
	@Transactional
    private Account createUserIfNotFound(
      String username, String firstName, String lastName, String phonenumber, List<Role> roles) {
  
        Account account = acctRepository.findByUsername(username);
        if (account == null) {
        	account = new Account();
        	account.setUsername(username);
        	account.setFirstName(firstName);
        	account.setLastName(lastName);
        	account.setPassword(passwordEncoder.encode(defaultPassword));
        	account.setPhoneNumber(phonenumber);
        	account.setCreationdate(new Date());
            account.addRole(roles);
            acctRepository.save(account);
        } 
        return account;
    }

}
