package org.moab.integration;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.json.JSONObject;
import com.jayway.restassured.RestAssured;
import org.junit.runner.RunWith;
import org.moab.MoabApplication;
import org.moab.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MoabApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class AccountIntegrationTest {

    @Value("${local.server.port}")
    public int port;

    @Autowired
    AccountRepository accountRepository;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void createAccount() throws JSONException {
        String dobString = "1960-01-01";
        JSONObject json = new JSONObject();
        json.put("clientName", "Silver Surfer");
        json.put("clientID", "SilverSurferID");
        json.put("clientDoB", dobString);

        given().
                accept("application/json").
                contentType("application/json").
                body(json.toString()).
        when().
                post("/api/v1/account").
        then().
                statusCode(201).
                body("accountNumber", notNullValue()).
                body("clientDoB", is(equalTo(dobString)));


    }
}
