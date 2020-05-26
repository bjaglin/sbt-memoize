lazy val `parent-memoized` = project
  .enablePlugins(MemoizePlugin)

lazy val `parent` = project

lazy val `child-memoized` = project
  .enablePlugins(MemoizePlugin)
  .dependsOn(`parent-memoized`, `parent`)

lazy val `child` = project
  .dependsOn(`parent-memoized`, `parent`)
