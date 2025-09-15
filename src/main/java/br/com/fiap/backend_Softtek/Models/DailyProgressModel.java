package br.com.fiap.backend_Softtek.Models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "daily_progress")
public class DailyProgressModel {

    @Id
    private String id;

    @Field("date")
    private String date;

    @Field("user_name")
    private String userName;

    @Field("completed_tasks")
    private Integer completedTasks;

    @Field("sequence_days")
    private Integer sequenceDays;

    public DailyProgressModel() {
    }
}
