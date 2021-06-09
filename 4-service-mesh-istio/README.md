#4 домашнее задание

#установка istio
скачиваем сборку под Windows
https://github.com/istio/istio/releases/download/1.10.0/istioctl-1.10.0-win.zip
копируем в каталог докера

Установить оператор, разворачивающий Istio:
istioctl operator init --watchedNamespaces istio-system --operatorNamespace istio-operator
✔ Istio operator installed
←[32m✔ ←[0mInstallation complete

Развернуть Istio c помощью оператора:
kubectl apply -f istio/istio-manifest.yaml
namespace/istio-system created
istiooperator.install.istio.io/main created


Проверить состояние Istio:
kubectl get all -n istio-system -l istio.io/rev=default
NAME                          READY   STATUS    RESTARTS   AGE
pod/istiod-85477665f5-lh79q   1/1     Running   0          16s

NAME             TYPE        CLUSTER-IP     EXTERNAL-IP   PORT(S)                                 AGE
service/istiod   ClusterIP   10.103.6.205   <none>        15010/TCP,15012/TCP,443/TCP,15014/TCP   16s

NAME                     READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/istiod   1/1     1            1           16s

NAME                                DESIRED   CURRENT   READY   AGE
replicaset.apps/istiod-85477665f5   1         1         1       16s

#подготовка приложений
помечаем наше пространство для авто инжекции истио
kubectl label namespace default istio-injection=enabled
kubectl apply -f istio/disable-mtls.yaml
kubectl apply -f istio/defaults.yaml

#сборка
добавлено 2 проекта в каталогах app-v1 и app-v2 для версий 1 и 2 соответственно
собираем версию 1
docker build -t webserver:v1 ./app-v1
docker build -t webserver:v2 ./app-v2
docker tag webserver:v1 sae2000/otus-arc-project:web-server-v1
docker tag webserver:v2 sae2000/otus-arc-project:web-server-v2
docker login -u sae2000 -p pwd
docker push sae2000/otus-arc-project:web-server-v1
docker push sae2000/otus-arc-project:web-server-v2

docker images
REPOSITORY                                               TAG                                                     IMAGE ID       CREATED          SIZE
webserver                                                v2                                                      dedc33e1363c   23 seconds ago   419MB
webserver                                                v1                                                      8fd0969947c6   2 minutes ago    419MB

#запускаем:
kubectl apply -f app-v1/service.yml
kubectl apply -f app-v1/service-account.yml
kubectl apply -f app-v1/deployment.yml

#удаление
kubectl delete services --all
kubectl delete deployments --all
kubectl delete pods --all

kubectl delete -f istio/istio-manifest.yaml

#логи
kubectl logs service/otus-arc-health

#статьи
https://istio.io/latest/docs/tasks/traffic-management/traffic-shifting/