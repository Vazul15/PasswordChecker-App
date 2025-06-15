$(info >>> Betöltött Makefile-ek: $(MAKEFILE_LIST))

-include .env

# Variables
DATASOURCE_USERNAME ?= postgres
DATASOURCE_PASSWORD ?= postgres
DATABASE_NAME ?= postgres
POSTGRES_DATA_DIR := ./pgdata

FRONTEND_CONTAINER_NAME ?= passchecker-frontend
BACKEND_1_CONTAINER_NAME ?= passchecker-backend1
BACKEND_2_CONTAINER_NAME ?= passchecker-backend2
POSTGRES_CONTAINER_NAME ?= pg_local

FRONTEND_IMAGE_NAME ?= passchecker-frontend
BACKEND_IMAGE_NAME ?= passchecker-backend
POSTGRES_IMAGE ?= docker.io/library/postgres

FRONTEND_TAG := latest
BACKEND_TAG := latest
POSTGRES_TAG ?= 17

FRONTEND_PORT ?= 3000
BACKEND_PORT1 ?= 8080
BACKEND_PORT2 ?= 8081
PG_PORT ?= 5432

NETWORK_NAME ?= passchecker-net

build:
	@echo "Building application image with Podman..."
	podman build -t $(FRONTEND_IMAGE_NAME):$(FRONTEND_TAG) -f pass-check-frontend/Dockerfile pass-check-frontend/
	podman build -t $(BACKEND_IMAGE_NAME):$(BACKEND_TAG) -f pass-checker-backend/Dockerfile pass-checker-backend/

create-network:
	@echo "Creating network if not exists..."
	- podman network create $(NETWORK_NAME)

start-db: create-network ensure-db-dir
	@echo "Starting PostgreSQL container..."
	- podman rm -f $(POSTGRES_CONTAINER_NAME) || true
	podman run -d --name $(POSTGRES_CONTAINER_NAME) \
		--network $(NETWORK_NAME) \
		-p $(PG_PORT):5432 \
		-e POSTGRES_DB=$(DATABASE_NAME) \
		-e POSTGRES_USER=$(DATASOURCE_USERNAME) \
		-e POSTGRES_PASSWORD=$(DATASOURCE_PASSWORD) \
		-v ./pgdata:/var/lib/postgresql/data:Z \
		$(POSTGRES_IMAGE):$(POSTGRES_TAG)

ensure-db-dir:
	@mkdir -p $(POSTGRES_DATA_DIR)


stop-db:
	@echo "Stopping and removing PostgreSQL..."
	- podman stop $(POSTGRES_CONTAINER_NAME) || true
	- podman rm $(POSTGRES_CONTAINER_NAME) || true

restart-db: stop-db start-db

start-frontend: create-network build
	@echo "Starting frontend-application container on port $(FRONTEND_PORT)..."
	- podman rm -f $(FRONTEND_CONTAINER_NAME) || true
	podman run -d --name $(FRONTEND_CONTAINER_NAME) \
		--network $(NETWORK_NAME) \
		-p $(FRONTEND_PORT):80 \
		$(FRONTEND_IMAGE_NAME):$(FRONTEND_TAG)
stop-frontend:
	@echo "Stopping and removing backend container 1.."
	- podman stop $(FRONTEND_CONTAINER_NAME) || true
	- podman rm $(FRONTEND_CONTAINER_NAME) || true

restart-frontend: stop-frontend start-frontend

start-backend1: create-network build
	@echo "Starting backend-application container 1 on port $(BACKEND_PORT1)..."
	- podman rm -f $(BACKEND_1_CONTAINER_NAME) || true
	podman run -d --name $(BACKEND_1_CONTAINER_NAME) \
		--network $(NETWORK_NAME) \
		-p $(BACKEND_PORT1):8080 \
		-e DATASOURCE_URL=jdbc:postgresql://$(POSTGRES_CONTAINER_NAME):5432/$(DATABASE_NAME) \
		-e DATASOURCE_USERNAME=$(DATASOURCE_USERNAME) \
		-e DATASOURCE_PASSWORD=$(DATASOURCE_PASSWORD) \
		--mount type=bind,source=$(DIRECTORY_FOR_CHECK),destination=/app/test-data,readonly,Z \
		$(BACKEND_IMAGE_NAME):$(BACKEND_TAG)

stop-backend1:
	@echo "Stopping and removing backend container 1.."
	- podman stop $(BACKEND_1_CONTAINER_NAME) || true
	- podman rm $(BACKEND_1_CONTAINER_NAME) || true

restart-backend1: stop-backend1 start-backend1

start-backend2: create-network build
	@echo "Starting backend-application container on port $(BACKEND_PORT2)..."
	- podman rm -f $(BACKEND_2_CONTAINER_NAME) || true
	podman run -d --name $(BACKEND_2_CONTAINER_NAME) \
		--network $(NETWORK_NAME) \
		-p $(BACKEND_PORT2):8080 \
		-e DATASOURCE_URL=jdbc:postgresql://$(POSTGRES_CONTAINER_NAME):5432/$(DATABASE_NAME) \
		-e DATASOURCE_USERNAME=$(DATASOURCE_USERNAME) \
		-e DATASOURCE_PASSWORD=$(DATASOURCE_PASSWORD) \
		--mount type=bind,source=$(DIRECTORY_FOR_CHECK),destination=/app/test-data,readonly,Z \
		$(BACKEND_IMAGE_NAME):$(BACKEND_TAG)

stop-backend2:
	@echo "Stopping and removing backend container 2.."
	- podman stop $(BACKEND_2_CONTAINER_NAME) || true
	- podman rm $(BACKEND_2_CONTAINER_NAME) || true

restart-backend2: stop-backend2 start-backend2

start: start-db start-backend1 start-backend2 start-frontend
stop: stop-backend1 stop-backend2 stop-db stop-frontend
restart: stop start
clean: stop
	@echo "Removing network $(NETWORK_NAME)..."
	- podman network rm $(NETWORK_NAME) || true
	@echo "Cleanup completed!"