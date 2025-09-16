package br.com.fiap.backend_Softtek.Models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "feedbacks")
public class FeedbackModel {

    @Id
    private String id;
    private String userId; // Associado ao usuário, mas pode ser anônimo no front
    private String subject;
    private String description;
    private LocalDateTime createdAt;

    public FeedbackModel() {
        this.createdAt = LocalDateTime.now();
    }
}
