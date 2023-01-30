enablePlugins(ScalaJSPlugin)

ThisBuild / organization := "com.yang-bo"

libraryDependencies += {
  if (VersionNumber(scalaJSVersion).matchesSemVer(SemanticSelector(">=1"))) {
     "me.shadaj" %%% "slinky-core" % "0.7.3"
  } else {
     "me.shadaj" %%% "slinky-core" % "0.6.8"
  }
}

libraryDependencies += {
  if (
    VersionNumber(scalaVersion.value).matchesSemVer(SemanticSelector(">=2.12"))
  ) {
    "com.thoughtworks.binding" %%% "binding" % "12.2.0"
  } else {
    "com.thoughtworks.binding" %%% "binding" % "11.9.0"
  }
}
