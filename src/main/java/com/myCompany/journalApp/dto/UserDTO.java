package com.myCompany.journalApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Data
public class UserDTO {

    private String userName;
    private String email;
    private boolean SentimentalAnalysis;
    private String password;

}
