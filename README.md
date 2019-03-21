# API Platform Example Lambda

This is an example of an AWS Lambda written in Scala.

## Build & Packaging

The project uses [sbt-assembly](https://github.com/sbt/sbt-assembly) to package compiled code into a ZIP file for upload to AWS. The command to run is `sbt assembly`. Additionally, the script `run_all_tests.sh` will run all tests associated with the project.

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
