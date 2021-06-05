package com.peerlender.lendingengine;

import com.google.gson.Gson;
import com.peerlender.lendingengine.application.model.LoanRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles(profiles = "test")
public class LoanIntegrationTest {

    private static final String JOHN = "John";
    private static final Gson GSON = new Gson();

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int serverPort;

    @Test
    public void testLoanApplicationCreation() throws Exception {
        final String baseURL = "http://localhost:"+ serverPort +"/loan";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, JOHN);

        HttpEntity<LoanRequest> loanRequestHttpEntity = new HttpEntity<>(
                new LoanRequest(50,10,3), httpHeaders);

        testRestTemplate.postForEntity(baseURL + "/request",loanRequestHttpEntity, String.class);

    }
}
