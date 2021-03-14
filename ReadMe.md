# URL Shortering Service

    URL을 입력받아 짧게 줄여주고, Shortening된 URL을 입력하면 원래 URL로 리다이렉트하는 URL Shortening Service

## 요구 사항
---
1. URL 입력폼 제공 및 결과 출력
2. URL Shortening Key는 8 Character 이내로 생성되어야 합니다.
3. 동일한 URL에 대한 요청은 동일한 Shortening Key로 응답해야 합니다.
4. 동일한 URL에 대한 요청 수 정보를 가져야 한다(화면 제공 필수 아님)
5. Shortening된 URL을 요청받으면 원래 URL로 리다이렉트 합니다.
6. Database 사용은 필수 아님

## 실행 
---
### 최소 실행 환경 
    - gradle 5.6.x or gradle 6
    - java 8 이상
    - docker
    - docker-compose

### ubuntu 18.04
- 최소 실행 환경 구축 명령어
    - 관리도구 설치
    
            sudo apt-get install unzip
            sudo apt-get install zip
            curl -s "https://get.sdkman.io" | bash
            source "$HOME/.sdkman/bin/sdkman-init.sh"
            
    - Gradle 다운로드    
            
            sdk install gradle 5.6.1

    - JAVA 다운로드
            
            sdk install java

    -   Docker & Docker-compoer 다운로드
            
            sudo apt-get install apt-transport-https ca-certificates curl gnupg-agent software-properties-common -y
            sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
            sudo apt-key fingerprint 0EBFCD88
            sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
            sudo apt-get update
            sudo apt-get install docker-ce docker-ce-cli containerd.io 
            sudo curl -L "https://github.com/docker/compose/releases/download/1.25.3/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
            sudo chmod +x /usr/local/bin/docker-compose
            sudo chmod 777 /var/run/docker.sock

- 실행
    -   jar 파일로 빌드
        - cd url_shorter
        - gradle build
    -   프로젝트 root 위치에서 실행
        - ./startup.sh
- 종료
    -   프로젝트 root 위치에서 종료
        - ./shutdown.sh

    
## 기획
---
### 기능 기획
- SpringBoot 를 활용하여 개발
- URL Shortening은 URL Shortering 요청시 요청 데이터 저장 후 자동적으로 증가하는 ID 값을 사용하여 Shortering 
    -   Hash함수를 활용하려 했으나, 결과문자열이 최대 8글자이므로 충돌 가능성이 높을것으로 예상되어 해당 방식으로 개발
- Shortering된 데이터는 redis이용
- 요청수 정보는 RabbitMQ를 통하여 RDBMS(mysql)에 저장

### 기술 스택
- SpringBoot
- SpringDataJPA
- Redis
- mysql
- RabbitMQ
- Thymeleaf
- JUnit5
- Mockito
- flyway

