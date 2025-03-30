enablePlugins(ScalaJSPlugin)

libraryDependencies += {
  if (
    VersionNumber(scalaVersion.value).matchesSemVer(SemanticSelector(">=2.13"))
  ) {
    "com.thoughtworks.binding" %%% "binding" % "12.2.0"
  } else {
    "com.thoughtworks.binding" %%% "binding" % "11.9.0"
  }
}
libraryDependencies += {
  if (VersionNumber(scalaJSVersion).matchesSemVer(SemanticSelector(">=1"))) {
    "org.scala-js" %%% "scalajs-dom" % "2.8.0"
  } else {
    "org.scala-js" %%% "scalajs-dom" % "1.2.0"
  }
}

webpack / version := {
  if (VersionNumber(scalaJSVersion).matchesSemVer(SemanticSelector("<1"))) {
    "3.12.0"
  } else {
    "5.98.0"
  }
}
