package com.myCompany.journalApp.schedulers;

import com.myCompany.journalApp.cache.AppCache;
import com.myCompany.journalApp.entity.JournalEntry;
import com.myCompany.journalApp.entity.User;
import com.myCompany.journalApp.enums.Sentiment;
import com.myCompany.journalApp.model.SentimentData;
import com.myCompany.journalApp.repository.UserRepositoryImpl;
import com.myCompany.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
@RequestMapping("/temp")
public class UserScheduler {

    @Autowired
    private EmailService emailService;   //for sending mail to eligible users.

    @Autowired
    private UserRepositoryImpl userRepository;    //for getting eligible users to send mail.

    @Autowired
    private AppCache appCache;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;


    //    @Scheduled(cron = "0 0 9 * * SUN")
//    @Scheduled(cron = "0 * * ? * *")
//    @Scheduled(cron = "*/1 * * * * *") // run every second


    @GetMapping()
    @ResponseBody
    public void fetchAndSendSAMail() {
        try {
            List<User> users = userRepository.getAllUserSA();
            if (users == null || users.isEmpty()) {
                System.out.println("No users found to process.");
                return;
            }

            for (User user : users) {
                List<JournalEntry> journalEntries = user.getJournalEntries();
                if (journalEntries == null) {
                    System.out.println("No journal entries for user: " + user.getEmail());
                    continue;
                }

                List<Sentiment> sentiments = journalEntries.stream()
                        .filter(x -> x.getDate()
                                .isAfter(LocalDateTime.now()
                                        .minus(7, ChronoUnit.DAYS)))
                        .map(JournalEntry::getSentiment)
                        .collect(Collectors.toList());

                Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
                for (Sentiment sentiment : sentiments) {
                    if (sentiment != null) {
                        sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
                    }
                }

                Sentiment mostFrequentSentiment = null;
                int maxCount = 0;
                for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                    if (entry.getValue() > maxCount) {
                        maxCount = entry.getValue();
                        mostFrequentSentiment = entry.getKey();
                    }
                }

                if (mostFrequentSentiment != null) {
                    SentimentData sentimentData = SentimentData.builder()
                            .email(user.getEmail())
                            .sentiment("Sentiment for last 7 days are : " + mostFrequentSentiment)
                            .build();

                    try {
                        kafkaTemplate.send("weekly-sentiment", sentimentData.getEmail(), sentimentData)
                                .addCallback(
                                        success -> System.out.println("Message sent: " + sentimentData.getEmail()),
                                        failure -> System.out.println("Message failed: " + failure.getMessage())
                                );
                    } catch (Exception e) {
                        System.err.println("Kafka send failed for user: " + user.getEmail());
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Error in fetchAndSendSAMail:");
            e.printStackTrace();
        }
    }


    @Scheduled(cron = "*/5 * * * * *")               // ✅ 6 fields (seconds + minute + hour + day + month + weekday)
    public void clearAppCache() {
        appCache.init();
    }

}
