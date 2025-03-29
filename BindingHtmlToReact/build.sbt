enablePlugins(ScalaJSPlugin)

enablePlugins(ScalaJSBundlerPlugin)

enablePlugins(Example)

libraryDependencies += {
  if (VersionNumber(scalaJSVersion).matchesSemVer(SemanticSelector(">=1"))) {
    "me.shadaj" %%% "slinky-web" % "0.7.3"
  } else {
    "me.shadaj" %%% "slinky-web" % "0.6.8"
  }
}

libraryDependencies += {
  if (
    VersionNumber(scalaVersion.value).matchesSemVer(SemanticSelector(">=2.13"))
  ) {
    "com.thoughtworks.binding" %%% "bindable" % "3.0.0"
  } else {
    "com.thoughtworks.binding" %%% "bindable" % "1.1.0"
  }
}

libraryDependencies += {
  if (
    VersionNumber(scalaVersion.value).matchesSemVer(SemanticSelector(">=3"))
  ) {
    "com.yang-bo" %%% "html" % "3.0.2" % Optional
  } else {
    "com.yang-bo" %%% "html" % "2.0.2" % Optional
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

Test / requireJsDomEnv := true

Test / npmDependencies += "react-dom" -> "18.2.0"

Test / npmDependencies += "react-test-renderer" -> "18.2.0"

scalacOptions ++= PartialFunction.condOpt(
  VersionNumber(scalaJSVersion).matchesSemVer(SemanticSelector("<1"))
) { case true =>
  "-P:scalajs:sjsDefinedByDefault"
}

scalacOptions ++= PartialFunction.condOpt(
  scalaBinaryVersion.value
) { case "2.13" =>
  "-Ymacro-annotations"
}

libraryDependencies ++= PartialFunction.condOpt(
  scalaBinaryVersion.value
) { case "2.12" =>
  compilerPlugin(
    "org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full
  )
}

webpack / version := {
  if (VersionNumber(scalaJSVersion).matchesSemVer(SemanticSelector("<1"))) {
    "3.12.0"
  } else {
    "5.98.0"
  }
}