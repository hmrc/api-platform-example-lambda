lazy val appName = "api-platform-example-lambda"
lazy val appDependencies: Seq[ModuleID] = compileDependencies ++ testDependencies

lazy val compileDependencies = Seq(
  "io.github.mkotsur" %% "aws-lambda-scala" % "0.1.1"
)

lazy val testScope: String = "test"

lazy val testDependencies = Seq(
  "org.scalatest" %% "scalatest" % "3.0.5" % testScope
)

lazy val plugins: Seq[Plugins] = Seq()

lazy val lambda = (project in file("."))
  .enablePlugins(plugins: _*)
  .settings(
    name := appName,
    scalaVersion := "2.11.11",
    libraryDependencies ++= appDependencies,
    parallelExecution in Test := false,
    fork in Test := false,
    retrieveManaged := true
  )
  .settings(
    resolvers += Resolver.bintrayRepo("hmrc", "releases"),
    resolvers += Resolver.jcenterRepo
  )
  .settings(
    assemblyJarName in assembly := s"$appName.zip"
  )


