# dependency-management

## dependency management 

[Dependency Management Resource](https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#dependency-management)

This repository dependency management, plugin management, code analysis, unit test coverage etc. examples are available.

You should add `dependencyManagement` in parent pom.

```
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
                <version>${spring.boot.version}</version>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-data-jpa</artifactId>
                <version>${spring.boot.version}</version>
            </dependency>
            <dependency>
                <groupId>org.postgresql</groupId>
                <artifactId>postgresql</artifactId>
                <version>${postgresql.version}</version>
                <scope>runtime</scope>
            </dependency>
            ...
        </dependencies>
    </dependencyManagement>
```

`version` added to `properties` to manage versions.

```
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <spring.boot.version>2.2.4.RELEASE</spring.boot.version>
        <postgresql.version>42.2.18</postgresql.version>
        ...
    </properties>
```

## Plugin Management

[Plugin Management Resource](https://maven.apache.org/pom.html#plugin-management)

You should add `pluginManagement` in parent pom.
To enable overriding from child pom, you firstly define in `pluginManagement` then define in `plugins` in the parent pom.

```
    <build>
        <pluginManagement>
            <plugins>
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                    <version>${spring.boot.version}</version>
                    <executions>
                        <execution>
                            <goals>
                                <goal>repackage</goal>
                            </goals>
                        </execution>
                    </executions>
                </plugin>
                ...
            </plugins>
        </pluginManagement>
        <plugins>
             <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            ...
        <plugins>
    </build>    

```

### Checkstyle Plugin

[Checkstyle Resource](https://maven.apache.org/plugins/maven-checkstyle-plugin/)

You will see plugin examples of checkstyle, findbugs and jacoco.
First starting with checkstyle.

- You should add checkstyle to both `pluginManagement` and `plugins`.

```
<build>
	...
	<plugins>
	    <plugin>
	        <groupId>org.springframework.boot</groupId>
	        <artifactId>spring-boot-maven-plugin</artifactId>
	    </plugin>

	    <!-- checkstyle -->
	    <plugin>
	        <groupId>org.apache.maven.plugins</groupId>
	        <artifactId>maven-checkstyle-plugin</artifactId>
	        <dependencies>
	            <dependency>
	                <groupId>com.puppycrawl.tools</groupId>
	                <artifactId>checkstyle</artifactId>
	                <version>${maven.checkstyle.puppycrawl.version}</version>
	            </dependency>
	            <dependency>
	                <groupId>tz.go.ega</groupId>
	                <artifactId>checkstyle-config</artifactId>
	                <version>${checkstyle.config.version}</version>
	            </dependency>
	        </dependencies>
	        <configuration>
	            <configLocation>checkstyle.xml</configLocation>
	            <encoding>${project.build.sourceEncoding}</encoding>
	            <failsOnError>${checkstyle.check.failsOnError}</failsOnError>
	            <failOnViolation>${checkstyle.check.failsOnError}</failOnViolation>
	            <maxAllowedViolations>0</maxAllowedViolations>
	            <violationSeverity>warning</violationSeverity>
	            <logViolationsToConsole>true</logViolationsToConsole>
	            <consoleOutput>true</consoleOutput>
	            <skip>${checkstyle.check.skip}</skip>
	            <propertyExpansion>lineLength=160</propertyExpansion>
	            <sourceDirectories>
	                <sourceDirectory>${project.build.sourceDirectory}</sourceDirectory>
	                <sourceDirectory>${project.build.testSourceDirectory}</sourceDirectory>
	            </sourceDirectories>
	        </configuration>
	        <executions>
	            <execution>
	                <id>validate</id>
	                <goals>
	                    <goal>check</goal>
	                </goals>
	            </execution>
	        </executions>
	    </plugin>
	  	...
	</plugins>
</build>

```

Checkstyle execute rules which we define in checkstyle.xml in build phase. Example: If line length greater than 160 character, fail build.

We add checkstyle.xml in `configLocation`.Since we don't have artifact management tool (Like `jfrog, nexus`), we created checkstyle-config service. You can use artifact management tool and add url of checkstyle.xml in your project (http://nexus.domain.com/.../checkstyle.xml).

- `skip` tag take true or false. If it is set to true, run the rules but build does not fail.
If it is set to false, run the rules and build fails.

- `failsOnError` tag If this is true, and Checkstyle reported any violations or errors, the build fails immediately after running Checkstyle, before checking the log for logViolationsToConsole. If you want to use logViolationsToConsole, use failOnViolation instead of this.

- `execution` and `goal` are checkstyle plugin execute phase(Like: check)

### FindBugs Plugin

[Findbugs Resource](https://gleclaire.github.io/findbugs-maven-plugin/index.html)

You should add findbugs-maven-plugin to both inside `pluginManagement` and `plugins`.

```

<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>findbugs-maven-plugin</artifactId>
    <configuration>
        <effort>Max</effort>
        <threshold>Low</threshold>
        <xmlOutput>true</xmlOutput>
        <skip>${findbugs.check.skip}</skip>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>check</goal>
            </goals>
        </execution>
    </executions>
</plugin>

```

- The `effort` when maxed out, performs a more complete and precise analysis, revealing more bugs in the code.

- The `xmlOutput` Optional directory to put findbugs xdoc xml report.

- The `skip` If it is set to true, run the rules but build does not fail. If it is set to false, run the rules and build fails.

- The `threshold` An optional attribute. It specifies the confidence/priority threshold for reporting issues. 
If set to "Low", confidence is not used to filter bugs. 
If set to "Medium" (the default), low confidence issues are supressed. 
If set to "High", only high confidence bugs are reported.

- The findbugs:check `goal` allows you to configure your build to fail if any errors are found in the FindBugs report.

### Jacoco (Java Code Coverage) Plugin

[Jacoco Resource](https://dzone.com/articles/reporting-code-coverage-using-maven-and-jacoco-plu)

You should add jacoco to both inside `pluginManagement` and `plugins`.

Jacoco is used for measuring unit test coverage.

```

<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <configuration>
        <destFile>${basedir}/target/coverage-reports/jacoco-unit.exec</destFile>
        <dataFile>${basedir}/target/coverage-reports/jacoco-unit.exec</dataFile>
        <excludes>
            <exclude>**/dto/**</exclude>
            <exclude>**/model/**</exclude>
            <exclude>**/entity/**</exclude>
            <exclude>**/enums/**</exclude>
            <exclude>**/mapper/**</exclude>
            <exclude>**/builders/**</exclude>
            <exclude>**/controller/**Controller**</exclude>
            <exclude>**/controller/**/**Controller**</exclude>
            <exclude>**/controller/migration/**</exclude>
            <exclude>**/controller/query/**</exclude>
            <exclude>**/controller/rest/**</exclude>
            <exclude>**/controller/resource/**</exclude>
            <exclude>**/controller/capability/**</exclude>
            <exclude>**/client/**</exclude>
            <exclude>**/exception/**</exclude>
            <exclude>**/**Application**</exclude>
            <exclude>${basedir}/target/**</exclude>
            <exclude>**/configuration/**</exclude>
            <exclude>**/configurations/**</exclude>
            <exclude>**/util/**</exclude>
            <exclude>**/clients/**</exclude>
            <exclude>**/lib/**</exclude>
            <exclude>**/context/**</exclude>
        </excludes>
        <destFile>${sonar.jacoco.reportPath}</destFile>
        <append>true</append>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <!-- attached to Maven test phase -->
        <execution>
            <id>report</id>
            <phase>install</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
        <execution>
            <id>jacoco-site</id>
            <phase>package</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
        <execution>
            <id>jacoco-check</id>
            <goals>
                <goal>check</goal>
            </goals>
            <configuration>
                <skip>${jacoco.check.skip}</skip>
                <rules>
                    <rule>
                        <element>PACKAGE</element>
                        <limits>
                            <limit>
                                <counter>LINE</counter>
                                <value>COVEREDRATIO</value>
                                <minimum>0.8</minimum>
                            </limit>
                            <limit>
                                <counter>BRANCH</counter>
                                <value>COVEREDRATIO</value>
                                <minimum>0.7</minimum>
                            </limit>
                        </limits>
                    </rule>
                </rules>
            </configuration>
        </execution>
    </executions>
</plugin>

```

- The `prepare-agent` goal sets up the property argLine (for most packaging types), pointing to the JaCoCo runtime agent. 
You can also pass argLine as a VM argument. 
Maven-surefire-plugin uses argLine to set the JVM options to run the tests.

- The Jacoco Java agent will collect coverage information when maven-surefire-plugin runs the tests. 
It will write it to `destFile` property value. (Default: "`target/jacoco.exec`")

- The `report` goal creates code coverage reports for tests in HTML, XML, CSV formats.

- The `check` goal validates that the coverage rules are met. (bound to the mvn clean install/verify phase)

- Jacoco `coverage-report` goal (bound to the mvn clean install/verify phase) generates HTML, XML and CSV reports.

- The `exclude` if you exclude a package, jacoco will ignore.

- The `LINE` counter that needs coverage of at least 80 percent.

- The `BRANCH` counter that needs coverage of at least 70 percent.

### Child Pom Import

In order to use parent pom in child pom, you should execute mvn clean install/verify.

You called parent pom from child pom as below.

```
    <parent>
        <groupId>tz.go.ega</groupId>
        <artifactId>parent-pom</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </parent>
```

You override `properties` and `dependency` on child pom

You have to define `dependencies`, which you define with `dependencyManagement` in parent pom, in child pom.

```
    <properties>
        <checkstyle.check.skip>false</checkstyle.check.skip>
        <checkstyle.check.failsOnError>false</checkstyle.check.failsOnError>
        ...
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        ...
    </dependencies>
```

You can override `plugin` configuration in child pom.

```
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <configuration>
        <excludes>
            <exclude>**/dto/**</exclude>
            <exclude>**/controller/**Controller**</exclude>
            <exclude>**/**Application**</exclude>
            <exclude>**/configuration/**</exclude>
            <exclude>**/enums/**</exclude>
            <exclude>**/client/**</exclude>
            <exclude>**/utils/BeanProvider.class</exclude>
            <exclude>**/service/LogModService.class</exclude>
            <exclude>**/listener/**</exclude>
        </excludes>
        <destFile>${sonar.jacoco.reportPath}</destFile>
        <append>true</append>
    </configuration>
    <executions>
        <execution>
            <id>default-prepare-agent</id>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>default-report</id>
            <phase>prepare-package</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>

```



