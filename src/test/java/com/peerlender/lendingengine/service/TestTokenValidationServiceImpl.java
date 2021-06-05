package com.peerlender.lendingengine.service;

import com.peerlender.lendingengine.application.service.TokenValidationService;
import com.peerlender.lendingengine.domain.exception.UserNotFoundException;
import com.peerlender.lendingengine.domain.model.User;
import com.peerlender.lendingengine.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("test")
@Primary
@Service
public class TestTokenValidationServiceImpl implements TokenValidationService {

    private final UserRepository userRepository;

    @Autowired
    public TestTokenValidationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User validateTokenAndGetUser(String token) {
        return userRepository.findById(token).orElseThrow(() -> new UserNotFoundException(token));
    }
}
