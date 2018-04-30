package exam.maisvida.med.br.repository;

import exam.maisvida.med.br.model.Specialty;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpecialtyRepository extends MongoRepository<Specialty, String> {

    Specialty findByName(String name);
}
