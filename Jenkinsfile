#!/usr/bin/env groovy

String target_file = "target/scala-2.11/${env.JOB_BASE_NAME}.zip"

pipeline {
    agent any

    stages {
        stage('Prepare') {
            steps {
                checkout(scm)
            }
        }
        stage('Build artefact') {
            steps {
                sh('docker run -t -v $(pwd):/data amazonlinux /bin/bash -c "cd /data; ./package.sh"')
            }
        }
        stage('Generate sha256') {
            steps {
                sh("openssl dgst -sha256 -binary ${target_file} | openssl enc -base64 > ${env.JOB_BASE_NAME}.zip.base64sha256")
            }
        }
        stage('Upload to s3') {
            steps {
                // sh("""for env in sandbox development qa staging integration externaltest production; do
                sh("""for env in integration; do
                aws s3 cp ${target_file} s3://mdtp-lambda-functions-\${env}/ --acl=bucket-owner-full-control --only-show-errors
                aws s3 cp ${env.JOB_BASE_NAME}.zip.base64sha256 s3://mdtp-lambda-functions-\${env}/ --content-type text/plain --acl=bucket-owner-full-control --only-show-errors
              done
          """)
            }
        }
    }
}
