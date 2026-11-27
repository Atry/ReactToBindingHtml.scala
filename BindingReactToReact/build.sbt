enablePlugins(ScalaJSPlugin)

enablePlugins(ScalaJSBundlerPlugin)

enablePlugins(Example)

libraryDependencies += "me.shadaj" %%% "slinky-core" % "0.7.5"

libraryDependencies += {
  if (
    VersionNumber(scalaVersion.value).matchesSemVer(SemanticSelector(">=2.13"))
  ) {
    "com.thoughtworks.binding" %%% "binding" % "12.2.0"
  } else {
    "com.thoughtworks.binding" %%% "binding" % "11.9.0"
  }
}

libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.20" % Test

libraryDependencies += "me.shadaj" %%% "slinky-testrenderer" % "0.7.5" % Test

libraryDependencies += "me.shadaj" %%% "slinky-web" % "0.7.5" % Test

Test / requireJsDomEnv := true

Test / npmDependencies += "react-dom" -> "18.2.0"

Test / npmDependencies += "react-test-renderer" -> "18.2.0"
