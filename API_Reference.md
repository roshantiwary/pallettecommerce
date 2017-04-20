#Pallete-commerce- API References:

## Browse Services:

### Get all Products.
	Service URL - "/products"
	HTTP Method - GET
	
### Get Selected Product.
	Service URL - "/products/{productId}"
	HTTP Method - GET

### Get Products by Title.
	Service URL - "/products/title/{productTitle}"
	HTTP Method - GET
	
### Get Products by Brand.
	Service URL - "/products/brand/{brandId}"
	HTTP Method - GET
	
### Get All Categories.
	Service URL - "/categories"
	HTTP Method - GET
	
### Get  Selected Category.
	Service URL - "/categories/{categoryId}"
	HTTP Method - GET
	
### Get Categories by Title.
	Service URL - "/categories/title/{categoryTitle}"
	HTTP Method - GET	
	
### Get All Brands.
	Service URL - "/brands"
	HTTP Method - GET	
		
### Get Selected Brand.
	Service URL - "/brands/{brandId}"
	HTTP Method - GET	
	
### Get Brands by Postal Code.
	Service URL - "/brands/postalCode/{postalCode}"
	HTTP Method - GET

### Get Brands by State.
	Service URL - "/brands/state/{state}"
	HTTP Method - GET	

### Get Brands by City.
	Service URL - "/brands/city/{city}"
	HTTP Method - GET	
	

## Cart Services:	

### Add Item To Cart.
	Service URL - "/cart/add"
	HTTP Method - POST
	Parameters :
	{
	"orderId":"",
	"productId":"prd002",
	"skuId":"sku003",
	"quantity":1,
	"profileId":"profile123"
	}
	Service Response:
		{
	  "message": "New Order created and Item successfully added to cart.",
	  "status": true,
	  "statusCode": 200,
	  "orderId": "order34",
	  "cartItems": [
	    {
	      "catalogRefId": "sku003",
	      "quantity": 1,
	      "description": "Milk Sugar Dry Fruits Rose Petal Jam ( Gulkand) Saffron",
	      "productId": "prd002",
	      "productTitle": "Mava Gulkand Roll",
	      "productSlug": "Mava Gulkand Roll",
	      "productBrand": "KC Das",
	      "productImage": "prd002_t.jpg",
	      "amount": 400
	    }
	  ],
	  "orderSubTotal": 400
	}

### Update Item .
	Service URL - "/cart/update"
	HTTP Method - POST
	Parameters :
	{
	"orderId":"order33",
	"productId":"prd002",
	"skuId":"sku003",
	"quantity":1
	}
	Service Response:
			{
	  "message": "Item successfully updated.",
	  "status": true,
	  "statusCode": 200,
	  "orderId": "order34",
	  "cartItems": [
	    {
	      "catalogRefId": "sku003",
	      "quantity": 5,
	      "description": "Milk Sugar Dry Fruits Rose Petal Jam ( Gulkand) Saffron",
	      "productId": "prd002",
	      "productTitle": "Mava Gulkand Roll",
	      "productSlug": "Mava Gulkand Roll",
	      "productBrand": "KC Das",
	      "productImage": "prd002_t.jpg",
	      "amount": 2000
	    }
	  ],
	  "orderSubTotal": 2000
	}
	
### Remove Item .
	Service URL - "/cart/remove"
	HTTP Method - POST
	Parameters :
	{
	"orderId":"order33",
	"productId":"prd002",
	"skuId":"sku003"
	}
	Service Response:
		{
	  "message": "Item successfully Removed.",
	  "status": true,
	  "statusCode": 200,
	  "orderId": "order34",
	  "cartItems": [],
	  "orderSubTotal": 0
	}
	
### Get Cart Details .
	Service URL - "/cart/{orderId}/details"
	HTTP Method - GET
	Service Response:
		{
	  "message": "Order Details Retrieved Successfully.",
	  "status": true,
	  "statusCode": 200,
	  "orderId": "order34",
	  "cartItems": [
	    {
	      "catalogRefId": "sku003",
	      "quantity": 1,
	      "description": "Milk Sugar Dry Fruits Rose Petal Jam ( Gulkand) Saffron",
	      "productId": "prd002",
	      "productTitle": "Mava Gulkand Roll",
	      "productSlug": "Mava Gulkand Roll",
	      "productBrand": "KC Das",
	      "productImage": "prd002_t.jpg",
	      "amount": 400
	    }
	  ],
	  "orderSubTotal": 400
	}

