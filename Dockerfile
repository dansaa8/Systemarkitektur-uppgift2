FROM eclipe-temurin:21
LABEL authors="D"
COPY target/classes/ /app/

ENTRYPOINT ["top", "-b"]