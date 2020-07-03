name          := "htwg-scala-mill"
//organization  := "de.htwg.se"
version       := "0.11"
scalaVersion  := "2.13.2"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.1.2"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.2" % "test"

//*******************************************************************************//
//Libraries that we will use in later lectures compatible with this scala version
// uncomment to use!!

libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "2.1.1"

libraryDependencies += "com.google.inject" % "guice" % "4.2.3"

libraryDependencies += "net.codingwell" %% "scala-guice" % "4.2.10"

libraryDependencies += "org.scala-lang.modules" % "scala-xml" % "1.2.0"

//libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.6"
