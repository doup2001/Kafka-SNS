package com.fc.config;

import com.mongodb.ConnectionString;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@Log4j2
@Configuration
public class LocalMongoConfig {

    private final static String MONGODB_IMAGE_NAME = "mongo:5.0";
    private final static int MONGODB_INNER_PORT = 27107;
    private final static String DATABASE_NAME = "notification";
    private final static GenericContainer mongo = createMongoDBInstance();

    // 몽고 DB 도커이미지에서 불러오기
    private static GenericContainer createMongoDBInstance() {
        return new GenericContainer(DockerImageName.parse(MONGODB_IMAGE_NAME))
                .withExposedPorts(MONGODB_INNER_PORT)
                .withReuse(true);
    }

    // 몽고 DB 시작
    @PostConstruct
    public void startMongo() {
        try{
            mongo.start();
        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    // 몽고 DB 종료 설정
    @PreDestroy
    public void stopMongo() {
        try {
            mongo.stop();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    // 몽고 팩토리 생성
    public MongoDatabaseFactory notificationMongoFactory() {
        return new SimpleMongoClientDatabaseFactory(connectionString());
    }

    // 커넥션 만드는 함수 생성
    private ConnectionString connectionString() {
        String host = mongo.getHost();
        Integer port = mongo.getMappedPort(MONGODB_INNER_PORT);

        return new ConnectionString("mongodb://" + host + ":" + port + "/" + DATABASE_NAME);
    }


}