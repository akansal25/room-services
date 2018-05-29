node(){

    stage "Build Room Services"
        echo "clean and build room services"
        sh "gradle clean build"
        echo "room services build completed ${env.BUILD_ID}"

    stage "Building ${container_name} Container for Core-Services"

        echo "Building ${container_name} with docker.build(${registry_url}"
        // add more tests
        sh "gradle buildDocker"
        // Set up the container to build

        echo "docker image successfully created"

//Tag the image built from previuos stage
}