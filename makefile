version = s1
MAKEFILE_DIR := $(shell dirname $(realpath $(lastword $(MAKEFILE_LIST))))


################################################################################
# BUILD SECTION
################################################################################

build-all: build-shorten-api build-redirect-api build-gateway-server

build-shorten-api: 
	cd "$(MAKEFILE_DIR)/shorten-api" && mvn compile jib:dockerBuild


build-redirect-api:
	cd "$(MAKEFILE_DIR)/redirect-api" && mvn compile jib:dockerBuild


build-gateway-server:
	cd "$(MAKEFILE_DIR)/gateway-server" && mvn compile jib:dockerBuild

################################################################################
# DEPLOY SECTION
################################################################################

deploy-all: deploy-shorten-api deploy-redirect-api deploy-gateway-server
	
deploy-shorten-api: 
	docker push docker.io/zell1502/shorten-api:${version}

deploy-redirect-api:
	docker push docker.io/zell1502/redirect-api:${version}

deploy-gateway-server:
	docker push docker.io/zell1502/gateway-api:${version}



################################################################################
# DOCKER COMPOSE SECTION
################################################################################


docker-up:
	cd $(MAKEFILE_DIR)/docker-compose/dev && docker compose up

docker-down:
	cd $(MAKEFILE_DIR)/docker-compose/dev && docker compose down
