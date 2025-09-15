package br.com.fiap.backend_Softtek.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;

@Data
@Document(collection = "tasks")
public class TaskModel {

    @Id
    private String id;

    @Field("userId")
    private String userId;

    @Field("date")
    String date;

    @Field("taskName")
    private String taskName;

    @Field("isDone")
    private Boolean isDone;

    @Field("status")
    private String status; // Campo 'status' adicionado

}


