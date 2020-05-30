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
	- client_credentials - Required to access all Browse/Checkout Services even as guest
	- password - Required to acess MyAccount services and all other Browse/Checkout Services as registered customer

### Client Credentials Grant Type to access Browse and Checkout services as guest customer
```curl --location --request POST 'http://localhost:8080/oauth/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=acme' \
--data-urlencode 'client_secret=acmesecret' \
--data-urlencode 'grant_type=client_credentials'
```
### Password Grant Type to access API as administrator
```curl --location --request POST 'http://localhost:8080/oauth/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=acme' \
--data-urlencode 'client_secret=acmesecret' \
--data-urlencode 'grant_type=administrator' \
--data-urlencode 'username=administrator' \
--data-urlencode 'password=pass'
```
### Password Grant Type to access Account, Browse and Checkout services as registered customer
```curl --location --request POST 'http://localhost:8080/oauth/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=acme' \
--data-urlencode 'client_secret=acmesecret' \
--data-urlencode 'grant_type=administrator' \
--data-urlencode 'username=<user-id used during registration>' \
--data-urlencode 'password=<password used during registration>'
```
### Data Setup : List of Services to create Merchandise Catalog from sample_data directory
- Product Images (01_Images.csv)
```
curl --location --request POST 'http://localhost:8080/rest/api/v1/media/upload' \
--header 'Authorization: Bearer 4df73d06-efec-415a-bfd3-2e9873b34d43' \
--form 'file=@/path/to/file'
```

- Brand or Store information for the products (02_Brand.csv)
```
curl --location --request POST 'http://localhost:8080/rest/api/v1/brand/upload' \
--header 'Authorization: Bearer 4df73d06-efec-415a-bfd3-2e9873b34d43' \
--form 'file=@/path/to/file'
```

- Inventory Data for products (03_Inventory.csv)
```
curl --location --request POST 'http://localhost:8080/rest/api/v1/inventory/upload' \
--header 'Authorization: Bearer 4df73d06-efec-415a-bfd3-2e9873b34d43' \
--form 'file=@/path/to/file'
```

- Category Data (04_Category_New.csv)
```
curl --location --request POST 'http://localhost:8080/rest/api/v1/category/upload' \
--header 'Authorization: Bearer 4df73d06-efec-415a-bfd3-2e9873b34d43' \
--form 'file=@/path/to/file'
```

- Pricing Data (05_Price.csv)
```
curl --location --request POST 'http://localhost:8080/rest/api/v1/price/upload' \
--header 'Authorization: Bearer 4df73d06-efec-415a-bfd3-2e9873b34d43' \
--form 'file=@/path/to/file'
```

- Product Data (06_Product.csv)
```
curl --location --request POST 'http://localhost:8080/rest/api/v1/product/upload' \
--header 'Authorization: Bearer 4df73d06-efec-415a-bfd3-2e9873b34d43' \
--form 'file=@/path/to/file'
```

- City Data (07_City.csv)
```
curl --location --request POST 'http://localhost:8080/rest/api/v1/product/upload' \
--header 'Authorization: Bearer 4df73d06-efec-415a-bfd3-2e9873b34d43' \
--form 'file=@/path/to/file'
```

- SKU Data (08_Sku.csv)
```
curl --location --request POST 'http://localhost:8080/rest/api/v1/sku/upload' \
--header 'Authorization: Bearer 4df73d06-efec-415a-bfd3-2e9873b34d43' \
--form 'file=@/path/to/file'
```

- Index product records to Solr server for search service
```
curl --location --request POST 'http://localhost:8080/rest/api/v1/index/solr/products' \
--header 'Authorization: Bearer 4df73d06-efec-415a-bfd3-2e9873b34d43'
```
