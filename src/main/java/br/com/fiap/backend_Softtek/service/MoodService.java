package br.com.fiap.backend_Softtek.service;

import br.com.fiap.backend_Softtek.Models.MoodModel;
import br.com.fiap.backend_Softtek.repositories.MoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class MoodService {
    @Autowired
    private MoodRepository moodRepository;

    public MoodModel saveOrUpdateMood(String userId, String mood) {
        LocalDate today = LocalDate.now();

        Optional<MoodModel> existingMood = moodRepository.findByUserIdAndDate(userId, today);

        if (existingMood.isPresent()) {
            MoodModel moodtoUpdate = existingMood.get();
            moodtoUpdate.setMood(mood);
            return moodRepository.save(moodtoUpdate);
        } else {
            MoodModel newMood = new MoodModel(mood, today, userId);
            return moodRepository.save(newMood);
        }
    }

    public Optional<MoodModel> findByUserIdAndDate(String userId, LocalDate date) {
        return moodRepository.findByUserIdAndDate(userId, date);
    }

}
