package br.com.fiap.backend_Softtek.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "moods")
public class MoodModel {
    @Id
    private String id;
    private String mood;
    private String userId;
    private LocalDateTime createdAt;

}
