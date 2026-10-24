pipeline {
    agent any

    parameters {
        choice(name: 'ENV', choices: ['qa', 'staging', 'prod'], description: 'Environment to run tests against')
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'chrome-headless'], description: 'Browser to run tests on')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/yourorg/OmniBank-BDD-Automation.git'
            }
        }

        stage('Run Cucumber Suite') {
            steps {
                sh "mvn clean test -Denv=${params.ENV} -Dbrowser=${params.BROWSER}"
            }
        }

        stage('Rerun Failed Scenarios') {
            when {
                expression { fileExists('reports/failed_scenarios.txt') }
            }
            steps {
                sh "mvn test -Dtest=RerunFailedTests -Denv=${params.ENV} -Dbrowser=${params.BROWSER}"
            }
        }

        stage('Publish Reports') {
            steps {
                publishHTML(target: [
                    reportDir: 'reports',
                    reportFiles: 'CucumberExtentReport.html',
                    reportName: 'Cucumber Extent Report'
                ])
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'screenshots/**, logs/**, reports/**', allowEmptyArchive: true
        }
    }
}
