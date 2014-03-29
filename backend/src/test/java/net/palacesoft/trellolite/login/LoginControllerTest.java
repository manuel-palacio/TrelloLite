package net.palacesoft.trellolite.login;


import com.jayway.restassured.filter.session.SessionFilter;
import net.palacesoft.trellolite.Application;
import net.palacesoft.trellolite.config.MongoConfigurationTest;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, MongoConfigurationTest.class})
@IntegrationTest
@WebAppConfiguration
@ActiveProfiles("test")
public class LoginControllerTest {

    public static final String BASE_URI = "http://localhost:8080/auth";

    private SessionFilter sessionFilter = new SessionFilter();


    @Before
    public void setUp() {
        given().filter(sessionFilter).
                contentType("application/json").
                body(new Credentials("admin", "admin")).
                when().
                post(BASE_URI + "/logIn").then().
                assertThat().
                statusCode(HttpStatus.SC_OK);
    }


    @Test
    public void is_logged_in() {
        given().filter(sessionFilter).
                contentType("application/json").
                when().
                get(BASE_URI + "/loggedIn").then().
                assertThat().
                body(is(equalTo("true")));

    }

    @Test
    public void cannot_log_in_with_bad_credentials() {
        given().
                contentType("application/json").
                body(new Credentials("", "")).
                when().
                post(BASE_URI + "/logIn").then().
                assertThat().
                statusCode(HttpStatus.SC_UNAUTHORIZED);

    }
}
