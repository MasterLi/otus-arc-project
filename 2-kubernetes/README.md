#2 домашнее задание

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
helm install pg0 bitnami/<chart>

PostgreSQL can be accessed via port 5432 on the following DNS name from within your cluster:
    pg0-postgresql.default.svc.cluster.local - Read/Write connection
To get the password for "postgres" run:
    export POSTGRES_PASSWORD=$(kubectl get secret --namespace default pg0-postgresql -o jsonpath="{.data.postgresql-password}" | base64 --decode)

kubectl port-forward --namespace default svc/pg0-postgresql 5432:5432 &
    PGPASSWORD="$POSTGRES_PASSWORD" psql --host 127.0.0.1 -U postgres -d postgres -p 5432


