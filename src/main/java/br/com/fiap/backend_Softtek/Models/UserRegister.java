package br.com.fiap.backend_Softtek.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserRegister {

    private String nickname;
    private String password;

    // Construtor para facilitar a criação de objetos
    public UserRegister(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }
}
