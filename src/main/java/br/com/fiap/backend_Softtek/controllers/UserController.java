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

//    @GetMapping("/{id}") // retorno do usuário escolhido [ talvez não tenha necessidade de ter esse endpoint... ]
//    public ResponseEntity<UserModel> getUserById(@PathVariable String id) {
//        return userRepository.findById(id)
//                .map(user -> {
//                    user.setUsername(user.getUsername());
//                    UserModel updatedUser = userRepository.save(user);
//                    return ResponseEntity.ok(updatedUser);
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }

    @PostMapping("/users") // endpoint que cadastra o nome digitado pelo usuário
    public AuthResponse authenticateUser(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");

        UserModel userModel = userService.findOrCreateUser(username);

        String jwt = jwtTokenProvider.generateToken(userModel.getId());

        return new AuthResponse(jwt, userModel.getId(), userModel.getUsername());
    }
}
