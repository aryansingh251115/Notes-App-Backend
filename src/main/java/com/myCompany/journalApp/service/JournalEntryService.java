package com.myCompany.journalApp.service;

import com.myCompany.journalApp.entity.JournalEntry;
import com.myCompany.journalApp.entity.User;
import com.myCompany.journalApp.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;


//Flow of program

//controller <-- service <-- Mongodb repository
@Component
@Slf4j
public class JournalEntryService {



    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;


    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        try {
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveEntry(user);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error has been occurred.", e);
        }


    }


    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId myId) {
        return journalEntryRepository.findById(myId);
    }

    @Transactional
    public boolean delById(ObjectId myId, String userName) {
        boolean removed = false;
        try {

            User user = userService.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(myId));
            if (removed) {
                userService.saveEntry(user);
                journalEntryRepository.deleteById(myId);
            }
        }
        catch(Exception e ){
            log.error("Error Occurred while using delById");
            throw new RuntimeException("An error occurred while deleting " , e);
        }
        return removed;
    }
}
