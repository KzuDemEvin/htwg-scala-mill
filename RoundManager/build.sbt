// RoundManager

enablePlugins(JavaAppPackaging)

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
  "com.google.code.gson" % "gson" % "2.8.6",
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "org.slf4j" % "slf4j-nop" % "1.7.30" % Test,
  "mysql" % "mysql-connector-java" % "8.0.24"
)

lazy val roundManager = (project in file(".")).settings(
  name          := "htwg-scala-mill-roundmanager",
  organization  := "de.htwg.se",
  version       := "0.13",
  scalaVersion  := "2.13.2",
  libraryDependencies ++= commonDependencies
)
  .enablePlugins(sbtdocker.DockerPlugin, JavaAppPackaging)
  .settings(dockerBaseImage := "hseeberger/scala-sbt:8u222_1.3.5_2.13.1")
  .settings(daemonUser in Docker := "sbtuser")
  .settings(mainClass in Compile := Some("de.htwg.se.mill.RoundManager"))
  .settings(dockerExposedPorts := Seq(8083))
