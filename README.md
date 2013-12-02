Blend4J Tutorials
=================

This project contains a few different tutorials for working with the Galaxy API using [blend4j](https://github.com/jmchilton/blend4j).  These tutorials assume that you have installed [Maven](http://maven.apache.org/) and that you are working with the [Eclipse IDE](http://eclipse.org/).  They also assume some familiarity with [Galaxy](http://galaxyproject.org/) including a working installation with administrator access.  Please see the [Get Galaxy](http://wiki.galaxyproject.org/Admin/Get%20Galaxy) guide for more information on how to install Galaxy.

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

This question walks you through adding blend4j to your project and uploading data to a Galaxy library.  The tasks are listed withing the pom.xml and src/main/java/Blend4JQuestion1 as 'TODO' comments.

The first step involves adding blend4j to your project within the pom.xml file.  Please refer to the __Jars__ section within the [blend4j project](https://github.com/jmchilton/blend4j) for more information on what to add.

The remaining steps involve hooking up to your running Galaxy instance and defining the file to upload.

When finished, please go to the __Shared Data__ section of your running Galaxy instance.  You should see your newly created Data library along with the uploaded file.
