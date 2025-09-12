package br.com.fiap.backend_Softtek.controllers;


import br.com.fiap.backend_Softtek.Models.UserModel;
import br.com.fiap.backend_Softtek.repositories.UserRepository;
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

    @GetMapping// retorno geral de todos os usuários
    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}") // retorno do usuário escolhido [ talvez não tenha necessidade de ter esse endpoint... ]
    public ResponseEntity<UserModel> getUserById(@PathVariable String id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(user.getUsername());
                    UserModel updatedUser = userRepository.save(user);
                    return ResponseEntity.ok(updatedUser);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/login-auth") // endpoint que cadastra o nome digitado pelo usuário
    public String loginUser(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");

        // Verifique se o usuário existe, se não, crie um novo registro

        return jwtTokenProvider.generateToken(username);
    }


}
