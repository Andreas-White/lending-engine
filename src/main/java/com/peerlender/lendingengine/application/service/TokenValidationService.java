package com.peerlender.lendingengine.application.service;

import com.peerlender.lendingengine.domain.exception.UserNotFoundException;
import com.peerlender.lendingengine.domain.model.User;
import com.peerlender.lendingengine.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenValidationService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String securityContextBaseUrl;

    @Autowired
    public TokenValidationService(final UserRepository userRepository,
                                  @Value("${security.baseurl}") final String securityContextBaseUrl) {
        this.userRepository = userRepository;
        this.securityContextBaseUrl = securityContextBaseUrl;
    }

    public User validateTokenAndGetUser(final String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate
                .exchange(securityContextBaseUrl+"/user/validate", HttpMethod.POST, httpEntity, String.class);

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return userRepository.findById(responseEntity.getBody())
                    .orElseThrow(() -> new UserNotFoundException(responseEntity.getBody()));
        }
        else
            throw new RuntimeException("Invalid Token");
    }
}
