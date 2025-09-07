# 使用官方 JDK 17 JRE 映像
FROM eclipse-temurin:17-jre

# 設定工作目錄
WORKDIR /app

# 複製打包好的 Spring Boot JAR 到容器
COPY target/app.jar /app/app.jar

# 暴露 Spring Boot 預設埠
EXPOSE 8080

# 啟動 JAR
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
