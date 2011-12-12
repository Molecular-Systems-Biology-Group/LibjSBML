import sbt._

class RestProject(info: ProjectInfo) extends DefaultWebProject(info) {

    // Add Maven Local repository for SBT to search for (disable if this doesn't suit you)
      val mavenLocal = "Local Maven Repository" at "file://"+Path.userHome+"/.m2/repository"

      val mavenRepo = "Maven Central Repository 1" at "http://repo1.maven.org/maven2"

      val projectRepo = "WikiModels Repository" at "file://../localMavenRepo"

      val bryanjswift = "Bryan J Swift Repository" at "http://repos.bryanjswift.com/maven2/"

      val junitInterface = "com.novocode" % "junit-interface" % "0.4.0" % "test"

      val jenabeanRepo = "Jenabean" at "http://jenabean.googlecode.com/svn/trunk/repo"

      val jenabean = "thewebsemantic" % "jenabean" % "1.0.7" 

      val jersey = "com.sun.jersey" % "jersey-server" % "1.1.5.1"

      val javanet2Repo = "maven2-repository.dev.java.net" at "http://download.java.net/maven/2"

      val javanet1Repo = "maven-repository.dev.java.net" at "http://download.java.net/maven/1"

      val javax = "javax.resource" % "connector-api" % "1.5" % "provided"

      val scalaRepo = "scala-tools.org" % "http://scala-tools.org/repo-releases" 

      val usefullScalaStuff = "UsefullScalaStuff" % "UsefullScalaStuff" % "0.1" 
      
      val slf4jApi = "org.slf4j" % "slf4j-api" % "1.6.1" 
      
      val logbackclassic = "ch.qos.logback" % "logback-classic" % "0.9.29"

      //val log4j = "log4j" % "log4j" % "1.2.16"

      //val libsbml = "org.sbml" % "libsbmmlj" % "3.4"

      val liftVersion = "2.4-M5"

      val liftWidget = "net.liftweb" %% "lift-common" % liftVersion % "test"

    }
