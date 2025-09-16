package br.com.fiap.backend_Softtek.service;

import br.com.fiap.backend_Softtek.Models.DailyProgressModel;
import br.com.fiap.backend_Softtek.repositories.DailyProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProgressService {

    @Autowired
    private DailyProgressRepository dailyProgressRepository;

    public DailyProgressModel saveProgress(DailyProgressModel progress) {
        // Lógica de upsert para verificar e atualizar um registro existente
        Optional<DailyProgressModel> existingProgress = dailyProgressRepository.findByUserNameAndDate(progress.getUserName(), progress.getDate());
        if (existingProgress.isPresent()) {
            DailyProgressModel updatedProgress = existingProgress.get();
            updatedProgress.setCompletedTasks(progress.getCompletedTasks());
            updatedProgress.setSequenceDays(progress.getSequenceDays());
            return dailyProgressRepository.save(updatedProgress);
        } else {
            return dailyProgressRepository.save(progress);
        }
    }

    // CORREÇÃO: Adicione este novo metodo
    public DailyProgressModel findProgressByDate(String userName, String date) {
        Optional<DailyProgressModel> progressOptional = dailyProgressRepository.findByUserNameAndDate(userName, date);
        return progressOptional.orElse(null);
    }

}
