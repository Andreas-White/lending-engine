package com.peerlender.lendingengine.application;

import com.peerlender.lendingengine.application.service.TokenValidationService;
import com.peerlender.lendingengine.domain.model.User;
import com.peerlender.lendingengine.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final TokenValidationService tokenValidationService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(TokenValidationService tokenValidationService, UserRepository userRepository) {
        this.tokenValidationService = tokenValidationService;
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/all")
    public List<User> findUsers(@RequestHeader String authorization) {
        tokenValidationService.validateTokenAndGetUser(authorization);
        return userRepository.findAll();
    }
}
