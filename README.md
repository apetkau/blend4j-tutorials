Blend4J Tutorials
=================

This project contains a few different tutorials for working with the Galaxy API using [blend4j](https://github.com/jmchilton/blend4j).  These tutorials assume that you have installed [Maven](http://maven.apache.org/) and that you are working with the [Eclipse IDE](http://eclipse.org/) along with the [Maven Integration for Eclipse](http://maven.apache.org/eclipse-plugin.html).  They also assume some familiarity with [Galaxy](http://galaxyproject.org/) including a working installation with administrator access.  Please see the [Get Galaxy](http://wiki.galaxyproject.org/Admin/Get%20Galaxy) guide for more information on how to install Galaxy.

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

The first step involves adding blend4j to your project within the pom.xml file.  Please refer to the __Jars__ section within the [blend4j project](https://github.com/jmchilton/blend4j) for more information on what to add.

The remaining steps involve hooking up to your running Galaxy instance and defining the file to upload.

When finished, please go to the __Shared Data__ section of your running Galaxy instance.  You should see your newly created Data library along with the uploaded file.

Question 2
----------

Question 1 involved creating a data library using blend4j, then visually looking for this library within the Galaxy web interface.  This question will re-factor that data library creation code into a test case using [JUnit](http://junit.org).  The code for this question, instead of being located within src/main/java, is located within src/test/java/Blend4JQuestion2.java.

The first step (TODO 1) involes moving some of the setup code to a setup() method that has been annotated with @Before.  The second and third steps involve writing [Assert](http://junit.sourceforge.net/javadoc/org/junit/Assert.html) statements to programatically verify that the data library was succsessfully created and the file uploaded correctly.

When finished, please run the JUnit test and verify everything passes.

Question 3
----------

Questions 2 involved running code against a pre-configured Galaxy and hardcoding a URL and API Key.  It would be nice to automate this process by having a clean instance of Galaxy setup each time we wish to run these test suites.  This can be accomplished with the [galaxy-bootstrap](https://github.com/jmchilton/galaxy-bootstrap) project.
