package br.com.fiap.backend_Softtek.service;

import br.com.fiap.backend_Softtek.Models.UserModel;
import br.com.fiap.backend_Softtek.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserModel findOrCreateUser(String username) {
        return userRepository.findByUsername(username)
                .orElseGet(() -> {
                    UserModel newUser = new UserModel(username);
                    return userRepository.save(newUser);
                });

    }
}
