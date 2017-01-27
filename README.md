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

Mongo DB start-up
<Mongo Server installation>\Server\3.4\bin>mongod.exe --dbpath "C:\Program Files\MongoDB\data"