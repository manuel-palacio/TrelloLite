package net.palacesoft.trellolite.stories;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationTest.class)
@IntegrationTest
@WebAppConfiguration
public class StoryControllerTest {

    private static final String BASE_URI = "http://localhost:8080/stories";

    private Story story = new Story("test", "test");

    private String location;

    @Autowired
    MongoOperations mongoOps;


    @Before
    public void setUp() {
        location = given().
                contentType("application/json").
                body(story).
                when().
                post(BASE_URI).getHeader("Location");

        assertThat(mongoOps.findById(StringUtils.substringAfterLast(location, "/"), Story.class), is(notNullValue()));
    }


    @After
    public void cleanUp() {
        when().
                delete(location).
                then().
                assertThat().
                statusCode(HttpStatus.SC_NO_CONTENT);

        when().
                get(location).
                then().
                assertThat().
                statusCode(HttpStatus.SC_NOT_FOUND);

        assertThat(mongoOps.findById(StringUtils.substringAfterLast(location, "/"), Story.class), is(nullValue()));

    }

    @Test
    public void can_find_stories() {

        when().
                get(BASE_URI).
                then().
                assertThat().
                body("name", hasItems("test"));
    }
}
