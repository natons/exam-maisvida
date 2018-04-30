package exam.maisvida.med.br.repository;

import exam.maisvida.med.br.model.City;
import exam.maisvida.med.br.model.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CityRepository extends MongoRepository<City, String> {

    public City findByName(String name);
}
