package br.com.fiap.backend_Softtek.controllers;


import br.com.fiap.backend_Softtek.Models.MoodModel;
import br.com.fiap.backend_Softtek.Models.WeeklyResponseModel;
import br.com.fiap.backend_Softtek.dtos.MonthlyMoodResponse;
import br.com.fiap.backend_Softtek.service.MoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @GetMapping("/daily/{userId}")
    public ResponseEntity<MoodModel> getDailyMood(@PathVariable String userId) {
        Optional<MoodModel> mood = moodService.findMoodForToday(userId);
        return mood.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/weekly")
    public ResponseEntity<List<WeeklyResponseModel>> getWeeklyMoods() {
        List<WeeklyResponseModel> weeklyMoods = moodService.findWeeklyMoods();
        return ResponseEntity.ok(weeklyMoods);
    }

    @GetMapping("/monthly")
    public List<MonthlyMoodResponse> getMonthlyMoods() {
        return moodService.getMonthlyMoods();
    }





    /*ENDPOINT APENAS PARA TESTES!!!*/
    @PostMapping("/test-save")
    public ResponseEntity<Void> testSaveMood(@RequestBody Map<String, String> payload, Principal principal) {
        String mood = payload.get("mood");
        String dateString = payload.get("createdAt");

        if (mood != null && dateString != null) {
            LocalDateTime createdAt = LocalDateTime.parse(dateString);
            moodService.saveMoodWithDate(principal.getName(), mood, createdAt);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
