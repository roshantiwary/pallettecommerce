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
- [Install Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
- [Install docker](https://docs.docker.com/get-docker/)

### How to run Pallette commerce?
- Download code from Git repository(https://github.com/roshantiwary/pallettecommerce)
- Run below docker command from the above repository location in your terminal or command prompt to start Spring Boot Application, MongoDB and Solr 
	-```docker-compose up```

### eCommerce Features Supported
- Browse & Search
- Commerce
	- Cart & Checkout
	- Shipping
	- Payment
- My Account and Profile
	- Login
	- Registration
	- Update Personal Details
	- Add Address To Profile
	- Edit Address
	- Remove Address
	- Change Password
	- Order History
	- Order Detail
- Merchandise Maintenance to upload Catalog Data

### Role Based Access
- Following Roles have been created ADMIN (Update/Modify Order), ADMINISTRATOR(Provide users with access rights), STORE_USER(Store user can login and update order information), USER(Logged-In Customer)
- Default password for the roles are read from environment variable DEFAULT_PASSWORD
- Please update ResourceServerConfiguration.java with URL and Grant Authority based on Role
- By default each user registration will have USER Role.

### OAUTH2 ACCESS using Mongo Token Store
There are 2 types of Grant Types
	- client_credentials - Required to access all Browse/Checkout Services even as guest
	- password - Required to acess MyAccount services and all other Browse/Checkout Services as registered customer

<details><summary>Client Credentials Grant Type to access Browse and Checkout services as guest customer</summary>
<p>
	
```python
curl --location --request POST 'http://localhost:8080/oauth/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=acme' \
--data-urlencode 'client_secret=acmesecret' \
--data-urlencode 'grant_type=client_credentials'
```
</p>	
</details>

<details><summary>Password Grant Type to access API as administrator</summary>
<p>
	
```python
curl --location --request POST 'http://localhost:8080/oauth/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=acme' \
--data-urlencode 'client_secret=acmesecret' \
--data-urlencode 'grant_type=administrator' \
--data-urlencode 'username=administrator' \
--data-urlencode 'password=pass'
```
</p>	
</details>

<details><summary>Password Grant Type to access Account, Browse and Checkout services as registered customer</summary>
<p>
	
```python
curl --location --request POST 'http://localhost:8080/oauth/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=acme' \
--data-urlencode 'client_secret=acmesecret' \
--data-urlencode 'grant_type=administrator' \
--data-urlencode 'username=<user-id used during registration>' \
--data-urlencode 'password=<password used during registration>'
```
</p>	
</details>


### Data Setup : List of Services to create Merchandise Catalog from [sample_data](https://github.com/roshantiwary/pallettecommerce/tree/master/sample_data) directory

- [Product Images](https://github.com/roshantiwary/pallettecommerce/blob/master/sample_data/01_Images.csv)
```curl --location --request POST 'http://localhost:8080/rest/api/v1/media/upload' \
--header 'Authorization: Bearer 4df73d06-efec-415a-bfd3-2e9873b34d43' \
--form 'file=@/path/to/file'```

- [Brand or Store Data](https://github.com/roshantiwary/pallettecommerce/blob/master/sample_data/02_Brand.csv)
```curl --location --request POST 'http://localhost:8080/rest/api/v1/brand/upload' \
--header 'Authorization: Bearer 4df73d06-efec-415a-bfd3-2e9873b34d43' \
--form 'file=@/path/to/file'```

- [Inventory Data for products](https://github.com/roshantiwary/pallettecommerce/blob/master/sample_data/03_Inventory.csv)
```curl --location --request POST 'http://localhost:8080/rest/api/v1/inventory/upload' \
--header 'Authorization: Bearer 4df73d06-efec-415a-bfd3-2e9873b34d43' \
--form 'file=@/path/to/file'```

- [Category Data](https://github.com/roshantiwary/pallettecommerce/blob/master/sample_data/04_Category_New.csv)
```curl --location --request POST 'http://localhost:8080/rest/api/v1/category/upload' \
--header 'Authorization: Bearer 4df73d06-efec-415a-bfd3-2e9873b34d43' \
--form 'file=@/path/to/file'```

- [Pricing Data](https://github.com/roshantiwary/pallettecommerce/blob/master/sample_data/05_Price.csv)
```curl --location --request POST 'http://localhost:8080/rest/api/v1/price/upload' \
--header 'Authorization: Bearer 4df73d06-efec-415a-bfd3-2e9873b34d43' \
--form 'file=@/path/to/file'```

- [Product Data](https://github.com/roshantiwary/pallettecommerce/blob/master/sample_data/06_Product.csvv)
```curl --location --request POST 'http://localhost:8080/rest/api/v1/product/upload' \
--header 'Authorization: Bearer 4df73d06-efec-415a-bfd3-2e9873b34d43' \
--form 'file=@/path/to/file'```

- [City Data](https://github.com/roshantiwary/pallettecommerce/blob/master/sample_data/07_City.csv)
```curl --location --request POST 'http://localhost:8080/rest/api/v1/product/upload' \
--header 'Authorization: Bearer 4df73d06-efec-415a-bfd3-2e9873b34d43' \
--form 'file=@/path/to/file'```

- [SKU Data](https://github.com/roshantiwary/pallettecommerce/blob/master/sample_data/08_Sku.csv)
```curl --location --request POST 'http://localhost:8080/rest/api/v1/sku/upload' \
--header 'Authorization: Bearer 4df73d06-efec-415a-bfd3-2e9873b34d43' \
--form 'file=@/path/to/file'```

- Index product records to Solr server for search service
```curl --location --request POST 'http://localhost:8080/rest/api/v1/index/solr/products' \
--header 'Authorization: Bearer 4df73d06-efec-415a-bfd3-2e9873b34d43'```
