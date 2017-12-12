# SDE 2017 Assigment 3 Server 


**Assigment 3 Server - Mattia Buffa mattia.buffa-1@studenti.unitn.it**  
**Assigment 3 Server WSDL heroku: http://server-as3.herokuapp.com/people?wsdl**  
**Partner Denis Gallo**
**Assigment 3 Partner Server repository: https://github.com/DenisGallo/introsde-2017-assignment-3-server**  
**Assigment 3 Partner Client repository: https://github.com/DenisGallo/introsde-2017-assignment-3-client**  
**Assigment 3 Partner Server WSDL heroku: http://assignment3-denisgallo.herokuapp.com/people?wsdl**  

**Table of Contents**

- [SDE 2017 Assigment 3 Server](#sde-2017-assigment-3-Server)
	- [Project Description](#project-description)
		- [Code analysis](#code-analysis)
		- [Code tasks](#code-tasks)
		- [Execution](#execution)
		- [Additional Notes](#additional-notes)

## Project Description

### Code analysis

**WS**

This package contains the exposed interface and its implementation.

**Models**

All the models are inside the package as.rest.model these classes contains the JAXB and JPA annotations to define the xml and database rappresentation. 
Person and Activity have ManyToOne and OneToMany link.
ActivityType and Activity have ManyToOne and OneToMany link.

**DAOs**

All the DAOs are inside the package as.rest.dao these classes contains methods that will connect and interact with the SQLite database.
DatabaseDao is used to initialize and populate the database.

**PeoplePublisher.java**

This class let the server to be executed as Java Application creating it locally.

### Code tasks

**WS**

PeopleImpl.java manages the execution of the requested methods listed inside People.java interface. 

**Build.xml**

The file build.xml contains all the task that will:  
- install, download and resolve ivy dependencies  
- compile classes  
- create war
	
### Execution

Server is on at link: http://server-as3.herokuapp.com/people?wsdl  

### Additional Notes
  
*Bugs don't exist there are only strange features.*
