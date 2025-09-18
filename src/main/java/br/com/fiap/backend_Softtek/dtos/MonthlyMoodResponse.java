package br.com.fiap.backend_Softtek.dtos;

import lombok.Data;

@Data
public class MonthlyMoodResponse {
    private String month;
    private String mood;
    private double percentage;

    // Construtor
    public MonthlyMoodResponse(String month, String mood, double percentage) {
        this.month = month;
        this.mood = mood;
        this.percentage = percentage;
    }
}