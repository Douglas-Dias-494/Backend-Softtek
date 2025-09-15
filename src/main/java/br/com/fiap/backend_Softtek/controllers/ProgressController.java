package br.com.fiap.backend_Softtek.controllers;

import br.com.fiap.backend_Softtek.Models.DailyProgressModel;
import br.com.fiap.backend_Softtek.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

@RestController
@RequestMapping("/progress")
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    @PostMapping
    public ResponseEntity<DailyProgressModel> createProgress(@RequestBody DailyProgressModel progress, Principal principal) {
        String userId = principal.getName();
        progress.setUserName(userId);

        DailyProgressModel savedProgress = progressService.saveProgress(progress);

        return ResponseEntity.ok(savedProgress);
    }
}
