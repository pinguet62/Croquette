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


## Configuration
### Servlet
Insert into `web.xml` :

	<servlet>
		<servlet-name>Faces Servlet</servlet-name>
		<servlet-class>javax.faces.webapp.FacesServlet</servlet-class>
		<load-on-startup>1</load-on-startup>
	</servlet>
 
	<servlet-mapping>
		<servlet-name>Faces Servlet</servlet-name>
		<url-pattern>*.xhtml</url-pattern>
	</servlet-mapping>

### PrimeFaces
Insert into `pom.xml` to download Primefaces and theme :

	<repositories>
		<repository>
			<!-- PrimeFaces -->
			<id>prime-repo</id>  
			<name>PrimeFaces Maven Repository</name>
			<url>http://repository.primefaces.org</url>
			<layout>default</layout>
		</repository>
	</repositories>
	<dependencies>
		<dependency>
			<groupId>org.primefaces</groupId>
			<artifactId>primefaces</artifactId>
			<version>3.5</version>
		</dependency>
		<dependency>
			<groupId>org.primefaces.themes</groupId>
			<artifactId>bootstrap</artifactId>
			<version>1.0.10</version>
		</dependency>
		<!-- Apache Commons -->
		<dependency>
			<groupId>commons-fileupload</groupId>
			<artifactId>commons-fileupload</artifactId>
			<version>1.2</version>
		</dependency>
	<dependencies>

Insert into `web.xml` to set theme :

	<context-param>
		<param-name>primefaces.THEME</param-name>
		<param-value>bootstrap</param-value>
	</context-param>
