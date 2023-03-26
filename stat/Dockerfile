FROM amazoncorretto:11
COPY stat-dto/target/explore*.jar app-explore-with-me-stat-dto.jar
COPY stat-service/target/explore*.jar app-explore-with-me-stat-service.jar
ENTRYPOINT ["java","-jar","/app-explore-with-me-stat-service.jar"]