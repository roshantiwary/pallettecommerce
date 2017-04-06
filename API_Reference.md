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
	
### Remove Item .
	Service URL - "/cart/remove"
	HTTP Method - POST
	Parameters :
	{
	"orderId":"order33",
	"productId":"prd002",
	"skuId":"sku003"
	}	
	
### Get Cart Details .
	Service URL - "/cart/{orderId}/details"
	HTTP Method - GET

### Move To Checkout.
	Service URL - "/cart/moveToCheckout/{orderId}"
	HTTP Method - GET


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
	 
### Remove Address.
	Service URL - "/shipping/address/remove/{orderId}"
	HTTP Method - GET

### Get Saved Addresses.
	Service URL - "/shipping/address/savedAddress/{orderId}"
	HTTP Method - GET

### Get Shipment Address.
	Service URL - "/shipping/address/shipmentAddress/{orderId}"
	HTTP Method - GET
	 
	 
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
	URL : "/account/{orderId}/orderDetail"
	Method : GET
	Parameters : orderId : order12
	
### Get Order History.
	URL : "/account/orders"
	Method : GET
	Parameters : 
	{
		OAuth2Authentication
	}
	
### Get All Profile Addresses.
	URL : "/account/addresses"
	Method : GET
	Parameters : 
	{
		oAuth2Authentication
	}
	
### Create Profile.
	URL : "/account/create"
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
