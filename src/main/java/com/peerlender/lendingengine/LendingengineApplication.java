package com.peerlender.lendingengine;

import com.peerlender.lendingengine.domain.model.Balance;
import com.peerlender.lendingengine.domain.model.User;
import com.peerlender.lendingengine.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LendingengineApplication implements CommandLineRunner {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(LendingengineApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.save(new User("John","John","Smith",27,"Plumber", new Balance()));
        userRepository.save(new User("Anna","Anna","Brown",53,"Designer", new Balance()));
        userRepository.save(new User("Larry","Larry","Blur",35,"Developer", new Balance()));
        userRepository.save(new User("Kelly","Kelly","Chapel",19,"Student", new Balance()));
    }
}
