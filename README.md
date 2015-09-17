#Participant comments

##Application configuration

Default configurations are included in app archive classpath:config/default/application.properties. To overwrite defaults you basically need add properties file to applications accessible classpath keeping folders/files structure: config/custom/*.properties  
App configuration properties will be rewritten found values in config/custom/*.properties


##Authentication

###REST authentication
HTTP Basic allows clients with credentials weather/w34th3r;

###SOAP authentication
UserNametoken security authentication



##Tested


* mvn clean install

* mvn tomcat:run
 - http://localhost:8080/weather/ws/weather.wsdl
 - http://localhost:8080/weather/weather/Vilnius
 - http://localhost:8080/weather/weather/Riga - info message
 - http://localhost:8080/weather/weather/LV/Riga 
 - http://localhost:8080/weather/weather/Jonava - not cached
 - http://localhost:8080/weather/weather/ - all popular cities
  
* mvn jetty:run
 - http://localhost:8080/ws/weather.wsdl
 - http://localhost:8080/weather/Vilnius
 - http://localhost:8080/weather/Riga - info message
 - http://localhost:8080/weather/LV/Riga 
 - http://localhost:8080/weather/Jonava - not cached
 - http://localhost:8080/weather/ - all popular cities

Provided soapUI project: /weather/src/test/Tieto-weather-soapui-project.xml 
  
windows 7 env.



------------------------------------------------------------------------------------------------------

# About

Company needs an internal weather service which will be used company-wide in different applications and services:

 * Portlets in intranet applications;
 * Remotely accessible via mobile devices;
 * Warnings on bad weather linked to corporate trips;


Current implementation simply proxies a well known SaaS provider weather service [http://www.wunderground.com](http://www.wunderground.com) and does not provide any additional functionality to clients. 

Initially, when service was not used broadly, current solution was feasible, but once usage increased, performance did not meet SLA (response time, uptime) and internal project for reimplementation was approved.

# Requirements for implementation

## General requirements/considerations

 1. Service must respond either with weather data or meaningfull responses in case of erroneous requests;
 2. Sufficient logging for production support must be implemented;
 3. Clients must authenticate using HTTP Basic auth. Should only allow clients with credentials weather/w34th3r;
 4. You must basically ignore [http://www.wunderground.com](http://www.wunderground.com) request limits, as for production deployment company will use paid account with request limits lifted.
 5. Meaningful set of automated JUnit tests must be implemented. Test-coverage of less than 70% is acceptable with proper motivation/explanation.

## Predefined locations

Predefined locations is a set of locations that are preconfigured for application before depoyment - can be either external or packaged configuration.

 1. Service must provide response times of less than 500ms for preconfigured locations. Locations must be provided via application configuration;
 2. Service must return recent observations for supported locations. Data should not be older than 3 hours, unless 3rd party weather service is down for a long period of time;
 3. Both provided SOAP and REST endpoints must return same data;
 4. Initial list of predefined locations: Vilnius, Riga, Tallinn, Helsinki.

## Popular locations

Popular locations are the locations that are requested via clients for more than n times during lifetime of application (between redeployments/restarts).

 1. Requests for locations that are not in predefined location list still bust be satisfied, but without strict SLA requirements;
 2. If location is requested for more than n times (n is configurable), it must be automatically included in 'Predefined location' framework and satisfy same 500ms SLA requirement. Though this will be reset after application redeployment/restart.

 
