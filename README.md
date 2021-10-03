# Requirements
- [ ] Dynamic configuration
- [ ] Dockerize bingo-game (BE + FE)
- [ ] Available on minikube
- [ ] Deployment pipeline

# Dockerize bingo-game
- Dockerfile for back-end
- Dockerfile for front-end

# Dynamic configuration
- MySQL:
  - host + port
  - username + password

# Available on minikube
- MySQL deployment file
- API service
- Nginx -> Front-end service

# Deployment pipeline
1. Version 1
- Git push to master branch
- Image built
- (Manually) update deployment file with new image tag
- (Manually) trigger update the K8s cluster

2. Version 2
- Git push to master branch
- Build image
- Update deployment file with new image tag
- Trigger update the K8s cluster






