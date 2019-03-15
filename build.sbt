lazy val appName = "api-platform-example-lambda"
lazy val appDependencies: Seq[ModuleID] = compile ++ test

lazy val compile = Seq(
  "io.github.mkotsur" %% "aws-lambda-scala" % "0.1.1"
)

lazy val scope: String = "test,it"

lazy val test = Seq(
  "org.scalatest" %% "scalatest" % "3.0.5"
)

lazy val plugins: Seq[Plugins] = Seq()

lazy val microservice = (project in file("."))
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
