# HTTP Server

This is an HTTP server written in Java. 
It satisfies the tests listed in [CobSpec](https://github.com/8thlight/cob_spec).

## Installation

    git clone git@github.com:devlinglasman/httpServer.git

    cd httpServer

First, run the below to compile the JAR file with the correct version of Gradle (you do not need 
to install gradle first, this command will handle that for you):

    ./gradlew jar

Run the command below to start the server.

    java -jar ./httpServer/build/libs/httpServer-1.0-SNAPSHOT.jar 