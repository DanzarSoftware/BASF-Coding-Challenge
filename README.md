# BASF-Coding-Challenge
#### Deploy project with docker (On the root of the project "/BASF-Coding-Challenge"):

`$ docker-compose up`


**Interesting endpoints:**


`Swagger Interface` : <http://localhost:8080/swagger-ui.html#>

`Execute the pipeline` : post </api/chemical> and you need to upload the zip file.

`Remove the whole database` : delete </api/chemical>

`Get patents containing a chemical` : get </api/patent/{chemical}>


**Considerations:**

Spring Boot 2, Java8, and Swagger have been used for this project. 

The code has been organized with controllers to receive the requests and some services to do the business logic.

The controllers have been documented with Swagger and we can do the request from the user interface (<http://localhost:8080/swagger-ui.html#>).

I decided to use a MongoDB database to improve the query times. For this, I used MongoDB Atlas a fully managed Cloud Database service.

To handle the parsing errors I created some custom exceptions like XmlDocumentException and UnzipException that are handled by RestResponseStatusExceptionResolver. This code will support the XML format changes because the XmlUtils class selects the name of the attributes and nodes of the XML document, so if the names are the same this code will continue working.

To run the Named Entity Recognition (NER) I started using OpenNlp. I was not able to find any trained model of chemicals and I started to train my own model, but following the OpenNlp documentation I should use 15000 sentences to train a good model and tag manually all these sentences, this work would take much time. So I decided to use a chemical tagger external lib, In this project, I use the chemicaltagger created by the University of Cambridge. Here is the official documentation:

Publication: https://jcheminf.biomedcentral.com/articles/10.1186/1758-2946-3-17

Documentation: https://www.javadoc.io/doc/uk.ac.cam.ch.wwmm/chemicalTagger/latest/index.html

GitHub project: https://github.com/BlueObelisk/chemicaltagger

This lib finds all the chemical components, the problem is that many of the results obtained are false negatives, containing non-chemical entities. For this reason, I have created an endpoint to search for patents containing a particular chemical (/api/patent/{chemical}).

I created in the root of the project a file with 150 patents(Test150.zip) to test the application. The time of process 150 files is 10 minutes. I tested with 7000 and 10000 files, the application is running for hours and the program ends successfully. I know that this time is unacceptable for a REST request, so I think that it would be better to upload the file with ftp and use batch process to process this data or a Spark Job.

Unitary tests have been done with Mockito, I covered all the methods.

The tests are executed with `$ docker-compose up`  

The application has been dockerized, for this, I have created a Dockerfile that compiles the application with a Gradle wrapper and runs the application. This process is orchestrated with docker-compose in case we decide to use a database or add other components to the architecture in the future.
