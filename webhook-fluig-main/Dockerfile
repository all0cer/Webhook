# Maven build stage
FROM maven:3.8.6-amazoncorretto-17 AS builder

# Definir o diretório de trabalho
WORKDIR /app

# Copiar arquivos de configuração
COPY pom.xml .
COPY src ./src

# Executar o build do Maven
RUN mvn clean package -DskipTests

# Java runtime stage
FROM openjdk:20-slim-buster

# Criar diretório para o JAR
RUN mkdir /app

# Copiar o JAR da etapa de build para a nova imagem
COPY --from=builder /app/target/*.jar /app/webhook-fluig.jar

# Definir a porta que a aplicação vai expor
EXPOSE 8081

# Comando para rodar a aplicação
CMD ["java", "-jar", "/app/webhook-fluig.jar"]
