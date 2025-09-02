package com.myCompany.journalApp.repository;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

class UserRepositoryImplTests {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Test
    void TestingMethod(){
        Assertions.assertNotNull(userRepository.getAllUserSA());
    }

}
