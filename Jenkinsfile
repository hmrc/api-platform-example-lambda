#!/usr/bin/env groovy

def account_info = [
        integration: ["account_id": "150648916438"],
//        management  : ["account_id": "419929493928"],
//        sandbox     : ["account_id": "370807233099"],
//        staging     : ["account_id": "186795391298"],
//        qa          : ["account_id": "248771275994"],
//        externaltest: ["account_id": "970278273631"],
//        production  : ["account_id": "490818658393"],
//        development : ["account_id": "618259438944"]
]

String target_file = "target/scala-2.11/${env.JOB_BASE_NAME}.zip"
String lambda_function_name = "LAMBDA_FUNCTION_NAME"

pipeline {
    agent any

    stages {
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
                sh(
                    """
                    for env in integration; do
                        aws s3 cp ${target_file} s3://mdtp-lambda-functions-\${env}/ --acl=bucket-owner-full-control --only-show-errors
                        aws s3 cp ${env.JOB_BASE_NAME}.zip.base64sha256 s3://mdtp-lambda-functions-\${env}/ --content-type text/plain --acl=bucket-owner-full-control --only-show-errors
                    done
                    """
                )
            }
        }
        stage('Deploy lambda') {
            when {
                // Only deploy if job param has been ticked ("true")
                equals expected: "true", actual: DEPLOY
                // Don't deploy if this is the example lambda
                not { equals expected: "api-platform-example-lambda", actual: env.JOB_BASE_NAME }
            }
            steps {
                sh(
                        """     
                    SESSIONID=\$(date +"%s")
                    AWS_CREDENTIALS=\$(aws sts assume-role --role-arn arn:aws:iam::${account_info['integration']['account_id']}:role/RoleJenkinsInfraBuild --role-session-name \$SESSIONID --query '[Credentials.AccessKeyId,Credentials.SecretAccessKey,Credentials.SessionToken]' --output text)
                    export AWS_ACCESS_KEY_ID=\$(echo \$AWS_CREDENTIALS | awk '{print \$1}')
                    export AWS_SECRET_ACCESS_KEY=\$(echo \$AWS_CREDENTIALS | awk '{print \$2}')
                    export AWS_SESSION_TOKEN=\$(echo \$AWS_CREDENTIALS | awk '{print \$3}')
                    aws lambda update-function-code --function-name ${lambda_function_name} --s3-bucket mdtp-lambda-functions-integration --s3-key ${env.JOB_BASE_NAME}.zip
                    """
                )
            }
        }
    }
}
