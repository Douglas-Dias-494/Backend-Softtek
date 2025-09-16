package br.com.fiap.backend_Softtek.Models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "questionnaires")
public class QuestionnaireModel {
    @Id
    private String id;
    private String userId;
    private List<Integer> answers;
    private int totalScore;
    private String resultMessage;

    public QuestionnaireModel() {

    }

    public QuestionnaireModel(String userId, List<Integer> answers, int totalScore, String resultMessage) {
        this.userId = userId;
        this.answers = answers;
        this.totalScore = totalScore;
        this.resultMessage = resultMessage;
    }


}
