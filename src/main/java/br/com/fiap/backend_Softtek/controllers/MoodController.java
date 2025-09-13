package br.com.fiap.backend_Softtek.controllers;


import br.com.fiap.backend_Softtek.Models.MoodModel;
import br.com.fiap.backend_Softtek.service.MoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/moods")
public class MoodController {
    @Autowired
    private MoodService moodService;

    @PostMapping
    public ResponseEntity<MoodModel> saveMood(@RequestBody Map<String, String> payload, Principal principal) {
        String userId = principal.getName();
        String mood = payload.get("mood");

        if (mood == null || mood.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        MoodModel savedMood = moodService.saveOrUpdateMood(userId, mood);
        return ResponseEntity.ok(savedMood);
    }
}
