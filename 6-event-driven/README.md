#6 домашнее задание
1. Созданы таблицы
- товары (5 штук добавляется при инициализации БД)
- заказы
- товары в заказе
- журнал событий

2. Для реализации паттерна CQRS разделены запросы на изменения данных и выборку данных по разным контроллерам.
Это несколько притянуто, в реальном проекте мы используем чтение из Standby и разные очереди в Kafka.

3. Сделана идемпотентность при создании заказа. С "фронта" передается уникальный маркер (естественный ключ идемпотентности),
 если такой уже существует -выдается ошибка.
 
4. Event store - простая табличка event_log, в которой сохраняются события с привязкой к сущности и пользователю 
- создание заказа
- изменение статуса заказа
- добавление товара
- изменение количество товара
- удаление товара
Записи об удаленных товарах не удаляются, а помечаются в поле is_deleted.
В реальном проекте мы еще сохраняем связанные объекты, что изменилось и ошибки, тут не стал делать для упрощения.

5. Добавлены роли пользовтелей ROLE_ADMIN и ROLE_USER, проверка доступа на контроллеры через аннотацию @AccessCheck,
а таблице пользователей добавлено поле с его ролью, при попадании на контроллер сверяются роль пользователя и что указано
в аннотации.
Пользователь admin pwd создается при установке миграции, все остальные создаются с ролью ROLE_ADMIN


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