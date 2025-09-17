package br.com.fiap.backend_Softtek.dtos;

import lombok.Data;

@Data
public class MonthlyMoodResponse {
    private String date;
    private String mood;

    // Construtor, Getters e Setters
    public MonthlyMoodResponse(String date, String mood) {
        this.date = date;
        this.mood = mood;
    }
}