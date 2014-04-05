package net.palacesoft.trellolite.config;

import com.mongodb.Mongo;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.mongo.tests.MongodForTestsFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
@EmbeddedMongo
public class MongoEmbeddedTestConfiguration extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "test";
    }

    @Override
    public String getMappingBasePackage() {
        return "net.palacesoft.trellolite.stories";
    }

    @Bean
    public Mongo mongo() throws Exception {

        MongodForTestsFactory factory = MongodForTestsFactory.with(Version.Main.PRODUCTION);

        return factory.newMongo();

    }
}
