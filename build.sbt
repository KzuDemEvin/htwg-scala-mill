import sbt.Keys.libraryDependencies
// ROOT

name := "htwg-scala"
organization := "de.htwg.se"
version := "0.13"
scalaVersion := "2.13.2"

val commonDependencies = Seq(
  "org.scalatest" %% "scalatest" % "3.0.8",
  "org.scalatest" %% "scalatest" % "3.0.8" % "test",
  "org.scala-lang.modules" %% "scala-swing" % "2.1.1",
  "net.codingwell" %% "scala-guice" % "4.2.6",
  "com.typesafe.play" %% "play-json" % "2.8.1",
  "org.scala-lang.modules" %% "scala-xml" % "1.2.0"
)

parallelExecution in Test := false

ThisBuild / trackInternalDependencies := TrackLevel.TrackIfMissing

lazy val fileIO = (project in file("FileIO"))
lazy val roundManager = (project in file("RoundManager"))
lazy val player = (project in file("Player"))
lazy val root = (project in file(".")).dependsOn(fileIO, roundManager, player).aggregate(fileIO, roundManager, player).settings(
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