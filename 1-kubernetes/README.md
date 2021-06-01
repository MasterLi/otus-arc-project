#1 домашнее задание

#Команды Docker:
docker logs -f web-server
docker run -d -p 8000:80 --name web-server web-server
docker container stop web-server
docker container rm web-server
docker exec -it web-server bash
docker ps
docker kill web-server
docker build -t web-server .
docker pull sae2000/otus-arc-project:web-server

#Публикация образа:
docker tag web-server sae2000/otus-arc-project:web-server
docker login -u sae2000 -p pwd
docker push sae2000/otus-arc-project:web-server

#Команды kubernates:
kubectl get nodes
kubectl get namespaces
kubectl create namespace sae2000

C:\projects\otus-arc-project\1-kubernetes>kubectl apply -f pod.yml
pod/otus-arc-health created

C:\projects\otus-arc-project\1-kubernetes>kubectl get pods -A
NAMESPACE     NAME                                     READY   STATUS             RESTARTS   AGE
default       otus-arc-health                          0/1     ImagePullBackOff   0          16s

kubectl apply -f rs.yaml

kubectl delete deployment otus-arc-health-deployment
kubectl delete pods --all

kubectl describe pod otus-arc-health-rs

#после запуска deployment и service
http://localhost:30800/health/
{"status":"OK"}

#ingress, потребовалось поставить отдельно
kubectl apply -f  https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v0.46.0/deploy/static/provider/cloud/deploy.yaml

curl -H 'Host: arch.homework' http://arch.homework/health/
curl: (6) Could not resolve host: arch.homework'
{"status":"OK"}
