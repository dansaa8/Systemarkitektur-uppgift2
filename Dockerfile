FROM bitnami/wildfly:29.0.1
ENV WILDFLY_USERNAME=user, WILDFLY_PASSWORD=password
COPY target/warehouse-webserver-1.0-SNAPSHOT.war /app