#pallete-commerce

# Spring BOOT based ECommerce Framework

## Prerequisites

### Develop
- [Git](http://git-scm.com/downloads)
- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Spring Boot](http://docs.spring.io/spring-boot)
- [MongoDB]
- [Spring-Security]

### Deployment
- [Download JBOSS Application Server 7] (https://developers.redhat.com/products/eap/download/)
- Run maven install of [POM.xml] to generate the .war file
- Copy the war file into [<EAP-7.0.0 installation>\standalone\deployments]
- Create gc.log file in [<EAP-7.0.0 installation>\standalone\log]
- Start JBoss server using the bat file [<EAP-7.0.0 installation>\bin\standalone.bat]

### Server Start-up
JBoss Server start-up in debug mode
<EAP-7.0.0 installation>\bin>standalone.bat --debug

###Mongo DB start-up

Starting Mongo using user access:
- [Start Mongo]
	>mongod.exe -dbpath "C:\Program Files\MongoDB\data"
- [Connect to mongo] 
	>mongo --port 27017
- [User Administration]
	$ use admin
	$ db.createUser(
	  {
		user: "superAdmin",
		pwd: "admin123",
		roles: [ { role: "root", db: "admin" } ]
	  })
- [Re-start the MongoDB instance with access control]
	> mongod.exe -auth -dbpath "C:\Program Files\MongoDB\data"
- [Connect to database instance with superAdmin access]
	> mongo --port 27017 -u "superAdmin" -p "admin123" --authenticationDatabase "admin"
- [Create user access (readWrite) for shop]
	>mongo --port 27017 -u "superAdmin" -p "admin123" --authenticationDatabase "admin"
	$use shop
	$db.createUser(
	  {
	   user: "shopUser",
	   pwd: "shop123",
	   roles: [ "readWrite"]
	})
- [Staring Mongo]
	<Mongo Server installation>\Server\3.4\bin>mongod.exe -auth -dbpath "C:\Program Files\MongoDB\data"

###Application URL
- Login: http://www.palletteapart.com/boot/login
- Registration: http://www.palletteapart.com/boot/registration
- Access Token: http://www.palletteapart.com/boot/oauth/token (Body: grant_type:password, client_id:acme, client_secret:acmesecret, username:XXXX ,password:XXXXXX) (Authorization: user: XXXX password XXXXX)
- Get Product: http://www.palletteapart.com/boot/rest/api/v1/products (Header: Authorization:Bearer 04da0457-375a-4f62-bcd0-97ad7456c8ed)