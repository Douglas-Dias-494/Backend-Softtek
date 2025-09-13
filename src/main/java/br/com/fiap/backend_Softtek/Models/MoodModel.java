package br.com.fiap.backend_Softtek.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "diary_moods")
public class MoodModel {
    @Id
    private String id;
    private String mood;
    private LocalDate date;
    private String userId;

    public MoodModel(String mood, LocalDate date, String userId) {
        this.mood = mood;
        this.date = date;
        this.userId = userId;
    }

}
