ThisBuild / organization := "com.yang-bo.reactbinding"

lazy val HtmlMountPoint = project

lazy val BindingReactToReact = project

lazy val BindingHtmlToReact = project.dependsOn(HtmlMountPoint)

lazy val ReactToBindingHtml = project

publish / skip := true

enablePlugins(ScalaUnidocPlugin)
