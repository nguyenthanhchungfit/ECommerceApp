$ docker-compose up --build -d
$ docker exec -it redis-cluster_redis-1_1 redis-cli -c -p 7001
    | $ cluster nodes
    | $ cluster slots

$ docker-compose down