package com.peerlender.lendingengine.application;

import com.peerlender.lendingengine.application.service.TokenValidationService;
import com.peerlender.lendingengine.application.service.TokenValidationServiceImpl;
import com.peerlender.lendingengine.domain.model.User;
import com.peerlender.lendingengine.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final TokenValidationService tokenValidationService;
    private final UserService userService;

    @Autowired
    public UserController(TokenValidationService tokenValidationService, UserService userService) {
        this.tokenValidationService = tokenValidationService;
        this.userService = userService;
    }

    @GetMapping(value = "/all")
    public List<User> findUsers(@RequestHeader String authorization) {
        tokenValidationService.validateTokenAndGetUser(authorization);
        return userService.getAllUsers();
    }
}
