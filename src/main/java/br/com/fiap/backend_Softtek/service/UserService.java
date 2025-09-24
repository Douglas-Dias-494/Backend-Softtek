package br.com.fiap.backend_Softtek.service;

import br.com.fiap.backend_Softtek.Models.UserModel;
import br.com.fiap.backend_Softtek.Models.UserRegister;
import br.com.fiap.backend_Softtek.repositories.UserRepository;
import br.com.fiap.backend_Softtek.security.AuthResponse;
import br.com.fiap.backend_Softtek.utils.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user =userRepository.findByNickname(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(user.getId(), "", Collections.emptyList());
    }

    public UserDetails loadUserById(String id) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(user.getId(), "", Collections.emptyList());
    }

    public AuthResponse register(UserRegister userRegister) {
        UserModel user = new UserModel(userRegister.getNickname(), userRegister.getPassword());
        user = userRepository.save(user);

        String jwt = jwtTokenProvider.generateToken(user.getId());
        return new AuthResponse(jwt, user.getId(), user.getNickname());
    }

    public AuthResponse login(String nickname, String password) {
        Optional<UserModel> optionalUser = userRepository.findByNickname(nickname);
        if (optionalUser.isPresent()) {
            UserModel user = optionalUser.get();
            String jwt = jwtTokenProvider.generateToken(user.getId());
            return new AuthResponse(jwt, user.getId(), user.getNickname());
        }
        return null;
    }

    public UserModel findByNickname(String nickname) {
        return userRepository.findByNickname(nickname).orElse(null);
    }

    public boolean checkNicknameExists(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }
}
