package br.com.fiap.backend_Softtek.Models;

import lombok.Data;

@Data
public class TaskStatusModel {
    private String name;
    private String description;
    private boolean isCompleted;

    public TaskStatusModel(String name, String description, boolean isCompleted) {
        this.name = name;
        this.description = description;
        this.isCompleted = isCompleted;
    }
}
