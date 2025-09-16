package br.com.fiap.backend_Softtek.service;


import br.com.fiap.backend_Softtek.Models.FeedbackModel;
import br.com.fiap.backend_Softtek.Models.FeedbackRequestModel;
import br.com.fiap.backend_Softtek.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public void saveFeedback(FeedbackRequestModel feedbackRequest, String userId) {
        FeedbackModel feedback = new FeedbackModel();

        feedback.setUserId(userId);
        feedback.setSubject(feedbackRequest.getSubject());
        feedback.setDescription(feedbackRequest.getDescription());
        feedback.setCreatedAt(LocalDateTime.now());

        feedbackRepository.save(feedback);
    }
}
