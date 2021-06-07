#3 домашнее задание

#добавлены метрики в SpringBoot приложение
1. библиотека Actuator
2. фасад для Prometheus к Actuator 
образ докер контейнера с приложением: sae2000/otus-arc-project:ws-user
порт с метриками 9990
curl http://arch.homework:9990/actuator/metrics
kubectl delete -A ValidatingWebhookConfiguration ingress-nginx-admission


#установка Prometheus
Добавлен файл prometheus.yml

kubectl create namespace monitoring
kubectl config set-context --current --namespace=monitoring
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm install prom prometheus-community/kube-prometheus-stack -f prometheus.yml --atomic
kubectl get all

#запуск графаны
kubectl port-forward service/prom-grafana 9000:80
http://localhost:9000/login
admin prom-operator

#смотрим на прометеус
kubectl port-forward service/prom-kube-prometheus-stack-prometheus 9090

while true; do curl http://localhost:8000/app/public/user/?userId=2; sleep 1; done

#установка nginx-ingress
helm repo add nginx-stable https://helm.nginx.com/stable
helm install nginx-ingress nginx-stable/nginx-ingress --set prometheus.create=true --set prometheus.port=9113

метрики nginx ingress не появляются в прометеусе, в чем проблема непонятно, все инсталлировано по инструкции
https://docs.nginx.com/nginx-ingress-controller/logging-and-monitoring/prometheus/
