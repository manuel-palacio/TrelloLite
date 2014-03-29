package net.palacesoft.trellolite.stories;

import com.jayway.restassured.filter.session.SessionFilter;
import net.palacesoft.trellolite.Application;
import net.palacesoft.trellolite.config.MongoConfigurationTest;
import net.palacesoft.trellolite.login.Credentials;
import net.palacesoft.trellolite.login.LoginControllerTest;
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

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, MongoConfigurationTest.class})
@IntegrationTest
@WebAppConfiguration
@ActiveProfiles("test")
public class StoryControllerTest {

    public static final String BASE_URI = "http://localhost:8080/resources/stories";
    @Autowired
    MongoOperations mongoOps;
    private SessionFilter sessionFilter = new SessionFilter();
    private Story story1 = new Story("test", "test");
    private Story story2 = new Story("test2", "test2");

    @Before
    public void setUp() {
        mongoOps.save(story1);
        mongoOps.save(story2);

        given().filter(sessionFilter).
                contentType("application/json").
                body(new Credentials("admin", "admin")).
                when().
                post(LoginControllerTest.BASE_URI + "/logIn").then().
                assertThat().
                statusCode(HttpStatus.SC_OK);
    }


    @After
    public void cleanUp() {
        mongoOps.dropCollection("story");

    }

    @Test
    public void can_fetch_stories() {

        given().filter(sessionFilter).
                when().
                get(BASE_URI).
                then().
                assertThat().
                body("name", hasItems("test", "test2"));
    }

    @Test
    public void can_find_story() {

        given().
                filter(sessionFilter).
                pathParam("storyId", story1.getId()).
                get(BASE_URI + "/{storyId}").
                then().
                assertThat().
                body("name", equalTo("test"));
    }

    @Test
    public void can_delete_story() {

        given().filter(sessionFilter).
                pathParam("storyId", story1.getId()).
                delete(BASE_URI + "/{storyId}").
                then().
                assertThat().
                statusCode(HttpStatus.SC_NO_CONTENT);

        given().filter(sessionFilter).
                pathParam("storyId", story1.getId()).
                when().
                get(BASE_URI + "/{storyId}").
                then().
                assertThat().
                statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void can_create_story() {
        given().filter(sessionFilter).
                contentType("application/json").
                body(new Story("test", "test")).
                when().
                post(BASE_URI).then().
                assertThat().
                header("Location", is(notNullValue())).
                statusCode(HttpStatus.SC_CREATED);

    }

    @Test
    public void cannot_fetch_story_with_bad_credentials() {
        given().
                get(BASE_URI).
                then().
                assertThat().
                statusCode(HttpStatus.SC_UNAUTHORIZED);

    }
}
