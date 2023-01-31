ThisBuild / organization := "com.yang-bo.reactbinding"

lazy val NodeSeqMountPoint = project

lazy val BindingReactToReact = project

lazy val BindingToReact = project.dependsOn(NodeSeqMountPoint)

lazy val ReactToBinding = project

publish / skip := true

enablePlugins(ScalaUnidocPlugin)
