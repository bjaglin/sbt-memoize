sbtPlugin := true
pluginCrossBuild / sbtVersion := "1.2.8" // cf https://github.com/sbt/sbt/issues/5049#issuecomment-538404839
scalaVersion := "2.12.11"

organization := "com.github.bjaglin"
name := "sbt-memoize"
homepage := Some(url("https://github.com/bjaglin/sbt-memoize"))
licenses := List("MIT" -> url("https://opensource.org/licenses/MIT"))
developers := List(
  Developer(
    "bjaglin",
    "Brice Jaglin",
    "bjaglin@gmail.com",
    url("https://github.com/bjaglin")
  )
)

enablePlugins(SbtPlugin)
scriptedLaunchOpts += "-Dplugin.version=" + version.value
scriptedBufferLog := false

semanticdbEnabled := true
semanticdbVersion := scalafixSemanticdb.revision
scalafixCaching := true
libraryDependencies += "com.github.liancheng" %% "organize-imports" % "0.4.0" % ScalafixConfig
