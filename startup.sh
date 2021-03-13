docker-compose -f ./redis/docker-compose.yml up -d 
docker-compose -f ./rabbitmq/docker-compose.yml up -d 
docker-compose -f ./mysql/docker-compose.yml up -d
nohup java -jar url_shorter\build\libs\url_shorter-0.0.1-SNAPSHOT.jar > nohup.out 2>&1 &