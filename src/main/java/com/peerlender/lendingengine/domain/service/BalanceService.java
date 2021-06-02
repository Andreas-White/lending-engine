package com.peerlender.lendingengine.domain.service;

import com.peerlender.lendingengine.domain.exception.UserNotFoundException;
import com.peerlender.lendingengine.domain.model.Money;
import com.peerlender.lendingengine.domain.model.User;
import com.peerlender.lendingengine.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BalanceService {

    private final UserRepository userRepository;

    @Autowired
    public BalanceService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional // Make sure that the database is modified only if there are no exceptions thrown
    public void topUpBalance(final Money money, String authToken) {
        User user = getUser(authToken);
        user.topUp(money);
    }

    @Transactional // Make sure that the database is modified only if there are no exceptions thrown
    public void withdrawBalance(final Money money, String authToken) {
        User user = getUser(authToken);
        user.withdraw(money);
    }

    private User getUser(String authToken) {
        return userRepository.findById(authToken).orElseThrow(() -> new UserNotFoundException(authToken));
    }
}
