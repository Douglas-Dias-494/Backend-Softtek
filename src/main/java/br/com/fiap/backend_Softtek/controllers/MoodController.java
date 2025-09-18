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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/moods")
public class MoodController {
    @Autowired
    private MoodService moodService;

    // Endpoint para SALVAR um humor.
    // Ele não precisa de ID na URL. O ID é pego do usuário autenticado.
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

    @GetMapping("/monthly")
    public List<MonthlyMoodResponse> getMonthlyMoods(Principal principal) {
        String userId = principal.getName();
        return moodService.getMonthlyMoods(userId);
    }

    @GetMapping("/weekly")
    public ResponseEntity<List<WeeklyResponseModel>> getWeeklyMoods(Principal principal) {
        String userId = principal.getName();
        List<WeeklyResponseModel> weeklyMoods = moodService.findWeeklyMoods(userId);
        return ResponseEntity.ok(weeklyMoods);
    }

    // Endpoint para buscar o humor do DIA.
    // Também usa o ID do usuário autenticado.
    @GetMapping("/daily")
    public ResponseEntity<MoodModel> getDailyMood(Principal principal) {
        String userId = principal.getName();
        Optional<MoodModel> mood = moodService.findMoodForToday(userId);
        return mood.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Você pode manter este endpoint se ele for usado por administradores para
    // buscar dados de um usuário específico, mas ele não deve ser usado pelo seu app
    // de forma genérica.
    @GetMapping("/monthly/{userId}")
    public List<MonthlyMoodResponse> getMonthlyMoodsByUserId(@PathVariable String userId) {
        return moodService.getMonthlyMoods(userId);
    }

    /*ENDPOINT APENAS PARA TESTES!!!*/
    @PostMapping("/test-data")
    public ResponseEntity<Void> addTestMoods(@RequestBody List<MoodModel> moods, Principal principal) {
        String userId = principal.getName();
        if (userId == null) {
            return ResponseEntity.badRequest().build();
        }

        // Garante que todos os humores sejam associados ao usuário logado
        moods.forEach(mood -> mood.setUserId(userId));
        moodService.saveAllMoods(moods);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
