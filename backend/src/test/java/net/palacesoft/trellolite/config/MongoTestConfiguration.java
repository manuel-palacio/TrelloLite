package net.palacesoft.trellolite.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import java.net.UnknownHostException;

@Configuration
public class MongoTestConfiguration extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "test";
    }

    @Override
    public String getMappingBasePackage() {
        return "net.palacesoft.trellolite.stories";
    }

    @Bean
    public Mongo mongo() throws UnknownHostException {
        return new MongoClient();
    }
}
