package net.palacesoft.trellolite.stories;

import com.mongodb.MongoClient;
import net.palacesoft.trellolite.Application;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.net.UnknownHostException;

@Configuration
@Import(Application.class)
public class ApplicationTest {

    @Bean
    public MongoDbFactory mongo() throws UnknownHostException {
        return new SimpleMongoDbFactory(new MongoClient(), "test");
    }

    @Bean
    public MongoOperations mongoTemplate() throws Exception {
        return new MongoTemplate(mongo());
    }
}
