package br.com.fiap.backend_Softtek.repositories;

import br.com.fiap.backend_Softtek.Models.TaskModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends MongoRepository<TaskModel, String> {
    List<TaskModel> findByUserIdAndDate(String userId, LocalDate date);

    Optional<TaskModel> findByUserIdAndTaskName(String userId, String taskName);
}
