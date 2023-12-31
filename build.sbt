ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.2.2"

libraryDependencies += "io.cequence" %% "openai-scala-client" % "1.0.0.RC.1"
libraryDependencies += "io.cequence" %% "openai-scala-client-stream" % "1.0.0.RC.1"
libraryDependencies ++= Seq("org.slf4j" % "slf4j-api" % "2.0.5", "org.slf4j" % "slf4j-simple" % "2.0.5")
libraryDependencies += "com.typesafe.akka" % "akka-actor-typed_2.13" % "2.6.20"
libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0"
libraryDependencies += "com.typesafe" % "config" % "1.4.2"
libraryDependencies += "com.github.sbt" % "junit-interface" % "0.13.3" % Test

lazy val root = (project in file("."))
  .settings(
    name := "LLMTestApp"
  )
