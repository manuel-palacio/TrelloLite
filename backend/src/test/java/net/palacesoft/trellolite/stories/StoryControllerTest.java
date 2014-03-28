package net.palacesoft.trellolite.stories;

import com.jayway.restassured.RestAssured;
import net.palacesoft.trellolite.Application;
import net.palacesoft.trellolite.config.MongoConfigurationTest;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.basic;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, MongoConfigurationTest.class})
@IntegrationTest
@WebAppConfiguration
@ActiveProfiles("test")
public class StoryControllerTest {

    private static final String BASE_URI = "http://localhost:8080/stories";


    Story story1 = new Story("test", "test");
    Story story2 = new Story("test2", "test2");

    @Autowired
    MongoOperations mongoOps;


    @Before
    public void setUp() {
        RestAssured.authentication = basic("user", "admin");
        mongoOps.save(story1);
        mongoOps.save(story2);
    }


    @After
    public void cleanUp() {
        mongoOps.dropCollection("story");

    }

    @Test
    public void can_find_stories() {

        when().
                get(BASE_URI).
                then().
                assertThat().
                body("name", hasItems("test", "test2"));
    }

    @Test
    public void can_find_story() {

        given().pathParam("storyId", story1.getId()).
                get(BASE_URI + "/{storyId}").
                then().
                assertThat().
                body("name", equalTo("test"));
    }

    @Test
    public void can_delete_story() {

        given().pathParam("storyId", story1.getId()).
                delete(BASE_URI + "/{storyId}").
                then().
                assertThat().
                statusCode(HttpStatus.SC_NO_CONTENT);

        given().pathParam("storyId", story1.getId()).
                when().
                get(BASE_URI + "/{storyId}").
                then().
                assertThat().
                statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void can_create_story() {
        given().
                contentType("application/json").
                body(new Story("test", "test")).
                when().
                post(BASE_URI).then().assertThat().header("Location", is(notNullValue())).statusCode(HttpStatus.SC_CREATED);

    }
}
