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
	Service URL : "/account/addresses"
	Method : POST
	Parameters :
	{
	    "firstName" : "Vivek",
	    "lastName" : "Dwivedi",
	    "address1" : "HNO 89 20th Main 4th 'A' Cross",
	    "address2" : "Near gangothri Circle BTM 1st Stage",
	    "city" : "Bangalore",
	    "state" : "Karnataka",
	    "postalCode" : "560029",
	    "country" : "India",
	    "ownerId" : "profile15",
	    "phoneNumber" : "9893084117"
	}
	
### Edit Address in Profile.
	Service URL : "/account/editAddress/{id}" id - AddressKey
	Method : PUT
	Parameters : 
	{
	    "firstName" : "Vivz",
	    "lastName" : "Dwivedi",
	    "address1" : "HNO 85 20th Main 4th 'A' Cross",
	    "address2" : "Near gangothri Circle BTM 1st Stage",
	    "city" : "Bangalore",
	    "state" : "Tamilnadu",
	    "postalCode" : "560029",
	    "country" : "IN",
	    "ownerId" : "profile14",
	    "phoneNumber" : "9893084117"
	}
	
### Remove Address From Profile.
	Service URL : "/account/removeAddress/{id}"
	Method : DELETE
	Parameters : 
	{
	    "id" : "Vivz"
	}
	
### Edit Personal Details.
	Service URL : "/account/edit"
	Method : PUT
	Parameters : 
	{
	    "id" : "profile3",
	    "username" : "vivz123456@gmail.com",
	    "firstName" : "Vivek",
	    "lastName" : "Dwivedi",
	    "phoneNumber" : "9893084117"
	}
	
	
### Change Password.
	Service URL : "/account/changePassword"
	Method : PUT
	Parameters : 
	{
	                "id" : "profile3",
	    "oldPassword" : "Admin@123",
	    "newPassword" : "Admin@1234",
	    "confirmPassword" : "Admin@1234"
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
	
### Create Profile.
	Service URL : "/account/create"
	Method : POST
	Parameters : 
	{
	    "id" : "profile3",
	    "username" : "vivz123456@gmail.com",
	    "password" : "Admin@123",
	                "confirmPassword" : "Admin@123",
	    "firstName" : "Vivek",
	    "lastName" : "Dwivedi",
	    "authtoken" : "9591480b-0ba4-4d91-a065-dfe4d0020783",
	    "phoneNumber" : "9893084117"
	}
	Service Response :
		{
	  "message": "Account has been created",
	  "status": true,
	  "statusCode": 200,
	  "id": "profile3",
	  "firstName": "Abhishek",
	  "lastName": "Mallick",
	  "email": "abhirick12@gmail.com",
	  "address": [],
	  "phoneNumber": "9893084117"
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