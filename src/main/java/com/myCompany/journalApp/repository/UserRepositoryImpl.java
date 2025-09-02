package com.myCompany.journalApp.repository;

import com.myCompany.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getAllUserSA(){
        Query query = new Query();
        query.addCriteria(Criteria.where("email")
                .regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"));


//        query.addCriteria(Criteria.where("email").exists(true));
//        query.addCriteria(Criteria.where("email").ne(null).ne(""));


        query.addCriteria(Criteria.where("SentimentalAnalysis").is(true));
        List<User> users = mongoTemplate.find(query,User.class);
        return users;
    }
}
