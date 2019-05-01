#!/usr/bin/env groovy

// Assume that the zip artefact has the same name as the Jenkins job
String target_file = "target/scala-2.11/${env.JOB_BASE_NAME}.zip"

pipeline {
    agent any

    stages {
        stage('Build artefact') {
            agent {
                dockerfile {
                    // Cache sbt dependencies
                    args "-v /tmp/.sbt:/root/.sbt -u root:root"
                }
            }
            steps {
                sh(script: "sbt assembly")
                stash(
                    name: 'artefact',
                    includes: target_file
                )
            }
        }
        stage('Generate sha256') {
            steps {
                unstash(name: 'artefact')
                sh("openssl dgst -sha256 -binary ${target_file} | openssl enc -base64 > ${env.JOB_BASE_NAME}.zip.base64sha256")
            }
        }
        stage('Upload to s3') {
            steps {
                script {
                    git_id = sh(returnStdout: true, script: 'git describe --always').trim()
                }
                sh(
                    """
                    aws s3 cp ${target_file} \
                        s3://mdtp-lambda-functions-integration/${env.JOB_BASE_NAME}/${env.JOB_BASE_NAME}_${git_id}.zip \
                        --acl=bucket-owner-full-control --only-show-errors
                    aws s3 cp ${env.JOB_BASE_NAME}.zip.base64sha256 \
                        s3://mdtp-lambda-functions-integration/${env.JOB_BASE_NAME}/${env.JOB_BASE_NAME}_${git_id}.zip.base64sha256 \
                        --content-type text/plain --acl=bucket-owner-full-control --only-show-errors
                    """
                )
            }
        }
        stage('Deploy to Integration') {
            steps {
                build(
                    job: 'api-platform-admin-api/deploy_lambda',
                    parameters: [
                        [$class: 'StringParameterValue', name: 'ARTEFACT', value: env.JOB_BASE_NAME],
                        [$class: 'StringParameterValue', name: 'VERSION', value: git_id],
                        [$class: 'BooleanParameterValue', name: 'DEPLOY_INTEGRATION', value: true]
                    ]
                )
            }
        }
    }
}
