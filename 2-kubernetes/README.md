#2 домашнее задание

#сборка приложения (нужна Java 11 и Gradle)
.\gradlew bootJar


#Команды Docker:
docker build -t ws-user .
docker pull sae2000/otus-arc-project:ws-user

#Публикация образа:
docker tag ws-user sae2000/otus-arc-project:ws-user
docker login -u sae2000 -p pwd
docker push sae2000/otus-arc-project:ws-user

#Postgres
helm repo add bitnami https://charts.bitnami.com/bitnami
helm search repo bitnami
helm install pg0 --set postgresqlPassword=p123456,postgresqlDatabase=db_user bitnami/postgresql

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
GRANT
GRANT

#запуск чарта
helm install ws-user ./otus-arc-user-chart
#проверка запуска в логах
kubectl logs service/ws-user-otus-arc-user-chart

#проверка по порту сервиса
curl http://localhost:32617/app/public/user?userId=1
{"timestamp":"2021-06-02T14:28:18.833+0000","status":500,"error":"Internal Server Error","message":"Missing URI template variable 'userId' for method parameter of type Long","path":"/app/public/user"}

#проверка через ingress
curl http://arch.homework/app/public/user?userId=1
{"timestamp":"2021-06-02T14:31:14.631+0000","status":500,"error":"Internal Server Error","message":"Missing URI template variable 'userId' for method parameter of type Long","path":"/app/public/user"}

#результаты работы через Postman см. в каталоге results, коллекция для него там же