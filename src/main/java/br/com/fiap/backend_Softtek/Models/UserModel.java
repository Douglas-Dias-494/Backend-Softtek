package br.com.fiap.backend_Softtek.Models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;


@Data
@Document(collection = "users")
public class UserModel {
    @Id
    private String id;
    private String nickname;
    private String password;

    public UserModel(){

    }

    public UserModel(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }
}
