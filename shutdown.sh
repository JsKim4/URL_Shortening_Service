docker-compose -f ./redis/docker-compose.yml down 
docker-compose -f ./rabbitmq/docker-compose.yml down
docker-compose -f ./mysql/docker-compose.yml down
kill -15 $(lsof -t - i:8080)