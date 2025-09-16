package br.com.fiap.backend_Softtek.controllers;

import br.com.fiap.backend_Softtek.Models.QuestionnaireModel;
import br.com.fiap.backend_Softtek.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questionnaries")
public class QuestionnaireController {

    private final QuestionnaireService questionnaireService;

    @Autowired
    public QuestionnaireController(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionnaireModel submitQuestionnaire(@RequestBody QuestionnaireModel questionnaire) {
        questionnaireService.saveQuestionnaire(questionnaire);
        return questionnaireService.saveQuestionnaire(questionnaire);
    }
}