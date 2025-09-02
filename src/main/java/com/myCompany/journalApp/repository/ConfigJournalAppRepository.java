package com.myCompany.journalApp.repository;

import com.myCompany.journalApp.entity.ConfigJournalAppEntity;
import com.myCompany.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

}