package br.com.fiap.backend_Softtek.service;

import br.com.fiap.backend_Softtek.Models.MoodModel;
import br.com.fiap.backend_Softtek.Models.WeeklyResponseModel;
import br.com.fiap.backend_Softtek.dtos.MonthlyMoodResponse;
import br.com.fiap.backend_Softtek.repositories.MoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MoodService {
    @Autowired
    private MoodRepository moodRepository;

    public MoodModel saveOrUpdateMood(String userId, String mood) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().plusDays(1).atStartOfDay();

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
            newMood.setCreatedAt(LocalDateTime.now()); // Garante que a data é sempre definida para novos humores
            return moodRepository.save(newMood);
        }
    }

    public List<WeeklyResponseModel> findWeeklyMoods(String userId) {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        List<MoodModel> moods = moodRepository.findByUserIdAndCreatedAtAfter(userId, sevenDaysAgo);

        // Filtra humores com data nula e mapeia os dados para o modelo de resposta
        return moods.stream()
                .filter(mood -> mood.getCreatedAt() != null) // Prevenção de NullPointerException
                .map(mood -> new WeeklyResponseModel(mood.getMood(), mood.getCreatedAt().toLocalDate()))
                .collect(Collectors.toList());
    }

    public Optional<MoodModel> findMoodForToday(String userId) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().plusDays(1).atStartOfDay();
        return moodRepository.findByUserIdAndCreatedAtBetween(userId, startOfDay, endOfDay);
    }

    /* ENDPOINT APENAS PARA TESTE! Permite a inserção de dados históricos. */
    public void saveMoodWithDate(String userId, String mood, LocalDateTime createdAt) {
        MoodModel newMood = new MoodModel();
        newMood.setUserId(userId);
        newMood.setMood(mood);
        newMood.setCreatedAt(createdAt); // Define a data de criação a partir da requisição
        moodRepository.save(newMood);
    }

    public List<MonthlyMoodResponse> getMonthlyMoods(String userId) {
        // 1. Fetch all moods for the user
        List<MoodModel> moods = moodRepository.findByUserId(userId);

        // 2. Filtra humores com data nula e agrupa por mês e ano
        Map<YearMonth, Map<String, Long>> moodsByMonth = moods.stream()
                .filter(mood -> mood.getCreatedAt() != null) // Prevenção de NullPointerException
                .collect(
                        Collectors.groupingBy(
                                mood -> YearMonth.from(mood.getCreatedAt()), // Agrupa por mês
                                Collectors.groupingBy(MoodModel::getMood, Collectors.counting()) // Conta os humores em cada mês
                        )
                );

        // 3. Converte os dados agrupados para o formato de resposta final
        return moodsByMonth.entrySet().stream()
                .map(entry -> {
                    YearMonth month = entry.getKey();
                    Map<String, Long> moodCounts = entry.getValue();

                    // Encontra o humor mais frequente
                    String mostFrequentMood = moodCounts.entrySet().stream()
                            .max(Map.Entry.comparingByValue())
                            .map(Map.Entry::getKey)
                            .orElse("Nenhum");

                    // Obtém a contagem do humor mais frequente
                    long mostFrequentCount = moodCounts.get(mostFrequentMood);

                    // Calcula o número total de humores no mês
                    long totalMoods = moodCounts.values().stream().mapToLong(Long::longValue).sum();

                    // Calcula a porcentagem
                    int percentage = (int) Math.round((double) mostFrequentCount / totalMoods * 100);

                    // Cria o objeto de resposta final
                    return new MonthlyMoodResponse(
                            month.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, new Locale("pt", "BR")),
                            mostFrequentMood,
                            percentage
                    );
                })
                .sorted(Comparator.comparing(MonthlyMoodResponse::getMonth))
                .collect(Collectors.toList());
    }

    public void saveAllMoods(List<MoodModel> moods) {
        moodRepository.saveAll(moods);
    }
}
