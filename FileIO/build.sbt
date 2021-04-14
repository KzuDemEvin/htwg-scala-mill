// FileIO

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
  "com.typesafe.akka" %% "akka-http" % "10.2.4"
)

lazy val root = (project in file(".."))
lazy val player = (project in file("../Player"))
lazy val fileIO = (project in file(".")).dependsOn(root, player).aggregate(root, player).settings(
  name          := "htwg-scala-mill-fileio",
  //organization  := "de.htwg.se",
  version       := "0.13",
  scalaVersion  := "2.13.2",
  libraryDependencies ++= commonDependencies
)