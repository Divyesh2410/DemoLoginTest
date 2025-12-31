pipeline {
    agent any
    
    tools {
        maven 'Maven'
        jdk 'JDK11'
    }
    
    parameters {
        choice(
            name: 'HEADLESS',
            choices: ['true', 'false'],
            description: 'Run tests in headless mode (true = no visible browser, false = visible browser)'
        )
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
            environment {
                HEADLESS = "${params.HEADLESS}"
            }
            steps {
                script {
                    def headlessMode = params.HEADLESS ?: 'false'
                    echo "Running tests with HEADLESS=${headlessMode}"
                    echo "Browser visibility: ${headlessMode == 'true' ? 'HIDDEN (headless)' : 'VISIBLE'}"
                }
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

