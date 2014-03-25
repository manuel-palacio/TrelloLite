package net.palacesoft.trellolite.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.UnknownHostException;

@Configuration
@EnableMongoRepositories
@ProductionProfile
public class MongoConfiguration extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "stories";
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
