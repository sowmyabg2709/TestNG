pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Build the project'
            }
        }
        stage('Deploy on Dev') {
            steps {
                echo 'Dev deployt'
            }
        }
        stage('Deploy on QA') {
            steps {
                echo 'QA deploy'
            }
        }
        
        stage('Smoke test') {
            steps {
                echo 'Smoke test'
            }
        }
        
          stage('Regression test') {
            steps {
                echo 'Regression test'
            }
        }
        
        stage('Deploy on stage') {
            steps {
                echo 'Stage Deploy'
            }
        }
        
        stage('Deploy on prod') {
            steps {
                echo 'Prod deploy'
            }
        }
    }
}
