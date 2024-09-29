DOCKER_BASE=192.168.0.130:1443/ckmro
REGISTRY_USER=admin
REGISTRY_PASSWORD=xxx
CONTAINER_IMAGE_RELEASE=xxx
REGISTRY_USER_RELEASE=admin
REGISTRY_PASSWORD_RELEASE=Harbor12345
VERSION=$(shell git rev-parse --short HEAD)
RELEASE_VERSION=$(shell git describe --abbrev=0 --tags)
HELM_RELEASE=giakt-develop
DEPLOYS=$(shell helm ls | grep $RELEASE_NAME | wc -l)
PROJECT_PATH=$(shell pwd)


dist-dev:
	gradle buildFatJar -i -S

dist-docker-dev:
	docker run --rm -u gradle -v $(PROJECT_PATH):/home/gradle/project -w /home/gradle/project gradle:8.4-jdk11-focal gradle buildFatJar -i -S

docker-dev:dist-dev
	docker build -f deploy/dev.Dockerfile -t $(DOCKER_BASE)/giakt_dev:${VERSION} .
	docker build -f deploy/dev.Dockerfile -t $(DOCKER_BASE)/giakt_dev:latest .
	docker login -u $(REGISTRY_USER) -p $(REGISTRY_PASSWORD) 192.168.0.130:1443
	docker push $(DOCKER_BASE)/giakt_dev:${VERSION}
	docker push $(DOCKER_BASE)/giakt_dev:latest

docker-test:dist-dev
	docker build -f deploy/test.Dockerfile -t $(DOCKER_BASE)/giakt_test:${RELEASE_VERSION} .
	docker build -f deploy/test.Dockerfile -t $(DOCKER_BASE)/giakt_test:latest .
	docker login -u $(REGISTRY_USER) -p $(REGISTRY_PASSWORD) 192.168.0.130:1443
	docker push $(DOCKER_BASE)/giakt_test:${RELEASE_VERSION}
	docker push $(DOCKER_BASE)/giakt_test:latest

docker-release:dist-dev
	docker build -f deploy/prod.Dockerfile -t $(CONTAINER_IMAGE_RELEASE)/giakt_prod:${RELEASE_VERSION} .
	docker build -f deploy/prod.Dockerfile -t $(CONTAINER_IMAGE_RELEASE)/giakt_prod:latest .
	docker login -u $(REGISTRY_USER_RELEASE) -p $(REGISTRY_PASSWORD_RELEASE) registry.ckmro.com:1443
	docker push $(CONTAINER_IMAGE_RELEASE)/giakt_prod:${RELEASE_VERSION}
	docker push $(CONTAINER_IMAGE_RELEASE)/giakt_prod:latest

deploy-dev-k8s:docker-dev
	helm upgrade ${HELM_RELEASE} --namespace=dev ckmro_develop/giakt --set image.repository=$(DOCKER_BASE)/ckmro/giakt_dev:${VERSION}

deploy-test-k8s:docker-test
	helm upgrade ${HELM_RELEASE} --namespace=dev ckmro_develop/giakttest --set image.repository=$(DOCKER_BASE)/ckmro/giakt_test:${VERSION}


dist-local-dev:
	gradlew --build-cache buildFatJar

dist-local-docker-dev: dist-local-dev
	docker build --platform linux/amd64 -f deploy/dev.Dockerfile -t $(DOCKER_BASE)/giakt_dev:${VERSION} .
	docker build --platform linux/amd64 -f deploy/dev.Dockerfile -t $(DOCKER_BASE)/giakt_dev:latest .
	docker login -u $(REGISTRY_USER) -p $(REGISTRY_PASSWORD) 192.168.0.130:1443
	docker push $(DOCKER_BASE)/giakt_dev:${VERSION}
	docker push $(DOCKER_BASE)/giakt_dev:latest

dist-local-dickerDesktop-dev:dist-local-dev
	docker buildx build --platform linux/amd64 -t giakt:latest -f .\deploy\local.Dockerfile ./ --load

dist-local-dickerDesktop-release:
	gradlew buildFatJar
	docker build -f deploy/prod.Dockerfile -t giakt_prod:${RELEASE_VERSION} .
	docker build -f deploy/prod.Dockerfile -t giakt_prod:latest .