### Move To Checkout.
	Service URL - "/cart/moveToCheckout/{orderId}"
	HTTP Method - GET
	Service Response:
		{
	  "message": "Good for Checkout.",
	  "status": true,
	  "statusCode": 200,
	  "orderId": null,
	  "cartItems": null,
	  "orderSubTotal": 400
	}

## Shipping Services:	
	
### Add Address.
	Service URL - "/shipping/address/add"
	HTTP Method - POST
	Parameters :
	{
	"orderId":"order33",
	"firstName":"Abhishek",
	"lastName":"Mallick",
	"address1":"Flat 203 , Vahin Towers",
	"address2":"Bomanhalli",
	"city":"Bangalore",
	"state":"Karnataka",
	"postalCode":"560068",
	"country":"India",
	"email":"abhirick12@gmail.com",
	"phoneNumber":"7899788385"
	}
	Service Response:
		{
	  "message": "Address was successfully added.",
	  "status": true,
	  "statusCode": 200,
	  "dataMap": {
	    "Added_Address": [
	      {
	        "message": null,
	        "status": false,
	        "statusCode": 0,
	        "firstName": "Test",
	        "middleName": null,
	        "lastName": "Mallick",
	        "address1": "Flat 203 , Vahin Towers",
	        "address2": "Bomanhalli",
	        "address3": null,
	        "city": "Bangalore",
	        "state": "Karnataka",
	        "postalCode": "560068",
	        "country": null,
	        "phoneNumber": "7899788385",
	        "email": "abhirick12@gmail.com"
	      }
	    ]
	  }
	}
	
### Edit Address.
	Service URL - "/shipping/address/edit"
	HTTP Method - POST
	Parameters :
	{
	"orderId":"order33",
	"firstName":"Abhishek",
	"lastName":"Mallick",
	"address1":"Flat 203 , Vahin Towers",
	"address2":"Bomanhalli",
	"city":"Bangalore",
	"state":"Karnataka",
	"postalCode":"560068",
	"country":"India",
	"email":"abhirick12@gmail.com",
	"phoneNumber":"7899788385"
	}
	Service Response:
		{
	  "message": "Address was successfully Edited.",
	  "status": true,
	  "statusCode": 200,
	  "dataMap": {
	    "Edited_Address": [
	      {
	        "message": null,
	        "status": false,
	        "statusCode": 0,
	        "firstName": "Test",
	        "middleName": null,
	        "lastName": "Mallick",
	        "address1": "Flat 203 , Vahin Towers",
	        "address2": "Bomanhalli",
	        "address3": null,
	        "city": "Bangalore",
	        "state": "Karnataka",
	        "postalCode": "560068",
	        "country": null,
	        "phoneNumber": "7899788385",
	        "email": "abhirick12@gmail.com"
	      }
	    ]
	  }
	}
	 
### Remove Address.
	Service URL - "/shipping/address/remove/{orderId}"
	HTTP Method - GET

### Get Saved Addresses.
	Service URL - "/shipping/address/savedAddress/{orderId}"
	HTTP Method - GET
	Service Response:
		{
	  "message": "Shipment Address was successfully retreived.",
	  "status": true,
	  "statusCode": 200,
	  "dataMap": {
	    "shipmentAddress": [
	      {
	        "message": null,
	        "status": false,
	        "statusCode": 0,
	        "firstName": "Test",
	        "middleName": null,
	        "lastName": "Mallick",
	        "address1": "Flat 203 , Vahin Towers",
	        "address2": "Bomanhalli",
	        "address3": null,
	        "city": "Bangalore",
	        "state": "Karnataka",
	        "postalCode": "560068",
	        "country": null,
	        "phoneNumber": "7899788385",
	        "email": "abhirick12@gmail.com"
	      }
	    ]
	  }
	}

