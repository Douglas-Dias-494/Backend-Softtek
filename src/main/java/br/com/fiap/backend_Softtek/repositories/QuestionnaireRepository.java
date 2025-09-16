package br.com.fiap.backend_Softtek.repositories;

import br.com.fiap.backend_Softtek.Models.QuestionnaireModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionnaireRepository extends MongoRepository<QuestionnaireModel, String> {

}
