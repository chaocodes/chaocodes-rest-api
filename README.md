ChaoCodes Rest API
==================
> **This project is not currently production ready. See Todo List for features that still need to be implemented.**

Introduction
------------------
This project is the backend for [ChaoCodes.Com](http://chaocodes.com/), a personal website for me (Chao). The idea behind the project was
to create a seperation of logic between the backend and frontend of my website to allow for multi-server hosting if needed.
Furthermore, this project was created so that I can learn the Java Spring web framework and familiarize myself with testing and
documentation in Java.

Technologies
------------------
* Maven
* Spring Boot
* Spring Data JPA
* Spring Security
* Jackson
* JUnit
* HSQLDB (For testing, production will possibly use MySQL)
* JsonPath
* JSONDoc

Documentation
------------------
Documentation is being generated using JSONDoc. This may change in the future. More information may be found [here](http://jsondoc.org/index.html).
JSONDoc settings can be found in `src/main/resources/application.properties`. JSONDoc settings are prefixed with `jsondoc`. By default you can view the docs
by going to `http://localhost:8080/api/jsondoc-ui.html` with `http://localhost:8080/api/jsondoc` as the parameter.

Todo List
-------------------
- [ ] Implement Skill API
	- [ ] API
	- [ ] Tests
	- [ ] Documentation
- [ ] Implement Project API
	- [ ] API
	- [ ] Tests
	- [ ] Documentation
- [x] Implement Course API
	- [x] API
	- [x] Tests
	- [x] Documentation
- [ ] Implement Static (About Me, Resume Link, others..?) API
	- [ ] API
	- [ ] Tests
	- [ ] Documentation
- [ ] Implement Blog API
	- [ ] API
	- [ ] Tests
	- [ ] Documentation
	- [ ] Use Disqus Comments?
- [ ] Custom Validators
	- [ ] Unique username, email, etc..
- [ ] Custom Error Messages
- [ ] Implement Spring Security, OAuth2?
	- [ ] User Passwords
	- [ ] Hash Passwords
- [ ] Integration Testing, TravisCI?
- [ ] Frontend (AngularJS?, Bootstrap, JQuery)