### Get Shipment Address.
	Service URL - "/shipping/address/shipmentAddress/{orderId}"
	HTTP Method - GET
	Service Response:
	{
	  "message": "Shipment Address was successfully retreived.",
	  "status": true,
	  "statusCode": 200,
	  "dataMap": {
	    "shipmentAddress": [
	      {
	        "message": null,
	        "status": false,
	        "statusCode": 0,
	        "firstName": "Test",
	        "middleName": null,
	        "lastName": "Mallick",
	        "address1": "Flat 203 , Vahin Towers",
	        "address2": "Bomanhalli",
	        "address3": null,
	        "city": "Bangalore",
	        "state": "Karnataka",
	        "postalCode": "560068",
	        "country": null,
	        "phoneNumber": "7899788385",
	        "email": "abhirick12@gmail.com"
	      }
	    ]
	  }
	}
	 
## Profile Services:	

### Add new Address In Profile.
	Service URL : "/account/address/add"
	Method : POST
	Parameters :
	{
		"firstName" : "Vivz",
		"lastName" : "Dwivedi",
		"emailAddress" : "vivzd@yahoo.com",
		"address1" : "HNO 84",
		"address2" : "BTM 1st Stage",
		"city" : "Bangalore",
		"state" : "KA",
		"zipcode" : "560029",
		"phoneNumber" : "8147928393"
	}
	
	Service Response : 
	{
	  "message": "Address Added Successfully",
	  "status": true,
	  "statusCode": 200,
	  "address": {
	    "emailAddress": "vivzd@yahoo.com",
	    "firstName": "Vivz",
	    "lastName": "Dwivedi",
	    "address1": "HNO 84",
	    "address2": "BTM 1st Stage",
	    "city": "Bangalore",
	    "state": "KA",
	    "zipcode": "560029",
	    "phoneNumber": "8147928393",
	    "profileId": "profile26",
	    "id": "address18"
	  }
	}
### Edit Address in Profile.
	Service URL : "/account/address/edit"
	Method : PUT
	Parameters : 
	{
		"id" : "address19",
		"firstName" : "Vivek",
		"lastName" : "Dwivedi",
		"emailAddress" : "vivzd@yahoo.com",
		"address1" : "HNO 84 Near Gangothri Circle",
		"address2" : "BTM 1st Stage",
		"city" : "Bangalore",
		"state" : "KA",
		"zipcode" : "560029",
		"phoneNumber" : "8147928393"
	}
	
	Service Response : 
	{
	  "message": "Address Updated Successfully",
	  "status": true,
	  "statusCode": 200,
	  "address": {
	    "emailAddress": "vivzd@yahoo.com",
	    "firstName": "Vivek",
	    "lastName": "Dwivedi",
	    "address1": "HNO 84 Near Gangothri Circle",
	    "address2": "BTM 1st Stage",
	    "city": "Bangalore",
	    "state": "KA",
	    "zipcode": "560029",
	    "phoneNumber": "8147928393",
	    "profileId": "profile26",
	    "id": "address19"
	  }
	}
	
### Remove Address From Profile.
	Service URL : "/account/address/{id}/remove"
	id : addressKey
	Method : DELETE
	Parameters : 
	{
		OAuth2Authentication
	}
	
	Service Response : 
	{
	  "message": "Address Removed Successfully",
	  "status": true,
	  "statusCode": 200
	}
	
### Edit Personal Details.
	Service URL : "/account/edit"
	Method : PUT
	Parameters : 
	{
		"firstName" : "Shalu",
		"lastName" : "Dwivedi",
		"emailAddress" : "vivz@yahoo.com"
	}
	
	Service Response :
	
	{
	  "message": "Profile Updated Successfully",
	  "status": true,
	  "statusCode": 200,
	  "user": {
	    "firstName": "Shalu",
	    "lastName": "Dwivedi",
	    "emailAddress": "vivz@yahoo.com"
	  }
	}
	
