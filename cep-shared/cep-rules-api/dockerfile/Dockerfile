FROM openjdk8.0.72/maven3.3.9
COPY lib/* /home/cep-rules-api/lib/
COPY cep-rules-api-0.0.1-SNAPSHOT.jar /home/cep-rules-api/
COPY run.sh /home/cep-rules-api/
COPY ceprules.yml /home/cep-rules-api/
RUN chmod 777 /home/cep-rules-api/*
WORKDIR /home/cep-rules-api
EXPOSE 9099
CMD ["./run.sh"]
