package com.myCompany.journalApp.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.myCompany.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import com.myCompany.journalApp.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.mockito.Mockito.when;


public class UserDetailsServiceImplTests {


    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Disabled
    @Test
    void loadUserByUsernameTest(){
        when(userRepository.findByUserName(ArgumentMatchers.anyString()))
                .thenReturn(User.builder().userName("shyam").password("adawedw").roles(List.of("USER")).build());
        UserDetails user = userDetailsService.loadUserByUsername("shyam");
        Assertions.assertNotNull(user);
    }
}




































