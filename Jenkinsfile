pipeline {
    agent any
    
    tools {
        maven 'Maven'
        jdk 'JDK11'
    }
    
    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timeout(time: 30, unit: 'MINUTES')
        timestamps()
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
                echo 'Code checked out successfully'
            }
        }
        
        stage('Build') {
            steps {
                echo 'Building the project...'
                bat 'mvn clean compile'
            }
        }
        
        stage('Test') {
            steps {
                echo 'Running tests...'
                bat 'mvn test'
            }
            post {
                always {
                    // Publish TestNG reports
                    publishTestNGResults(
                        testResultsPattern: 'test-output/testng-results.xml'
                    )
                    
                    // Archive test reports
                    archiveArtifacts artifacts: 'test-output/**/*, reports/**/*', 
                                    allowEmptyArchive: true
                    
                    // Publish HTML reports
                    publishHTML([
                        reportDir: 'test-output',
                        reportFiles: 'index.html',
                        reportName: 'TestNG Report',
                        keepAll: true
                    ])
                    
                    publishHTML([
                        reportDir: 'reports',
                        reportFiles: 'ExtentReport.html',
                        reportName: 'Extent Report',
                        keepAll: true
                    ])
                }
            }
        }
    }
    
    post {
        always {
            echo 'Pipeline execution completed'
            cleanWs()
        }
        success {
            echo 'Pipeline succeeded!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}

