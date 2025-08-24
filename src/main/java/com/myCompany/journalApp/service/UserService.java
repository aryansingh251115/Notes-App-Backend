package com.myCompany.journalApp.service;

import com.myCompany.journalApp.entity.User;
import com.myCompany.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


//Flow of program

//controller <-- service <-- Mongodb repository
@Component
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean saveNewUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("Users"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            log.error("Error occurred for {}", user.getUserName());
            log.warn("logging");
            log.info("logging");
            log.trace("logging");
            log.debug("logging");
            return false;
        }

    }

    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("Users", "ADMIN"));
        userRepository.save(user);

    }


    public void saveEntry(User user) {
        user.setRoles(Arrays.asList("Users"));
        userRepository.save(user);

    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId myId) {
        return userRepository.findById(myId);
    }

    public void delById(ObjectId myId) {
        userRepository.deleteById(myId);
    }

    public User findByUserName(String username) {
        return userRepository.findByUserName(username);
    }
}
