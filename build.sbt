lazy val root = (project in file(".")).aggregate(fileio, player)
lazy val fileio = (project in file("fileIo"))
lazy val player = (project in file("player"))

name          := "htwg-scala-mill"
//organization  := "de.htwg.se"
version       := "0.13"
scalaVersion  := "2.13.2"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.1.2"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.2" % "test"

libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "2.1.1"

libraryDependencies += "com.google.inject" % "guice" % "4.2.3"

libraryDependencies += "net.codingwell" %% "scala-guice" % "4.2.10"

libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.2.0"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.9.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % "2.6.8",
  "com.typesafe.akka" %% "akka-stream" % "2.6.8",
  "com.typesafe.akka" %% "akka-http" % "10.2.4"
)
