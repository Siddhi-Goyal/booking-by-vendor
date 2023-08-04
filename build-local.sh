# this script assumes you store your Artifactory username and password in your shell environment
# This document will help you find your Artifactory token
# https://gapinc.atlassian.net/wiki/spaces/CICD/pages/936094870/How+to+update+gradle+and+npm+to+use+Artifactory+with+authentication
#
#docker build . \
#    -t smee-user-service:local \
#    -f Dockerfile \
#    # --secret id=docker_env,src="sensitive_vars" \ # use this if you need to pass in secrets: KEY=VALUE
#    --progress plain \
#    --build-arg SERVICE_NAME="smee-user-service" \
#    --build-arg ARTIFACTORY_USERNAME=${ARTIFACTORY_USERNAME} \
#    --build-arg ARTIFACTORY_PASSWORD=${ARTIFACTORY_PASSWORD} \
#    --build-arg ARTIFACTORY_REPO="maven-repos" \
#    --build-arg IMAGE_MAINTAINER="me" \
#    --build-arg IMAGE_DATE=$(date +%m/%d/%Y)
# this script assumes you store your Artifactory username and password in your shell environment
# This document will help you find your Artifactory token
# https://gapinc.atlassian.net/wiki/spaces/CICD/pages/936094870/How+to+update+gradle+and+npm+to+use+Artifactory+with+authentication


docker build -t smee-user-service:latest -f Dockerfile --build-arg ARTIFACTORY_USERNAME=${ARTIFACTORY_USERNAME} --build-arg ARTIFACTORY_PASSWORD=${ARTIFACTORY_PASSWORD} --build-arg JASYPT_ENCRYPTOR_PASSWORD=${JASYPT_ENCRYPTOR_PASSWORD} --build-arg IMAGE_DATE=$(date +%m/%d/%Y) --build-arg SERVICE_NAME="smee-user-service" --build-arg APPLICATION_VERSION="0.0.1" --build-arg PROFILE="default"
