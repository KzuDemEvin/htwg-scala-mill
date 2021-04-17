import sbtdocker.DockerKeys._

enablePlugins(JavaAppPackaging)

// ROOT

name := "htwg-scala"
organization := "de.htwg.se"
version := "0.13"
scalaVersion := "2.13.2"

lazy val global = project.in(file(".")).settings(libraryDependencies ++= commonDependencies)
  .settings(mainClass in Compile := Some("de.htwg.se.mill.Mill"))
  .settings(dockerBaseImage := "hseeberger/scala-sbt:8u222_1.3.5_2.13.1")
  .settings(dockerExposedPorts := Seq(8080))
  .settings(daemonUser := "sbtuser")
  .aggregate(player, fileIO)
  .dependsOn(player,fileIO)
  .enablePlugins(sbtdocker.DockerPlugin)

lazy val player = project.in(file("Player")).settings(libraryDependencies ++= commonDependencies)
  .enablePlugins(sbtdocker.DockerPlugin, JavaAppPackaging)
  .settings(dockerBaseImage := "hseeberger/scala-sbt:8u222_1.3.5_2.13.1")
  .settings(dockerExposedPorts := Seq(8081))
  .settings(daemonUser := "sbtuser")

lazy val fileIO =  project.in(file("FileIO")).settings(libraryDependencies ++= commonDependencies)
  .enablePlugins(sbtdocker.DockerPlugin)
  .settings(dockerBaseImage := "hseeberger/scala-sbt:8u222_1.3.5_2.13.1")
  .settings(dockerExposedPorts := Seq(8082))
  .settings(daemonUser := "sbtuser")

lazy val dependencies =
  new {
    val akka = "com.typesafe.akka" %% "akka-http" % "10.2.4"
    val akkaactor = "com.typesafe.akka" %% "akka-actor-typed" % "2.6.8"
    val akkastream = "com.typesafe.akka" %% "akka-stream" % "2.6.8"
    val scalatest = "org.scalatest" %% "scalatest" % "3.1.2" % "test"
    val scalactic = "org.scalactic" %% "scalactic" % "3.1.2"
    val scalaswing = "org.scala-lang.modules" %% "scala-swing" % "2.1.1"
    val guice = "com.google.inject" % "guice" % "4.2.3"
    val scalaguice = "net.codingwell" %% "scala-guice" % "4.2.10"
    val playjson = "com.typesafe.play" %% "play-json" % "2.9.0"
    val scalaxml = "org.scala-lang.modules" %% "scala-xml" % "1.2.0"
    val gson = "com.google.code.gson" % "gson" % "2.8.6"
  }

val commonDependencies =  Seq(
  dependencies.akka,
  dependencies.scalatest,
  dependencies.scalactic,
  dependencies.scalaswing,
  dependencies.guice,
  dependencies.scalaguice,
  dependencies.playjson,
  dependencies.scalaxml,
  dependencies.akkaactor,
  dependencies.akkastream,
  dependencies.gson
)

parallelExecution in Test := false

ThisBuild / trackInternalDependencies := TrackLevel.TrackIfMissing



lazy val root = (project in file(".")).dependsOn(fileIO, player).aggregate(fileIO, player).settings(
  name := "htwg-scala-mill",
  libraryDependencies ++= commonDependencies,
  assemblyMergeStrategy in assembly := {
    case PathList("reference.conf") => MergeStrategy.concat
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case x => MergeStrategy.first
  },
  assemblyJarName in assembly := "Mill.jar",
  mainClass in assembly := Some("de.htwg.se.mill.Mill")
)