package br.com.fiap.backend_Softtek.repositories;

import br.com.fiap.backend_Softtek.Models.DailyProgressModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DailyProgressRepository extends MongoRepository<DailyProgressModel, String> {
    Optional<DailyProgressModel> findByUserNameAndDate(String userName, String date);
}