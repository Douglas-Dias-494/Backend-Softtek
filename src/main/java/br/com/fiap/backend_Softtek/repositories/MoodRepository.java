package br.com.fiap.backend_Softtek.repositories;

import br.com.fiap.backend_Softtek.Models.MoodModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MoodRepository extends MongoRepository<MoodModel, String> {
    @Query("{ 'userId' : ?0, 'createdAt' : { $gte : ?1, $lt : ?2 } }")
    Optional<MoodModel> findByUserIdAndCreatedAtBetween(String userId, LocalDateTime startOfDay, LocalDateTime endOfDay);

    List<MoodModel> findByCreatedAtAfter(LocalDateTime dateTime);


}
