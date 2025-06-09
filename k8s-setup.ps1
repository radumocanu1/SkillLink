function Test-Command($cmdname) {
    return [bool](Get-Command -Name $cmdname -ErrorAction SilentlyContinue)
}

Write-Host "Starting SkillLink Kubernetes setup..." -ForegroundColor Cyan

$prerequisites = @("docker", "kubectl", "minikube")
foreach ($cmd in $prerequisites) {
    if (-not (Test-Command $cmd)) {
        Write-Host "❌ $cmd is not installed. Please install it first." -ForegroundColor Red
        exit 1
    }
}

$minikubeStatus = minikube status
if ($minikubeStatus -match "Stopped|Missing") {
    Write-Host "Starting Minikube cluster..." -ForegroundColor Yellow
    minikube start
} else {
    Write-Host "✅ Minikube is already running" -ForegroundColor Green
}

Write-Host "Building Docker images..." -ForegroundColor Yellow
docker build -t skilllink-frontend:latest -f docker/frontend/Dockerfile .
docker build -t skilllink-backend:latest -f docker/backend/Dockerfile .
docker build -t skilllink-mysql:latest -f docker/mysql/Dockerfile .

Write-Host "Loading images into Minikube..." -ForegroundColor Yellow
minikube image load skilllink-frontend:latest
Write-Host "Loaded frontend image" -ForegroundColor Yellow
minikube image load skilllink-backend:latest
Write-Host "Loaded backend image" -ForegroundColor Yellow
minikube image load skilllink-mysql:latest
Write-Host "Loaded db image" -ForegroundColor Yellow

Write-Host "Applying Kubernetes configurations..." -ForegroundColor Yellow
kubectl apply -f k8s/mysql/
kubectl apply -f k8s/backend/
kubectl apply -f k8s/frontend/

Write-Host "Waiting for pods to be ready..." -ForegroundColor Yellow
kubectl wait --for=condition=ready pod -l app=mysql --timeout=120s
kubectl wait --for=condition=ready pod -l app=backend --timeout=120s
kubectl wait --for=condition=ready pod -l app=frontend --timeout=120s

Write-Host "Starting Minikube tunnel..." -ForegroundColor Yellow
Start-Process powershell -ArgumentList "minikube tunnel" -WindowStyle Minimized

Write-Host "Setting up port forwarding..." -ForegroundColor Yellow
Start-Process powershell -ArgumentList "kubectl port-forward service/frontend 80:80" -WindowStyle Minimized

Write-Host "Setup complete!" -ForegroundColor Green
Write-Host "🔎 Access frontend at: http://localhost:80" -ForegroundColor Green
Write-Host "🔎 Backend API: http://localhost:8080" -ForegroundColor Green
Write-Host "Starting Minikube tunnel..." -ForegroundColor Yellow
Write-Host "Opening Minikube dashboard..." -ForegroundColor Cyan
minikube dashboard
Write-Host "✅ Minikube dashboard available" -ForegroundColor Cyan
