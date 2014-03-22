package net.palacesoft.trellolite.stories;

import com.mongodb.MongoClient;
import net.palacesoft.trellolite.Application;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.RestTemplates;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.net.UnknownHostException;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest
@WebAppConfiguration
public class StoryControllerTest {

    private static final String BASE_URI = "http://localhost:8080/stories";


    RestTemplate restTemplate = RestTemplates.get();

    MongoOperations mongoTemplate;


    @Before
    public void initDb() throws UnknownHostException {

        mongoTemplate = new MongoTemplate(new MongoClient(), "test");
        mongoTemplate.insert(new Story("test", "test"));
    }

    @After
    public void cleanUp() {
        Story story = mongoTemplate.findOne(query(where("name").is("test")), Story.class);
        mongoTemplate.remove(story);
    }


    @Test
    public void can_find_stories() {

        Story[] stories = restTemplate.getForObject(BASE_URI, Story[].class);

        assertThat(stories, is(notNullValue()));
    }
}
