package br.com.fiap.backend_Softtek.service;

import br.com.fiap.backend_Softtek.Models.DailyProgressModel;
import br.com.fiap.backend_Softtek.repositories.DailyProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgressService {

    @Autowired
    private DailyProgressRepository dailyProgressRepository;

    public DailyProgressModel saveProgress(DailyProgressModel progress) {
        // Implemente a lógica para salvar no seu banco de dados
        // Por exemplo:
         return dailyProgressRepository.save(progress);

    }
}
