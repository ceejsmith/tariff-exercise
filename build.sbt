name := "tariff-exercise"
scalaVersion := "2.11.8"

val tariff = project
  .in(file("."))
  .settings(libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % Test)