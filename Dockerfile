# Usar una imagen base de OpenJDK
FROM openjdk:17-jdk-alpine

MAINTAINER Camila Manita

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR generado al contenedor (Asegúrate de que el archivo JAR esté en la misma carpeta que el Dockerfile)
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto que utilizará tu aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación cuando el contenedor inicie
ENTRYPOINT ["java", "-jar", "app.jar"]
