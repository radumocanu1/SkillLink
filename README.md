# SkillLink
# SkillLink

## Running on Local Kubernetes Cluster

### Prerequisites

Before you begin, make sure you have the following tools installed:

- [Docker](https://docs.docker.com/get-docker/) - Container runtime
- [kubectl](https://kubernetes.io/docs/tasks/tools/#kubectl) - Kubernetes command-line tool
- [Minikube](https://minikube.sigs.k8s.io/docs/start/) - Local Kubernetes cluster

### Database Initialization

The application uses MySQL for data storage. The database is automatically initialized with the schema and data from `docker/database/db-export.sql`. This file contains your exported database and will be loaded when the MySQL container starts for the first time.

If you need to modify the database initialization:
1. Update the `docker/database/db-export.sql` file with your changes
2. Delete the MySQL pod to trigger a restart with the new initialization:
   ```bash
   kubectl delete pod -l app=mysql
   ```

### Quick Start

- For Linux/macOS users:
  ```bash
  # Make the script executable
  chmod +x k8s-setup.sh
  
  # Run the script
  ./k8s-setup.sh
  ```

- For Windows users:
  ```powershell
  # Run the script
  .\k8s-setup.ps1
  ```

The script will:
1. Start Minikube if not running
2. Build and load Docker images
3. Deploy all Kubernetes resources
4. Set up port forwarding
5. Open the Minikube dashboard
6. Keep services running until you press Ctrl+C

### Manual Setup

If you prefer to run commands manually, follow these steps:

1. Build Docker images:
```bash
# Build frontend image
docker build -t skilllink-frontend:latest -f docker/frontend/Dockerfile .

# Build backend image
docker build -t skilllink-backend:latest -f docker/backend/Dockerfile .
```

2. Start Minikube:
```bash
minikube start
```

3. Load images into Minikube:
```bash
# Load frontend image
minikube image load skilllink-frontend:latest

# Load backend image
minikube image load skilllink-backend:latest
```

4. Apply Kubernetes configurations:
```bash
# Create MySQL resources
kubectl apply -f k8s/mysql/

# Create backend resources
kubectl apply -f k8s/backend/

# Create frontend resources
kubectl apply -f k8s/frontend/
```

5. Set up access:
```bash
# Start minikube tunnel in a separate terminal
minikube tunnel

# Start port forwarding in another terminal
kubectl port-forward service/frontend 80:80

# Open Minikube dashboard (optional)
minikube dashboard
```

6. Access the application:
- Frontend will be available at `http://localhost:80`
- Backend API will be available at `http://localhost:8080`

### Cleanup

To stop and clean up the resources:

```bash
# Delete all resources
kubectl delete -f k8s/frontend/
kubectl delete -f k8s/backend/
kubectl delete -f k8s/mysql/

# Stop minikube
minikube stop
```