### Change Password.
	Service URL : "/account/changePassword"
	Method : PUT
	Parameters : 
	{
    "oldPassword" : "Admin@123",
    "newPassword" : "Admin@1234",
    "confirmPassword" : "Admin@1234"
	}
	
	Service Response :
	{
	  "message": "Password Updated Successfully",
	  "status": true,
	  "statusCode": 200
	}
	
### Get Order Details.
	Service URL : "/account/{orderId}/orderDetail"
	Method : GET
	Parameters : orderId : order12
	
### Get Order History.
	Service URL : "/account/orders"
	Method : GET
	Parameters : 
	{
		OAuth2Authentication
	}
	
### Get All Profile Addresses.
	Service URL : "/account/addresses"
	Method : GET
	Parameters : 
	{
		oAuth2Authentication
	}
	
	Service Response : 
	{
	  "message": "Profile Addresses Retrived Successfully",
	  "status": true,
	  "statusCode": 200,
	  "adressResponse": [
	    {
	      "message": null,
	      "status": false,
	      "statusCode": 0,
	      "id": "address18",
	      "firstName": "Vivz",
	      "lastName": "Dwivedi",
	      "address1": "HNO 84",
	      "address2": "BTM 1st Stage",
	      "city": "Bangalore",
	      "state": "KA",
	      "zipcode": "560029",
	      "phoneNumber": "8147928393",
	      "profileId": "profile26"
	    },
	    {
	      "message": null,
	      "status": false,
	      "statusCode": 0,
	      "id": "address19",
	      "firstName": "Vikas",
	      "lastName": "Dwivedi",
	      "address1": "HNO 38",
	      "address2": "BTM 2nd Stage",
	      "city": "Bangalore",
	      "state": "KA",
	      "zipcode": "560029",
	      "phoneNumber": "9685667831",
	      "profileId": "profile26"
	    }
	  ]
	}
	
### Create Profile.
	Service URL : "/account/create"
	Method : POST
	Parameters : 
	{
    "user" : {
        "emailAddress": "vivz@yahoo.com",
        "firstName": "Vivek",
        "lastName": "Dwivedi",
        "age": "25"
    },
    "password" : "Admin@123"
	}
	
	Service Response :
	{
	  "message": "User Created Successfully",
	  "status": true,
	  "statusCode": 200,
	  "apiUser": {
	    "emailAddress": "vivz@yahoo.com",
	    "firstName": "Vivek",
	    "lastName": "Dwivedi",
	    "age": 25,
	    "id": "profile26",
	    "name": "vivz@yahoo.com"
	  },
	  "oauth2AccessToken": {
	    "access_token": "2341254b-70a3-4606-886e-67307236989c",
	    "token_type": "bearer",
	    "refresh_token": "85b1fe95-9673-49f5-b6b7-e15847c2ae17",
	    "expires_in": 499,
	    "scope": "read write"
	  }
	}

### Get Profile Address
	Service URL : "/account/address/{id}"
	id : addressKey
	Method : GET
	Parameters : 
	{
		OAuth2Authentication
	}
	
	Service Response :
	{
	  "message": "Address found",
	  "status": false,
	  "statusCode": 200,
	  "id": "address18",
	  "firstName": "Vivz",
	  "lastName": "Dwivedi",
	  "address1": "HNO 84",
	  "address2": "BTM 1st Stage",
	  "city": "Bangalore",
	  "state": "KA",
	  "zipcode": "560029",
	  "phoneNumber": "8147928393",
	  "profileId": "profile26"
	}
	
## Merchandising Services:

### Product Upload. 
	Service URL - "/product/upload"
	HTTP Method - POST
	
### Images Upload. 
	Service URL - "/media/upload"
	HTTP Method - POST
	
### Brand Upload. 
	Service URL - "/brand/upload"
	HTTP Method - POST
	
### Inventory Upload. 
	Service URL - "/inventory/upload"
	HTTP Method - POST
	
### Category Upload. 
	Service URL - "/category/upload"
	HTTP Method - POST
	
### Price Upload. 
	Service URL - "/price/upload"
	HTTP Method - POST
	
### City Upload. 
	Service URL - "/city/upload"
	HTTP Method - POST
	
### SKU Upload. 
	Service URL - "/sku/upload"
	HTTP Method - POST