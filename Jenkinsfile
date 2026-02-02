pipeline {
  agent any
  tools {
    jdk 'JDK11'
    maven 'Maven3'
  }
  stages {
    stage('Checkout') {
      steps { checkout scm }
    }
    stage('Build & Test') {
      steps {
        sh 'mvn -q clean test'
      }
    }
  }
  post {
    always {
      archiveArtifacts artifacts: 'target/extent-report/**, target/logs/**, target/screenshots/**', fingerprint: true
      junit allowEmptyResults: true, testResults: 'target/surefire-reports/testng-results.xml'
    }
  }
}
