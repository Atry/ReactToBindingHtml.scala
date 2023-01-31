ThisBuild / organization := "com.yang-bo.reactbinding"

lazy val DomMountPoint = project

lazy val BindingReactToReact = project

lazy val BindingToReact = project.dependsOn(DomMountPoint)

lazy val ReactToBinding = project

publish / skip := true

enablePlugins(ScalaUnidocPlugin)
