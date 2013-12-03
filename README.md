Blend4J Tutorials
=================

This project contains a few different tutorials for working with the Galaxy API using [blend4j](https://github.com/jmchilton/blend4j) and [galaxy-bootstrap](https://github.com/jmchilton/galaxy-bootstrap), both written by [John Chilton](https://github.com/jmchilton).  These tutorials assume that you have installed [Maven](http://maven.apache.org/) and that you are working with the [Eclipse IDE](http://eclipse.org/) along with the [Maven Integration for Eclipse](http://maven.apache.org/eclipse-plugin.html).  They also assume some familiarity with [Galaxy](http://galaxyproject.org/) including a working installation with administrator access.  Please see the [Get Galaxy](http://wiki.galaxyproject.org/Admin/Get%20Galaxy) guide for more information on how to install Galaxy.

Getting Started
---------------

To get started with these tutorials, please run the following command:

	$ git clone https://github.com/apetkau/blend4j-tutorials.git

This creates a __blend4j-tutorials__ directory containing the code and other necessary files.  This directory is structured like:

	$ ls blend4j-tutorials
	blend4j-tutorial-answers  blend4j-tutorial-questions  README.md

Each of these subdirectories is a Maven project containing the questions and answers respectively.  To import these projects into eclipse please go to __File > Import...__.  From here select __Maven > Existing Maven Projects__ and click __Next__.  From here click __Browse__ and select the _blend4j-tutorials_ as the root directory.  This should list the _questions_ and _answers_ projects.  Please click __Finish__ to import.

The code for each of the questions should be located within src/main/java.  Please follow along below for each of the questions.

Question 1
----------

This question walks you through adding blend4j to your project and uploading data to a Galaxy library.  The tasks are listed withing the pom.xml and src/main/java/Blend4JQuestion1.java as 'TODO' comments.

The first TODO involves adding blend4j to your project within the pom.xml file.  Please refer to the __Jars__ section within the [blend4j project](https://github.com/jmchilton/blend4j) for more information on what to add.

The remaining TODOs involve hooking up to your running Galaxy instance and defining the file to upload.

When finished, please go to the __Shared Data__ section of your running Galaxy instance.  You should see your newly created Data library along with the uploaded file.

Question 2
----------

Question 1 involved creating a data library using blend4j, then visually looking for this library within the Galaxy web interface.  This question will re-factor that data library creation code into a test case using [JUnit](http://junit.org).  The code for this question, instead of being located within src/main/java, is located within src/test/java/Blend4JQuestion2.java.

The first step (TODO 1) involes moving some of the setup code to a setup() method that has been annotated with @Before.  The second and third steps (TODO 2 and 3) involve writing [Assert](http://junit.sourceforge.net/javadoc/org/junit/Assert.html) statements to programatically verify that the data library was succsessfully created and the file uploaded correctly.

When finished, please run the JUnit test and verify everything passes.

Question 3
----------

Questions 2 involved running code against a pre-configured Galaxy and hardcoding a URL and API Key.  It would be nice to automate this process by having a clean instance of Galaxy setup each time we wish to run these test suites.  This can be accomplished with the [galaxy-bootstrap](https://github.com/jmchilton/galaxy-bootstrap) project.

Galaxy bootstrap can be installed by adding the appropriate dependency within the pom.xml file (and Maven will go and download the correct version when necessary).  However, I created a fork of galaxy-bootstrap and added a vew extra print statements to help see what's going on behind the scenes.  This custom version can be built and installed using the following commands:

	$ git clone https://github.com/apetkau/galaxy-bootstrap.git
	$ cd galaxy-bootstrap	
	$ mvn -Dmaven.test.skip=true install

This first clones the customized version of galaxy-bootstrap and then builds the appropriate package (skipping tests since they take a while to run).  This package can be found within the target/ directory, for example:

	$ ls target/
	classes  galaxybootstrap-0.3.0-rc2-SNAPSHOT-apetkau.jar  generated-sources  maven-archiver

Once the package is built it is copied over to your local Maven repository, which on Linux is located within _~/.m2/repository_.  For example, to see the locally installed package run the following command:

	$ ls ~/.m2/repository/com/github/jmchilton/galaxybootstrap/galaxybootstrap/0.3.0-rc2-SNAPSHOT-apetkau
	galaxybootstrap-0.3.0-rc2-SNAPSHOT-apetkau.jar              galaxybootstrap-0.3.0-rc2-SNAPSHOT-apetkau.pom              _maven.repositories
	galaxybootstrap-0.3.0-rc2-SNAPSHOT-apetkau.jar.lastUpdated  galaxybootstrap-0.3.0-rc2-SNAPSHOT-apetkau.pom.lastUpdated

The path and version number of the package are defined within the __galaxybootstrap/pom.xml__ file and must also be entered as a dependency entry within the __blend4j-tutorials/blend4j-tutorial-questions/pom.xml__ file.  Please copy and paste the following information to __blend4j-tutorials/blend4j-tutorial-questions/pom.xml__ in order to setup the appropriate dependency (TODO 3.1):

	 <dependency>
	  <groupId>com.github.jmchilton.galaxybootstrap</groupId>
	  <artifactId>galaxybootstrap</artifactId>
	  <version>0.3.0-rc2-SNAPSHOT-apetkau</version>
	 </dependency>

More information in building and installing Maven projects can be found at [Maven Install Plugin](http://maven.apache.org/plugins/maven-install-plugin/).

Step 2 (TODO 3.2) of this question involves creating a new GalaxyInstance by filling in the appropriate URL and API Key.  This information is obtained from galaxy-bootstrap instead of being hardcoded into a String.

Once you are finished each of the tasks, please run the tests.  This will first download the Galaxy code and build Galaxy, then adjust some of the Galaxy settings and start Galaxy running.  After this point, the tests will run.  Please monitor the Console for information on what is happening.

More Examples
-------------

More examples on how to use blend4j as well as galaxy-bootstrap can be found on the [blend4j](https://github.com/jmchilton/blend4j) site as well as the [tests for blend4j](https://github.com/jmchilton/blend4j/tree/master/src/test/java/com/github/jmchilton/blend4j/galaxy).
