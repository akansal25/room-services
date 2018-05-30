node () {

//Set parameters:
//set build Number
//    BUILD_NUMBER = "${env.BUILD_ID}"
//// set your registry url  variable
//    registry_url = "462836960916.dkr.ecr.us-east-1.amazonaws.com/${container_name}" // ECR Docker Registry
//
//    def aws_mobilogy_ecs = "ebeaae28-5627-4335-a748-e7e14e677395"
//    def scm_creds_id = "f7205fe8-59a5-439c-a47a-685261c29289"


//Git Checkout
    stage 'check-out-code-from-gitLab'
    checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: scm_creds_id, url: '${GIT_URL}']]])

//Build Container locally
    stage "Building ${container_name} Container for Core-Services"

    echo "this is the build number: ${BUILD_NUMBER}"
    echo "Building ${container_name} with docker.build(${registry_url}:${env.BUILD_ID})"
    // add more tests
    sh "docker build -t ${container_name}:${BUILD_NUMBER} ."
    // Set up the container to build


    currentBuild.result = 'SUCCESS'

//Tag the image built from previuos stage
    stage 'tag the image'

    echo "the registry url is : ${registry_url}"
    sh "docker tag ${container_name}:${BUILD_NUMBER}  ${registry_url}:${BUILD_NUMBER}"


//Push the built image from previuos stage to ECR
    stage 'push the image to ecr'
    withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', accessKeyVariable: 'AWS_ACCESS_KEY_ID', credentialsId: aws_mobilogy_ecs, secretKeyVariable: 'AWS_SECRET_ACCESS_KEY']]) {
        sh     'eval `aws ecr get-login --region us-east-1 --no-include-email`'
        sh     "docker push ${registry_url}:${BUILD_NUMBER}"
    }
    currentBuild.result = 'SUCCESS'


//Cleanup local images on Jenkins
    stage 'cleanup Local Images'

    // Remove local Docker images
    sh "docker rmi ${registry_url}:${BUILD_NUMBER} && docker rmi ${container_name}:${BUILD_NUMBER}"


}