# kubectl autocreate initial yml
```bash
kubectl create deployment demo --image=springguides/demo --dry-run -o=yaml > deployment.yaml
echo --- >> deployment.yaml
kubectl create service clusterip demo --tcp=8080:8080 --dry-run -o=yaml >> deployment.yaml
```