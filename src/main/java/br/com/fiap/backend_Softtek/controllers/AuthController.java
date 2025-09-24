package br.com.fiap.backend_Softtek.controllers;

import br.com.fiap.backend_Softtek.Models.LoginRequest;
import br.com.fiap.backend_Softtek.Models.UserModel;
import br.com.fiap.backend_Softtek.Models.UserRegister;
import br.com.fiap.backend_Softtek.security.AuthResponse;
import br.com.fiap.backend_Softtek.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody UserRegister userRegister) {
        if (userService.findByNickname(userRegister.getNickname()) != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Retorna um erro se o usuário já existe
        }

        // Hashing da senha antes de salvar
        String hashedPassword = passwordEncoder.encode(userRegister.getPassword());
        UserRegister newUser = new UserRegister(userRegister.getNickname(), hashedPassword);

        AuthResponse authResponse = userService.register(newUser);
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Encontra o usuário pelo nickname
        UserModel user = userService.findByNickname(loginRequest.getNickname());

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Usuário não encontrado
        }

        // Compara a senha digitada com a senha hasheada no banco
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Senha incorreta
        }

        // Gera o token e retorna o sucesso
        AuthResponse authResponse = userService.login(user.getNickname(), user.getPassword());
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
}
