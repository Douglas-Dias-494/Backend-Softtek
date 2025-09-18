package br.com.fiap.backend_Softtek.repositories;


import br.com.fiap.backend_Softtek.Models.MoodModel;
import br.com.fiap.backend_Softtek.Models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {
    Optional<UserModel> findByUsername(String username);

}
