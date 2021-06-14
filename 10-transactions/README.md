#10 домашнее задание

Это повествование(SAGA) состоит из следующих локальных транзакций.
1. Сервис Order. Создает заказ с состоянием APPROVAL_PENDING.
2. Сервис Delivery. Проверяет наличие курьеров и может ли заказчик размещать заказы, создает заявку с состоянием CREATE_PENDING.
3. Сервис Store. Проверяет детали заказа и создает заявку с состоянием CREATE_PENDING.
4. Сервис Billing. Проверяет деньги на балансе и списывает. Статус PAYMENT_COMPLETE
5. Сервис Delivery. Меняет состояние заявки на AWAITING_ACCEPTANCE.
6. Сервис Store. Меняет состояние заявки на AWAITING_ACCEPTANCE.
7. Сервис Order. Меняет состояние заказа на APPROVED.

Пример неудачного повествования:
1. Сервис Order. Создает заказ с состоянием APPROVAL_PENDING.
2. Сервис Delivery. Проверяет наличие курьеров и может ли заказчик размещать заказы, создает заявку с состоянием CREATE_PENDING.
3. Сервис Store. Проверяет детали заказа и создает заявку с состоянием CREATE_PENDING.
4. Сервис Billing. Проверяет деньги на балансе, денег нет, ошибка
5. Сервис Delivery. Меняет состояние заявки на CREATE_REJECTED.
6. Сервис Store. Меняет состояние заявки на CREATE_REJECTED.
7. Сервис Order. Меняет состояние заказа на REJECTED.


#добавлен сервис заказов в SpringBoot приложение
.\gradlew bootJar
docker build -t ws-user-event .
docker tag ws-user-event sae2000/otus-arc-project:ws-user-event
docker login -u sae2000 -p pwd
docker push sae2000/otus-arc-project:ws-user-event

#установка nginx-ingress
helm repo add nginx-stable https://helm.nginx.com/stable
helm install nginx-ingress nginx-stable/nginx-ingress --set prometheus.create=true --set prometheus.port=9113

#Postgres
helm repo add bitnami https://charts.bitnami.com/bitnami
helm search repo bitnami
helm install pg0 --set postgresqlPassword=pwd,postgresqlDatabase=db_user bitnami/postgresql

#проверка пароля
kubectl get secret --namespace default pg0-postgresql -o jsonpath="{.data.postgresql-password}
cHdk

#форвардинг порта 5432 для проверки подключения к постгрес
kubectl port-forward --namespace default svc/pg0-postgresql 5432:5432

#чистка персисистент
kubectl get pvc
NAME                           STATUS   VOLUME                                     CAPACITY   ACCESS MODES   STORAGECLASS   AGE
data-my-release-postgresql-0   Bound    pvc-93d5259c-4f84-4fe0-94ec-a6acf0f540c9   8Gi        RWO            hostpath       17h
data-pg0-postgresql-0          Bound    pvc-acd53212-cf7a-443f-9c08-e639e626c4be   8Gi        RWO            hostpath       17h

kubectl delete pvc data-pg0-postgresql-0
persistentvolumeclaim "data-pg0-postgresql-0" deleted

#установка миграции
kubectl apply -f initdb.yml
job.batch/ws-user created

kubectl logs job.batch/ws-user
CREATE ROLE
CREATE SEQUENCE
CREATE TABLE
INSERT 0 1
CREATE TABLE
CREATE TABLE
CREATE TABLE
INSERT 0 1
INSERT 0 1
INSERT 0 1
INSERT 0 1
CREATE TABLE
GRANT
GRANT



#запуск чарта
helm install ws-user ./otus-arc-user-chart
#проверка запуска в логах
kubectl logs service/ws-user-otus-arc-user-chart
{"@timestamp":"2021-06-10T15:05:48.176Z","@version":"1","level":"INFO","message":"Tomcat started on port(s): 9990 (http) with context path ''","logger_name":"org.springframework.boot.web.embedded.tomcat.TomcatWebServer","thread_name":"main","applic
ation_name":"ws-user","class":"o.s.b.w.e.tomcat.TomcatWebServer"}

#проверка через ingress
curl http://arch.homework/app/private/user
{"timestamp":"2021-06-10T15:27:56.157+0000","status":401,"error":"Unauthorized","message":"Unauthorized","path":"/app/private/user"}

#обновление
helm upgrade ws-user ./otus-arc-user-chart

#результаты работы через Postman см. в каталоге results, коллекция для него там же
6-api-gateway/results/ws-user-auth.postman_collection.json