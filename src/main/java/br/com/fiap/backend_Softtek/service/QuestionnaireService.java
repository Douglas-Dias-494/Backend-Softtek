package br.com.fiap.backend_Softtek.service;

import br.com.fiap.backend_Softtek.Models.QuestionnaireModel;
import br.com.fiap.backend_Softtek.repositories.QuestionnaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionnaireService {

    private final QuestionnaireRepository questionnaireRepository;

    @Autowired
    public QuestionnaireService(QuestionnaireRepository questionnaireRepository) {
        this.questionnaireRepository = questionnaireRepository;
    }

    public QuestionnaireModel saveQuestionnaire(QuestionnaireModel questionnaire) {
        String resultMessage = generateResultMessage(questionnaire.getTotalScore());
        questionnaire.setResultMessage(resultMessage);

        return questionnaireRepository.save(questionnaire);
    }

    private String generateResultMessage(int totalScore) {
        if (totalScore >= 10 && totalScore <= 20) {
            return "Parece que você não tem tido animação nos últimos dias. Lembre-se que o cuidado com a sua mente é essencial, procure ajuda médica.";
        } else if (totalScore >= 21 && totalScore <= 30) {
            return "Sua pontuação indica um nível moderado de atenção à saúde mental. É importante ficar atento aos sinais do seu corpo e mente para evitar que o quadro evolua. Se precisar de apoio, não hesite em procurar ajuda profissional.";
        } else if (totalScore >= 31 && totalScore <= 40) {
            return "Sua pontuação está dentro de um estado razoável de bem-estar. Mantenha os bons hábitos e continue atento às suas emoções para preservar sua saúde mental.";
        } else { // totalScore > 40
            return "Parabéns! Sua pontuação mostra que você está com a saúde mental em dia. Continue com os bons hábitos e a atenção às suas emoções. Você está no caminho certo!";
        }
    }
}