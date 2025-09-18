package br.com.fiap.backend_Softtek.repositories;

import br.com.fiap.backend_Softtek.Models.MoodModel;
import br.com.fiap.backend_Softtek.dtos.MonthlyMoodResponse;
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
        // Método para buscar um humor por userId e um intervalo de tempo (para o humor do dia)
    Optional<MoodModel> findByUserIdAndCreatedAtBetween(String userId, LocalDateTime startDate, LocalDateTime endDate);

    // Método para buscar todos os humores de um usuário criados após uma data específica (para o relatório semanal)
    List<MoodModel> findByUserIdAndCreatedAtAfter(String userId, LocalDateTime date);

    // Método para buscar todos os humores de um usuário (para o relatório mensal)
    List<MoodModel> findByUserId(String userId);


}
