package com.myCompany.journalApp.entity;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.print.attribute.standard.JobHoldUntil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @NonNull
    private String userName;

    private String email;

    private boolean SentimentalAnalysis;

    @NonNull
    private String password;

    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();

    private List<String> roles = new ArrayList<>();
}
