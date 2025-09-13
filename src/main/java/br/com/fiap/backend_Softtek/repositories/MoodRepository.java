package br.com.fiap.backend_Softtek.repositories;

import br.com.fiap.backend_Softtek.Models.MoodModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MoodRepository extends MongoRepository<MoodModel, String> {
    Optional<MoodModel> findByUserIdAndDate(String userId, LocalDate date);
}
