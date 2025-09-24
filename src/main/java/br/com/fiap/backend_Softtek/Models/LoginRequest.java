package br.com.fiap.backend_Softtek.Models;


import lombok.Data;

@Data
public class LoginRequest {
    private String nickname;
    private String password;
}
