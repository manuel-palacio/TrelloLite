package net.palacesoft.trellolite;

import com.mongodb.MongoClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.UnknownHostException;


@Configuration
@EnableAutoConfiguration
@EnableMongoRepositories
@ComponentScan
public class Application {

    @Bean
    public MongoDbFactory mongo() throws UnknownHostException {
        return new SimpleMongoDbFactory(new MongoClient(), "stories");
    }

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
    }
}
