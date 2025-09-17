package br.com.fiap.backend_Softtek.service;

import br.com.fiap.backend_Softtek.Models.UserModel;
import br.com.fiap.backend_Softtek.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserModel findOrCreateUser(String username) {
        return userRepository.findByUsername(username)
                .orElseGet(() -> {
                    //Se o user não existir, cria um novo
                    UserModel user = new UserModel();
                    user.setUsername(username);
                    return userRepository.save(user);
                });
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user =userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(user.getId(), "", Collections.emptyList());
    }

    public UserDetails loadUserById(String id) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(user.getId(), "", Collections.emptyList());
    }
}
