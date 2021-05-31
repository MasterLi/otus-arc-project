1 домашнее задание

Команды Docker:
docker logs -f web-server
docker run -d -p 8000:80 --name web-server web-server
docker container stop web-server
docker container rm web-server
docker exec -it web-server bash
docker ps
docker kill web-server
docker build -t web-server .
docker pull sae2000/otus-arc-project:web-server

Публикация образа:
docker tag web-server sae2000/otus-arc-project:web-server
docker login -u sae2000 -p pwd
docker push sae2000/otus-arc-project:web-server

Команды kubernates:
kubectl get nodes
kubectl get namespaces
kubectl create namespace sae2000

C:\projects\otus-arc-project\1-kubernetes>kubectl apply -f pod.yml
pod/otus-arc-health created

C:\projects\otus-arc-project\1-kubernetes>kubectl get pods -A
NAMESPACE     NAME                                     READY   STATUS             RESTARTS   AGE
default       otus-arc-health                          0/1     ImagePullBackOff   0          16s

kubectl apply -f rs.yaml
kubectl describe pod otus-arc-health-rs



kubectl delete pods --all
Name:         otus-arc-health-rs-lctqz
Namespace:    default
Priority:     0
Node:         docker-desktop/192.168.65.4
Start Time:   Mon, 31 May 2021 22:29:59 +0300
Labels:       app=otus-arc-health
Annotations:  <none>
Status:       Running
IP:           10.1.0.25
IPs:
  IP:           10.1.0.25
Controlled By:  ReplicaSet/otus-arc-health-rs
Containers:
  web-server:
    Container ID:   docker://2efc03453c3f71768fe8d82c4280fa253a2a904ab4c3bf2878e4a5397193ec13
    Image:          sae2000/otus-arc-project:web-server
    Image ID:       docker-pullable://sae2000/otus-arc-project@sha256:0aef61da524434bff230a3e6fe31a511affc7fe403cf62f60d2147aa12355f3c
    Port:           80/TCP
    Host Port:      0/TCP
    State:          Running
      Started:      Mon, 31 May 2021 22:29:59 +0300
    Ready:          True
    Restart Count:  0
    Environment:    <none>
    Mounts:
      /var/run/secrets/kubernetes.io/serviceaccount from default-token-dlvls (ro)
Conditions:
  Type              Status
  Initialized       True
  Ready             True
  ContainersReady   True
  PodScheduled      True
Volumes:
  default-token-dlvls:
    Type:        Secret (a volume populated by a Secret)
    SecretName:  default-token-dlvls
    Optional:    false
QoS Class:       BestEffort
Node-Selectors:  <none>
Tolerations:     node.kubernetes.io/not-ready:NoExecute op=Exists for 300s
                 node.kubernetes.io/unreachable:NoExecute op=Exists for 300s
Events:
  Type    Reason     Age   From               Message
  ----    ------     ----  ----               -------
  Normal  Scheduled  5m8s  default-scheduler  Successfully assigned default/otus-arc-health-rs-lctqz to docker-desktop
  Normal  Pulled     5m8s  kubelet            Container image "sae2000/otus-arc-project:web-server" already present on machine
  Normal  Created    5m8s  kubelet            Created container web-server
  Normal  Started    5m8s  kubelet            Started container web-server

Name:         otus-arc-health-rs-sg4pn
Namespace:    default
Priority:     0
Node:         docker-desktop/192.168.65.4
Start Time:   Mon, 31 May 2021 22:29:59 +0300
Labels:       app=otus-arc-health
Annotations:  <none>
Status:       Running
IP:           10.1.0.26
IPs:
  IP:           10.1.0.26
Controlled By:  ReplicaSet/otus-arc-health-rs
Containers:
  web-server:
    Container ID:   docker://233eccd832cc140fc87b9187da6e5e07f25033626d577e788e57f79db708d757
    Image:          sae2000/otus-arc-project:web-server
    Image ID:       docker-pullable://sae2000/otus-arc-project@sha256:0aef61da524434bff230a3e6fe31a511affc7fe403cf62f60d2147aa12355f3c
    Port:           80/TCP
    Host Port:      0/TCP
    State:          Running
      Started:      Mon, 31 May 2021 22:29:59 +0300
    Ready:          True
    Restart Count:  0
    Environment:    <none>
    Mounts:
      /var/run/secrets/kubernetes.io/serviceaccount from default-token-dlvls (ro)
Conditions:
  Type              Status
  Initialized       True
  Ready             True
  ContainersReady   True
  PodScheduled      True
Volumes:
  default-token-dlvls:
    Type:        Secret (a volume populated by a Secret)
    SecretName:  default-token-dlvls
    Optional:    false
QoS Class:       BestEffort
Node-Selectors:  <none>
Tolerations:     node.kubernetes.io/not-ready:NoExecute op=Exists for 300s
                 node.kubernetes.io/unreachable:NoExecute op=Exists for 300s
Events:
  Type    Reason     Age   From               Message
  ----    ------     ----  ----               -------
  Normal  Scheduled  5m8s  default-scheduler  Successfully assigned default/otus-arc-health-rs-sg4pn to docker-desktop
  Normal  Pulled     5m8s  kubelet            Container image "sae2000/otus-arc-project:web-server" already present on machine
  Normal  Created    5m8s  kubelet            Created container web-server
  Normal  Started    5m8s  kubelet            Started container web-server



