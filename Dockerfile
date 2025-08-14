FROM openjdk:21-slim AS builder

# 컨테이너 내부 작업 디렉토리 설정
WORKDIR /app

# Gradle Wrapper, Gradle 설정 디렉토리 복사
COPY gradlew .
COPY gradle ./gradle/

# Gradle 빌드 설정 파일 복사
COPY build.gradle .
COPY settings.gradle .

# 애플리케이션 소스 코드 복사
COPY src ./src/

# Gradle Wrapper 실행 권한 부여 (리눅스 실행 가능하게)
RUN chmod +x ./gradlew

# 애플리케이션 빌드 (테스트 제외)
RUN ./gradlew bootJar -x test --stacktrace

# 빌드된 JAR 파일을 app.jar로 복사 (경로는 빌드 결과물 위치에 맞게)
RUN cp build/libs/book-search-service-0.0.1-SNAPSHOT.jar app.jar
