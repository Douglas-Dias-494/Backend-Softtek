package br.com.fiap.backend_Softtek.service;

import br.com.fiap.backend_Softtek.Models.MoodModel;
import br.com.fiap.backend_Softtek.Models.WeeklyResponseModel;
import br.com.fiap.backend_Softtek.dtos.MonthlyMoodResponse;
import br.com.fiap.backend_Softtek.repositories.MoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MoodService {
    @Autowired
    private MoodRepository moodRepository;

    public MoodModel saveOrUpdateMood(String userId, String mood) {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        Optional<MoodModel> existingMood = moodRepository.findByUserIdAndCreatedAtBetween(userId, startOfDay, endOfDay);

        if (existingMood.isPresent()) {
            // Se já existe, atualiza o humor
            MoodModel moodToUpdate = existingMood.get();
            moodToUpdate.setMood(mood);
            return moodRepository.save(moodToUpdate);
        } else {
            // Se não existe, cria um novo
            MoodModel newMood = new MoodModel();
            newMood.setMood(mood);
            newMood.setUserId(userId);
            // O campo createdAt será definido automaticamente no construtor
            return moodRepository.save(newMood);
        }
    }

    public List<WeeklyResponseModel> findWeeklyMoods() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        List<MoodModel> moods = moodRepository.findByCreatedAtAfter(sevenDaysAgo);

        // Mapeia os dados do banco para o modelo de resposta do frontend
        return moods.stream()
                .map(mood -> new WeeklyResponseModel(mood.getMood(), mood.getCreatedAt().toLocalDate()))
                .collect(Collectors.toList());
    }

    public Optional<MoodModel> findMoodForToday(String userId) {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);
        return moodRepository.findByUserIdAndCreatedAtBetween(userId, startOfDay, endOfDay);
    }

    /*ENDPOINT APENAS PARA TESTE !!!*/
    public void saveMoodWithDate(String userId, String mood, LocalDateTime createdAt) {
        MoodModel newMood = new MoodModel();
        newMood.setUserId(userId);
        newMood.setMood(mood);
        newMood.setCreatedAt(createdAt); // Define a data de criação
        moodRepository.save(newMood);
    }

    public List<MonthlyMoodResponse> getMonthlyMoods() {
        return moodRepository.findAll()
                .stream()
                .map(mood -> {
                    String formattedDate = Optional.ofNullable(mood.getCreatedAt())
                            .map(date -> date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                            .orElse(null);

                    return new MonthlyMoodResponse(formattedDate, mood.getMood());
                })
                .collect(Collectors.toList()); // Adicione esta linha
    }
}
