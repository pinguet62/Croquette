# How create the project

## Create Maven project
This project is a Maven project, initialised with this command :

	mvn archetype:create -X -DarchetypeArtifactId=maven-archetype-webapp -DartifactId=Croquette -DgroupId=fr.pinguet62.croquette

## Eclipse
Create `.project` file, `.classpath` file, `.settings` folder and update `.gitgnore` files for Eclipse :

	mvn eclipse:eclipse

Import into Eclipse :

	?? `File` > `Import...` > `Existing Projects into Workspace`
	`Package Explorer` view > Right click on `Croquette` project > `Configure` > `Convert to Maven project`

Correct bug of Maven updating project :

	`Package Explorer` view > Right click on `Croquette` project > `Maven` > `Disable Nature Project`
	mvn eclipse:clean
	`Package Explorer` view > Right click on `Croquette` project > `Configure` > `Convert to Maven project`
