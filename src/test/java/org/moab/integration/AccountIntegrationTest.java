package org.moab.integration;

import com.jayway.restassured.RestAssured;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moab.MoabApplication;
import org.moab.events.AccountCreated;
import org.moab.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDate;

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
                body("accountNumber", nullValue()).
                body("clientDoB", is(equalTo(dobString)));


    }

    @Test
    public void showAccount() throws JSONException {
        String clientName = "Jason";
        String clientID = "N39482";
        String accountNumber = "123";
        createTestAccount(clientName, clientID, LocalDate.now(), accountNumber);

        given().
                accept("application/json").
        when().
                get("/api/v1/account/" + accountNumber).
        then().
                statusCode(200).
                body("accountNumber", is(equalTo(accountNumber))).
                body("clientName", is(equalTo(clientName))).
                body("balance", is(equalTo(0)));
    }



    @Test
    public void failToShowNonExistentAccount() throws JSONException {
        String clientName = "Jason";
        String clientID = "N39482";
        String badAccountNumber = "231";
        String accountNumber = "123";
        createTestAccount(clientName, clientID, LocalDate.now(), accountNumber);

        given().
                accept("application/json").
        when().
                get("/api/v1/account/" + badAccountNumber).
        then().
                statusCode(404);

    }

    private void createTestAccount(String clientName, String clientID, LocalDate dob, String accountNumber) {
        AccountCreated account = new AccountCreated(clientName, clientID, LocalDate.now());
        account.setAccountNumber(accountNumber);
        accountRepository.add(account);
    }

}
