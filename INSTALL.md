# How create the project

## Create Maven project
This project is a Maven project, initialised with this command :

	mvn archetype:create -X -DarchetypeArtifactId=maven-archetype-webapp -DartifactId=Croquette -DgroupId=fr.pinguet62.croquette

## Eclipse
Execute Maven command :

	mvn eclipse:eclipse

Creates `.project` file, `.classpath` file, `.settings` folder and update `.gitgnore` files for Eclipse.

### Import into Eclipse :
Go to `File`, `Import...`, `Existing Projects into Workspace`, ...(TODO)
Go to `Package Explorer` view, do right click on `Croquette` project, `Configure`, `Convert to Maven project`.

### Correct bug of Maven updating project :
Go to `Package Explorer` view, do right click on `Croquette` project, `Maven`, `Disable Nature Project`.
Execute `mvn eclipse:clean`
Go to `Package Explorer` view, fo right click on `Croquette` project, `Configure`, `Convert to Maven project`.

## Configuration

### JavaServer Faces

#### Dependencies
Insert into `pom.xml` file :

	<dependencies>
		<dependency>
			<groupId>com.sun.faces</groupId>
			<artifactId>jsf-api</artifactId>
			<version>2.2.2</version>
		</dependency>
		<dependency>
			<groupId>com.sun.faces</groupId>
			<artifactId>jsf-impl</artifactId>
			<version>2.2.2</version>
		</dependency>
	</dependencies>

#### Configuration
Insert into `web.xml` file :

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

#### Dependencies
Insert into `pom.xml` file to download Primefaces and theme :

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
		<!-- PrimeFaces -->
		<dependency>
			<groupId>org.primefaces</groupId>
			<artifactId>primefaces</artifactId>
			<version>3.5</version>
		</dependency>
		<dependency>
			<groupId>org.primefaces.extensions</groupId>
			<artifactId>primefaces-extensions</artifactId>
			<version>0.6.3</version>
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

#### Theme
Insert into `web.xml` file to set theme :

	<context-param>
		<param-name>primefaces.THEME</param-name>
		<param-value>bootstrap</param-value>
	</context-param>

### Servlet
This project uses Servlet 3.0 version.

#### Maven dependencies
Add this dependencies into `pom.xml` file :

	<dependency>
		<groupId>javax.servlet</groupId>
		<artifactId>javax.servlet-api</artifactId>
		<version>3.0.1</version>
		<scope>provided</scope>
	</dependency>

#### Annotation scan
Remove `metadata-complete="true"` option from `<web-app />` tag, that Tomcat can scan annotations into classes.