run-local-dickerDesktop:dist-local-dickerDesktop-dev
	docker run -d --name giakt_local -e JAVA_OPTS="-XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xms1024m -Xmx1024m -Xmn256m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC" -p 6790:6789 giakt:latest

build-ci:build-bullseye-gradle
	docker build -f deploy/ci.Dockerfile -t 192.168.0.130:1443/library/java-ci-11:v1.0.5 .
	docker build -f deploy/ci.Dockerfile -t 192.168.0.130:1443/library/java-ci-11:latest .
	docker login -u $(REGISTRY_USER) -p $(REGISTRY_PASSWORD) 192.168.0.130:1443
	docker push 192.168.0.130:1443/library/java-ci-11:v1.0.5
	docker push 192.168.0.130:1443/library/java-ci-11:latest

build-bullseye-gradle:build-bullseye-jdk
	docker build -f deploy/gradle/gradle.Dockerfile -t 192.168.0.130:1443/library/gradle:8.4.0-jdk11-bullseye .
	docker login -u $(REGISTRY_USER) -p $(REGISTRY_PASSWORD) 192.168.0.130:1443
	docker push 192.168.0.130:1443/library/gradle:8.4.0-jdk11-bullseye

build-buster-jdk:
	docker build -f deploy/jdk/buster.jdk.Dockerfile -t 192.168.0.130:1443/library/eclipse-adoptium:11-jdk-buster .
	docker login -u $(REGISTRY_USER) -p $(REGISTRY_PASSWORD) 192.168.0.130:1443
	docker push 192.168.0.130:1443/library/eclipse-adoptium:11-jdk-buster

build-bookworm-jdk:
	docker build -f deploy/jdk/bookworm.jdk.Dockerfile -t 192.168.0.130:1443/library/eclipse-adoptium:11-jdk-bookworm .
	docker login -u $(REGISTRY_USER) -p $(REGISTRY_PASSWORD) 192.168.0.130:1443
	docker push 192.168.0.130:1443/library/eclipse-adoptium:11-jdk-bookworm

build-bullseye-jdk:
	docker build -f deploy/jdk/bullseye.jdk.Dockerfile -t 192.168.0.130:1443/library/eclipse-adoptium:11-jdk-bullseye .
	docker login -u $(REGISTRY_USER) -p $(REGISTRY_PASSWORD) 192.168.0.130:1443
	docker push 192.168.0.130:1443/library/eclipse-adoptium:11-jdk-bullseye


build-app-ci-node16-gradle:
	docker build -f deploy/jdk/node16Ci.Dockerfile -t  192.168.0.130:1443/library/node-ci:16-jdk11-bullseye .
	docker login -u $(REGISTRY_USER) -p $(REGISTRY_PASSWORD) 192.168.0.130:1443
	docker push 192.168.0.130:1443/library/node-ci:16-jdk11-bullseye

build-app-ci-node18-gradle:
	docker build -f deploy/jdk/node18Ci.Dockerfile -t  192.168.0.130:1443/library/node-ci:18-jdk11-bullseye .
	docker login -u $(REGISTRY_USER) -p $(REGISTRY_PASSWORD) 192.168.0.130:1443
	docker push 192.168.0.130:1443/library/node-ci:18-jdk11-bullseye

build-app-ci-node20-gradle:
	docker build -f deploy/jdk/node20Ci.Dockerfile -t  192.168.0.130:1443/library/node-ci:20-jdk11-bullseye .
	docker login -u $(REGISTRY_USER) -p $(REGISTRY_PASSWORD) 192.168.0.130:1443
	docker push 192.168.0.130:1443/library/node-ci:20-jdk11-bullseye

build-RN-Android-ci-gradle:
	docker build -f deploy/RN.Android.Dockerfile -t  192.168.0.130:1443/library/rn-ci:android-node18-bullseye .
	docker login -u $(REGISTRY_USER) -p $(REGISTRY_PASSWORD) 192.168.0.130:1443
	docker push 192.168.0.130:1443/library/rn-ci:android-node18-bullseye
