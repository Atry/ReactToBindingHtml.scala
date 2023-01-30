enablePlugins(ScalaJSPlugin)
enablePlugins(ScalaJSBundlerPlugin)
enablePlugins(Example)

ThisBuild / organization := "com.yang-bo"

name := "ReactBinding"

libraryDependencies += {
  if (VersionNumber(scalaJSVersion).matchesSemVer(SemanticSelector(">=1"))) {
    "me.shadaj" %%% "slinky-core" % "0.7.3"
  } else {
    "me.shadaj" %%% "slinky-core" % "0.6.8"
  }
}

libraryDependencies += {
  if (
    VersionNumber(scalaVersion.value).matchesSemVer(SemanticSelector(">=2.13"))
  ) {
    "com.thoughtworks.binding" %%% "binding" % "12.2.0"
  } else {
    "com.thoughtworks.binding" %%% "binding" % "11.9.0"
  }
}

libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.15" % Test

libraryDependencies += {
  if (VersionNumber(scalaJSVersion).matchesSemVer(SemanticSelector(">=1"))) {
    "me.shadaj" %%% "slinky-testrenderer" % "0.7.3" % Test
  } else {
    "me.shadaj" %%% "slinky-testrenderer" % "0.6.8" % Test
  }
}

libraryDependencies += {
  if (VersionNumber(scalaJSVersion).matchesSemVer(SemanticSelector(">=1"))) {
    "me.shadaj" %%% "slinky-web" % "0.7.3" % Test
  } else {
    "me.shadaj" %%% "slinky-web" % "0.6.8" % Test
  }
}

webpack / version := "5.75.0"

Test / requireJsDomEnv := true

Test / npmDependencies += "react-dom" -> "18.2.0"

Test / npmDependencies += "react-test-renderer" -> "18.2.0"
