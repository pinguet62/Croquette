# How create the project

## Create Maven project

Run this Maven command to initialize the Maven project:

```
mvn archetype:create -X -DarchetypeArtifactId=maven-archetype-webapp -DartifactId=Croquette -DgroupId=fr.pinguet62.croquette
```

This command will generate this structure in current path:

```
|-- pom.xml
`-- src
	`-- main
		|-- resources
		`-- webapp
			|-- index.jsp
			`-- WEB-INF
				`-- web.xml
```

The file `src/main/webapp/index.jsp` can be deleted for JSF project.

## Eclipse

### Generate configuration files (obsolete)

Run this Maven command to initialize the project to Eclipse:

```
mvn eclipse:eclipse
```

This command will create, `.project`, `.classpath`, `.settings` files.

### Gitgnore

The three configuration files will not be committed.

Add this content to `.gitgnore` file:

```
/.classpath
/.project
/.settings
```

### Import project into Eclipse

`Package Explorer` view, right click on `JSFBaseProject` project, `Maven`, `Update Project...`.

`Package Explorer` view, right click on `JSFBaseProject` project, `Configure`, `Convert to Maven project`.

### Correct bug of Maven updating project

`Package Explorer` view, right click on `JSFBaseProject` project, `Maven`, `Disable Nature Project`.

Run this Maven command:

```
mvn eclipse:clean
```

`Package Explorer` view, right click on `JSFBaseProject` project, `Configure`, `Convert to Maven project`.

## Configuration

### JavaServer Faces

#### Dependencies

Add to `pom.xml` file:

```xml
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
```

#### Configuration

Add to `web.xml` file:

```xml
<servlet>
	<servlet-name>Faces Servlet</servlet-name>
	<servlet-class>javax.faces.webapp.FacesServlet</servlet-class>
	<load-on-startup>1</load-on-startup>
</servlet>

<servlet-mapping>
	<servlet-name>Faces Servlet</servlet-name>
	<url-pattern>*.xhtml</url-pattern>
</servlet-mapping>
```

### PrimeFaces

#### Dependencies

Add to `pom.xml` file:

```xml
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
```

#### Theme

*The theme used here is `bootstrap`.*

##### Constant

Add to `web.xml` file:

```xml
<context-param>
	<param-name>primefaces.THEME</param-name>
	<param-value>bootstrap</param-value>
</context-param>
```

##### Switcher

To change dynamically the theme:

1. Declare a Bean:

``` java
@ManagedBean(name = "themeManagedBean")
@SessionScoped
public class ThemeManagedBean {
	private String theme = "bootstrap";
	
	public Theme getTheme() {
		return theme;
	}
}
```

It should be stored in session, to be specific to the user, and be kept during the navigation between pages.

2. Add to `web.xml` file:

``` xml
<context-param>
	<param-name>primefaces.THEME</param-name>
	<param-value>#{themeManagedBean.theme}</param-value>
</context-param>
```

It uses the previous Bean.

### Servlet

This project uses Servlet 3.0 version.

#### Maven dependencies

Add to `pom.xml` file:

``` xml
<dependency>
	<groupId>javax.servlet</groupId>
	<artifactId>javax.servlet-api</artifactId>
	<version>3.0.1</version>
	<scope>provided</scope>
</dependency>
```

#### Annotation scan

Remove `metadata-complete="true"` option from `<web-app />` tag, that Tomcat can scan annotations into classes.
