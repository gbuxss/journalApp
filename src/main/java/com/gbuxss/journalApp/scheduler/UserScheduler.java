package com.gbuxss.journalApp.scheduler;

import com.gbuxss.journalApp.entity.JournalEntry;
import com.gbuxss.journalApp.entity.User;
import com.gbuxss.journalApp.enums.Sentiment;
import com.gbuxss.journalApp.repository.UserRepositoryImp;
import com.gbuxss.journalApp.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    private final UserRepositoryImp userRepositoryImp;
    private final EmailService emailService;

    public UserScheduler(UserRepositoryImp userRepositoryImp, EmailService emailService) {
        this.userRepositoryImp = userRepositoryImp;
        this.emailService = emailService;
    }


    @Scheduled(cron = "0 0 8 22 * *")
    public void fetchUserAndSendSAEmail() {
        List<User> users = userRepositoryImp.getUserForSA();
        for (User user : users) {
            List<JournalEntry> journalEntries = user.getUserJournal();
            List<Sentiment> sentiments = journalEntries.stream()
                    .filter(x -> x.getDate().isAfter(LocalDateTime.now().minusDays(7))).map(JournalEntry::getSentiment).collect(Collectors.toList());

            Map<Sentiment, Integer> sentimentCount = new HashMap<>();
            for (Sentiment sentiment : sentiments) {
                if (sentiment != null)
                    sentimentCount.put(sentiment, sentimentCount.getOrDefault(sentiment, 0) + 1);
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCount.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
                if (mostFrequentSentiment != null) {
                    emailService.sendEmailForSA(user.getEmail(), "Sentiment", mostFrequentSentiment.toString());
                }
            }
        }
    }
}
