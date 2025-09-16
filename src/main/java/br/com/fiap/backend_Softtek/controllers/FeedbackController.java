package br.com.fiap.backend_Softtek.controllers;

import br.com.fiap.backend_Softtek.Models.FeedbackRequestModel;
import br.com.fiap.backend_Softtek.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public ResponseEntity<Void> submitFeedback(@RequestBody FeedbackRequestModel feedbackRequest, Principal principal) {
        feedbackService.saveFeedback(feedbackRequest, principal.getName());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
