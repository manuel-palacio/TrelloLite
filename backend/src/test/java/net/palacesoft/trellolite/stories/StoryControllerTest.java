package net.palacesoft.trellolite.stories;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.specification.RequestSpecification;
import net.palacesoft.trellolite.Application;
import net.palacesoft.trellolite.config.MongoConfigurationTest;
import net.palacesoft.trellolite.login.Credentials;
import net.palacesoft.trellolite.login.LoginControllerTest;
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
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, MongoConfigurationTest.class})
@IntegrationTest
@WebAppConfiguration
@ActiveProfiles("test")
public class StoryControllerTest {

    public static final String BASE_URI = "http://localhost:8080/resources/stories";

    @Autowired
    private MongoOperations mongoOps;

    private SessionFilter sessionFilter = new SessionFilter();

    private RequestSpecification requestSpec;

    private Story story1 = new Story("test", "test");
    private Story story2 = new Story("test2", "test2");

    @Before
    public void setUp() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

        requestSpecBuilder.addFilter(sessionFilter).setContentType("application/json");

        requestSpec = requestSpecBuilder.build();

        mongoOps.save(story1);
        mongoOps.save(story2);

        logIn();
    }

    private void logIn() {
        given().spec(requestSpec).
                body(new Credentials("admin", "admin")).
                when().
                post(LoginControllerTest.BASE_URI + "/logIn").then().
                assertThat().
                statusCode(SC_OK);
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
                body("title", hasItems("test", "test2"));
    }

    @Test
    public void can_fetch_story() {

        given().
                spec(requestSpec).
                pathParam("storyId", story1.getId()).
                get(BASE_URI + "/{storyId}").
                then().
                assertThat().
                body("title", equalTo("test"));
    }

    @Test
    public void can_delete_story() {

        given().spec(requestSpec).
                pathParam("storyId", story1.getId()).
                delete(BASE_URI + "/{storyId}").
                then().
                assertThat().
                statusCode(SC_NO_CONTENT);

        given().spec(requestSpec).
                pathParam("storyId", story1.getId()).
                when().
                get(BASE_URI + "/{storyId}").
                then().
                assertThat().
                statusCode(SC_NOT_FOUND);
    }

    @Test
    public void can_create_story() {
        given().spec(requestSpec).
                body(new Story("test", "test")).
                when().
                post(BASE_URI).then().
                assertThat().
                header("Location", is(notNullValue())).
                statusCode(SC_CREATED);
    }

    @Test
    public void can_update_story() {
        Story story = given().
                spec(requestSpec).
                pathParam("storyId", story1.getId()).
                get(BASE_URI + "/{storyId}").as(Story.class);

        story.setTitle("updated");

        given().spec(requestSpec).
                body(story).
                when().
                put(BASE_URI).then().
                assertThat().
                statusCode(SC_OK);

        Story updated = given().
                spec(requestSpec).
                pathParam("storyId", story.getId()).
                get(BASE_URI + "/{storyId}").as(Story.class);

        assertThat(updated.getTitle(), is(equalTo(story.getTitle())));

    }

    @Test
    public void cannot_fetch_story_with_bad_credentials() {
        given().
                get(BASE_URI).
                then().
                assertThat().
                statusCode(SC_UNAUTHORIZED);

    }
}
