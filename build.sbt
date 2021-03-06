import sbt.Keys.libraryDependencies
// ROOT

name := "htwg-scala"
organization := "de.htwg.se"
version := "0.13"
scalaVersion := "2.13.2"

val commonDependencies = Seq(
  "org.scalactic" %% "scalactic" % "3.1.2",
  "org.scalatest" %% "scalatest" % "3.1.2" % "test",
  "org.scala-lang.modules" %% "scala-swing" % "2.1.1",
  "com.google.inject" % "guice" % "4.2.3",
  "net.codingwell" %% "scala-guice" % "4.2.10",
  "org.scala-lang.modules" %% "scala-xml" % "1.2.0",
  "com.typesafe.play" %% "play-json" % "2.9.0",
  "com.typesafe.akka" %% "akka-actor-typed" % "2.6.8",
  "com.typesafe.akka" %% "akka-stream" % "2.6.8",
  "com.typesafe.akka" %% "akka-http" % "10.2.4",
  "com.google.code.gson" % "gson" % "2.8.6"
)

fork in run := true
parallelExecution in Test := false
coverageExcludedPackages := "<empty>;.*aview.*;.*Mill"
coverageEnabled.in(Test, test) := true
javaOptions += "-Dscala.concurrent.context.maxThreads=2"

ThisBuild / trackInternalDependencies := TrackLevel.TrackIfMissing

lazy val roundManager = project in file("RoundManager")
lazy val player = project in file("Player")
lazy val fileIO = project in file("FileIO")

lazy val root =  (project in file(".")).dependsOn(player, fileIO, roundManager).aggregate(player, fileIO, roundManager).settings(
  name := "Mill",
  libraryDependencies ++= commonDependencies,
  assemblyMergeStrategy in assembly := {
    case PathList("reference.conf") => MergeStrategy.concat
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case x => MergeStrategy.first
  },
  assemblyJarName in assembly := "Mill.jar",
  mainClass in assembly := Some("de.htwg.se.mill.Mill")
).settings(dockerBaseImage := "hseeberger/scala-sbt:8u222_1.3.5_2.13.1")
  .settings(daemonUser in Docker := "sbtuser")
  .settings(dockerExposedPorts := Seq(8080))
  .settings(mainClass in Compile := Some("de.htwg.se.mill.Mill"))
  .aggregate(player, fileIO, roundManager)
  .dependsOn(player,fileIO, roundManager)
  .enablePlugins(sbtdocker.DockerPlugin, JavaAppPackaging)