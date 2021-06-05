package com.peerlender.lendingengine;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.peerlender.lendingengine.application.model.LoanRequest;
import com.peerlender.lendingengine.model.LoanApplicationDTO;
import com.sun.istack.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        HttpHeaders httpHeaders = getHttpHeaders();

        HttpEntity<LoanRequest> loanRequestHttpEntity = new HttpEntity<>(
                new LoanRequest(50,10,3), httpHeaders);

        testRestTemplate.postForEntity(baseURL + "/request",loanRequestHttpEntity, String.class);

        ResponseEntity<String> responseEntity = testRestTemplate
                .exchange(baseURL + "/requests", HttpMethod.GET,
                        new HttpEntity(null, getHttpHeaders()),String.class);

        List<LoanApplicationDTO> loanApplicationDTOS = GSON.fromJson(responseEntity.getBody(),
                new TypeToken<List<LoanApplicationDTO>>(){}.getType());

        assert loanApplicationDTOS != null;
        assertEquals(1,loanApplicationDTOS.size());
        assertEquals(loanApplicationDTOS.get(0).getBorrower().getUsername(), JOHN);
    }

    @NotNull
    private HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, JOHN);
        return httpHeaders;
    }
}
