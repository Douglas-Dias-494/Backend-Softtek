package br.com.fiap.backend_Softtek.controllers;


import br.com.fiap.backend_Softtek.Models.UserModel;
import br.com.fiap.backend_Softtek.repositories.UserRepository;
import br.com.fiap.backend_Softtek.security.AuthResponse;
import br.com.fiap.backend_Softtek.service.UserService;
import br.com.fiap.backend_Softtek.utils.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/softtek-users")
public class UserController {

    // criando os endpoints de usuário da aplicação
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @GetMapping// retorno geral de todos os usuários
    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/check-nickname/{nickname}")
    public ResponseEntity<Boolean> checkNickname(@PathVariable String nickname) {
        boolean exists = userService.checkNicknameExists(nickname);
        return ResponseEntity.ok(exists);
    }
}
