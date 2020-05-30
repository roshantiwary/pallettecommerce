# pallete-commerce

# Spring BOOT based ECommerce Framework

### Built With
- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Spring Boot](http://docs.spring.io/spring-boot)
- [MongoDB](https://www.mongodb.com)
- [Solr](https://lucene.apache.org/solr/)
- [Docker](https://www.docker.com)
- [Maven](https://maven.apache.org)

### Pre-Requisite
[Install docker](https://docs.docker.com/get-docker/)

### How to run Pallette commerce?
Run below docker command from your terminal or command prompt to start Spring Boot Application, MongoDB and Solr 
- docker-compose up

### Application URL
- Login: http://www.palletteapart.com/boot/login
- Registration: http://www.palletteapart.com/boot/account/create
- Update Personal Details: http://www.palletteapart.com/boot/account/edit
- Add Address To Profile: http://www.palletteapart.com/boot/account/address/add
- Edit Address: http://www.palletteapart.com/boot/account/address/edit
- Remove Address: http://www.palletteapart.com/boot/account/address/{id}/remove
- Change Password: http://www.palletteapart.com/boot/account/changePassword
- Access Token: http://www.palletteapart.com/boot/oauth/token (Body: grant_type:password, client_id:acme, client_secret:acmesecret, username:XXXX ,password:XXXXXX) (Authorization: user: XXXX password XXXXX)
- Get Product: http://www.palletteapart.com/boot/rest/api/v1/products (Header: Authorization:Bearer 04da0457-375a-4f62-bcd0-97ad7456c8ed)

### Role Based Access
- Following Roles have been created ADMIN (Update/Modify Order), ADMINISTRATOR(Provide users with access rights), STORE_USER(Store user can login and update order information), USER(Logged-In Customer)
- Default password for the roles are read from environment variable DEFAULT_PASSWORD
- Please update ResourceServerConfiguration.java with URL and Grant Authority based on Role
- By default each user registration will have USER Role.

### OAUTH2 ACCESS using Mongo Token Store
There are 2 types of Grant Types
	- client_credentials - Browse/Checkout Services do not require password
	- password - MyAccount services require users to be logged-in

### Client Credentials Grant
	- x-www-form-urlencoded body should contain following attributes
		- granty_type - client_credentials
		- client-id - acme
		- client_secret - acmesecret

### Password Grant
	- x-www-form-urlencoded body should contain following attributes
		- granty_type - client_credentials
		- client-id - acme
		- client_secret - acmesecret
		- username - administrator
		- password - <default_password>
### Service URL
