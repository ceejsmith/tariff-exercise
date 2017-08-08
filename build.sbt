name := "tariff-exercise"
scalaVersion := "2.11.8"

val tariff = project
  .in(file("."))
  .settings(libraryDependencies ++= Seq(
    "org.json4s" %% "json4s-native" % "3.3.0",
    "org.scalatest" %% "scalatest" % "3.0.0" % Test
  ))