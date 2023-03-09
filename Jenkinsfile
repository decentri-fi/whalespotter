pipeline {
    agent any
    stages {
        stage('Package') {
             steps {
                 echo "-=- packaging project -=-"
                 sh "./mvnw clean package"
             }
        }
        stage('Docker Package') {
            when {
                allOf {
                    branch 'main'
                }
            }
            steps {
                sh "bash ci/package.sh"
            }
        }
    }
}