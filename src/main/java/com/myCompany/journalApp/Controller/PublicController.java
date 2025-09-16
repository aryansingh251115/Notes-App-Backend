package com.myCompany.journalApp.Controller;

import com.myCompany.journalApp.entity.User;
import com.myCompany.journalApp.service.UserDetailsServiceImpl;
import com.myCompany.journalApp.service.UserService;
import com.myCompany.journalApp.utils.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/public")
@Slf4j
@Tag(name = "Public Controller" , description = "SignUp, Login and Health-check methods.")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public void signup(@RequestBody User user) {
        userService.saveNewUser(user);
    }



    @GetMapping("health-check")
    public ResponseEntity<?> healthCheck(){
        return new ResponseEntity<>("Application is running, Catch it!!" , HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred while createUserAuthenticationToken" , e);
            return new ResponseEntity<>("Incorrect userName or Password" , HttpStatus.BAD_REQUEST);
        }

    }
}
