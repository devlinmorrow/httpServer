# HTTP Server

This is an HTTP server written in Java which has been built to meet a challenge set by my company, 
8th Light. It satisfies the 8th Light official HTTP server tests which may be reviewed 
at [CobSpec](https://github.com/8thlight/cob_spec).


### Getting started

The Cob Spec and this server require [Maven](https://maven.apache.org/install.html) and 
[JDK 8](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html) so please 
download these first.

Clone the Cob Spec repo to save the test suite and root file directory onto your machine:  

    git clone git@github.com:8thlight/cob_spec.git 

Then clone this project's repo:

    git clone git@github.com:devlinglasman/httpServer.git

### Running the server

cd into this project's repo:

    cd httpServer

Compile the JAR file with the correct version of Gradle (note that you do not need to install 
gradle first - this command will handle that for you):

    ./gradlew jar

Execute the jar to start the server on your machine, making sure to replace the root 
directory argument below with the correct path to the Cob Spec's directory called 'public' on 
your machine (to find the full path, cd into the required directory and execute 'pwd'):

    java -jar ./httpServer/build/libs/httpServer-1.0-SNAPSHOT.jar -p 5000 -d (path-to-cob-spec-root-dir)/cob_spec/public
   
This server should now be running (note that there is no confirmation message).  

You can go to [http://localhost:5000/](http://localhost:5000) to see the public directory of files and make 
HTTP requests.  

### Running the Cob Spec tests

In order to run the Cob Spec tests against this project, you must first cd into your cob_spec directory
then build the cob spec project with the following command:

    mvn package

Now that it is built, you can start the Cob Spec server:

    java -jar fitnesse.jar -p 9090

Open your browser and go to [http://localhost:9090](http://localhost:9090/). 
You should see the Cob Spec website.

##### Configure the Cob Spec server

Before running the tests, you must configure some variables.

Navigate to the HttpTestSuite and click 'Edit' at the top. The 'User-Defined Variables' in the text
box are the ones you need to update.

1. SERVER_START_COMMAND is the command to start your server and the '.jar' file should be replaced with:

      *(path-to-httpServer-root)/build/libs/httpServer-1.0-SNAPSHOT.jar*

2. PUBLIC_DIR is the path to cob spec public folder of test files and it should be replaced with:

    *(path-to-cob-spec-root-dir)/public*

You must also remove the '-' at the beginning of each line in order for the User-Defined Variables 
to be recognised.

Click Save.

##### Run the Cob Spec Test Suite

Go back to the HttpTestSuite website's initial page and click 'Suite' at the top to run the full 
test suite. You can click into suites or tests and click 'Suite' or 'Test' respectively at the top to 
test these individually.  
  
  All tests should pass. 


### Issues and Wish List

* **Logging**  
The Logger class is very simple - logs are maintained as an array of Strings and to add a log, the class simply adds
a String input to such array, and this is (mostly) done only for the first line of requests (and does not include errors, 
for example). In the future, I would like to research how sophisticated logging systems work and employ 
this in my program.  

  Further, in the ConnectionManager class, the 'respondTo' method has two responsibilities: generating a response to
the request and logging. I would expect this issue to be eliminated by the use of a more sophisticated
logging system.  

* **Error Handling**  
I have not had a lot of experience with error handling and mostly have thrown errors. Given more time, I would like
to research how to handle errors better.

* **Threading**  
Currently my httpServer class makes a new ConnectionManager (the class which generates the 
Response and writes it to the output stream of the client connection) every time that a new 
client connection is made. This does not cause an issue in terms of speed or memory for the 
Cob Spec tests, but in a real server, this would not be the most efficient formulation for the 
program - there only needs to be one ConnectionManager. However, there is a conflict in use of 
resources somewhere in my program if I do not create a new ConnectionManager each time. So in the
future I would like to amend this.  

* **Setup for others**  
There is currently a reasonably large set of instructions that other developers must follow in 
order to set up running the Cob Spec test suite against this program. I would like to create more 
extensive build automation to cut down on such set up for others.  

* **Response Builder**  
Several of the Handlers build the same type of Response (e.g. 'Not Found Response') in certain
scenarios. Currently, they are each manually building such identical Responses - this should 
be performed by a Builder class.  
  
  This will also help with some of the 'magic Strings' that are in my code - places where I have hardcoded
String values which need to be removed, such as when setting the values of ResponseHeaders. 

* **File Handling**  
In addition to Response building, there are file manipulations shared between classes which essentially has led 
to code duplication. Given more time, I would like to come up with a better system for automating retrieving files
(for example, identifying their file type and automatically setting this as the Content-Type header of the response).  


### UML Activity Diagram  

While developing the server, I made this UML diagram to map out the flow of the program. This led me to understand
that HTTP requests should be split up into verb+path routes to be sent to a handler to be dealt with. Previously, 
I had thought that it would be best to split up requests at the verb first, and then subdivide based on path.  

![httpserver_umldiagram](https://user-images.githubusercontent.com/30846026/44061700-733dd42c-9f51-11e8-90a6-2e732567df9c.jpg)






