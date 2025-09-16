package br.com.fiap.backend_Softtek.controllers;

import br.com.fiap.backend_Softtek.Models.DailyProgressModel;
import br.com.fiap.backend_Softtek.service.ProgressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/progress")
public class ProgressController {


    private static final Logger logger = LoggerFactory.getLogger(ProgressController.class);


    @Autowired
    private ProgressService progressService;

    @PostMapping
    public ResponseEntity<DailyProgressModel> createProgress(@RequestBody DailyProgressModel progress, Principal principal) {
        String userId = principal.getName();
        logger.info("Tentativa de criar progresso para o usuário: {}", userId);
        progress.setUserName(userId);

        DailyProgressModel savedProgress = progressService.saveProgress(progress);

        return ResponseEntity.ok(savedProgress);
    }

    @GetMapping("/{userName}/{date}")
    public ResponseEntity<DailyProgressModel> getProgressByDate(@PathVariable String userName, @PathVariable String date) {
        logger.info("Buscando progresso para o usuário: {} na data: {}", userName, date);

        // CORREÇÃO: Chame o método findProgressByDate do Service
        DailyProgressModel progress = progressService.findProgressByDate(userName, date);

        if (progress != null) {
            return ResponseEntity.ok(progress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
