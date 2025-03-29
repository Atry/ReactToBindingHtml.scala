ThisBuild / organization := "com.yang-bo"

lazy val HtmlMountPoint = project

lazy val BindingReactToReact = project

lazy val BindingHtmlToReact = project.dependsOn(HtmlMountPoint)

lazy val ReactToBindingHtml = project.dependsOn(BindingReactToReact % Test)

publish / skip := true

enablePlugins(ScalaUnidocPlugin)
