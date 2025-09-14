package com.myCompany.journalApp.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder


public class SentimentData {
    private String email;
    private String sentiment;

}
