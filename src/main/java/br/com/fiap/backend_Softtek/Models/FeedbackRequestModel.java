package br.com.fiap.backend_Softtek.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackRequestModel {
    private String subject;
    private String description;
}
