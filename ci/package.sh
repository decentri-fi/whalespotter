docker build -t defitrack/whalespotter-runner:${BRANCH_NAME} whalespotter-runner -f ci/Dockerfile
docker build -t defitrack/whalespotter-web-${BRANCH_NAME} whalespotter-web -f ci/Dockerfile