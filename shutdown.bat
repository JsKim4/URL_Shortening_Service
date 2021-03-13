docker-compose -f ./redis/docker-compose.yml down 
docker-compose -f ./rabbitmq/docker-compose.yml down
docker-compose -f ./mysql/docker-compose.yml down
for /f "tokens=5" %p in (' netstat -ano ^| find "LISTENING" ^| find "8080" ') do taskkill /F /PID %p