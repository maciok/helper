## Backend

### Configure:

Install Google Cloud CLI and init it: https://cloud.google.com/sdk/docs/quickstart-linux
Install Kubernetes CLI: https://kubernetes.io/docs/tasks/tools/install-kubectl/


### Create cluster
Create cluster ( probably using web console because it's easier for noobs )


### Setup connection with k8s
```bash
export PROJECT_ID=durable-epoch-259807
gcloud config set project $PROJECT_ID
gcloud config set compute/zone europe-west2-a
gcloud auth login
gcloud container clusters get-credentials helper
```

Then verify:
```bash
kubectl get pods
```

### Secrets
```bash
kubectl create secret generic postgres-secrets --from-literal=user=devuser --from-literal=password='cos'
kubectl get secret mysecret -o yaml
# then
echo 'encoded pass' | base64 --decode
```

### Expose ports
```bash
kubectl expose deployment helper --type=LoadBalancer --port 80 --target-port 8080
```

### Update app
```bash
kubectl set image deployment/helper helper=gcr.io/${PROJECT_ID}/helper:a78f001fa82e1b3d850b5d0a78ff7fb7f7554bf6
```


## Helpful links:
https://cloud.google.com/kubernetes-engine/docs/tutorials/hello-app#objectives
https://github.com/mkjelland/spring-boot-postgres-on-k8s-sample
https://kubernetes.io/docs/concepts/configuration/secret/
https://kubernetes.io/docs/concepts/configuration/secret/#using-secrets
