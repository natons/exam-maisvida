package exam.maisvida.med.br.repository;

import exam.maisvida.med.br.model.Region;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegionRepository extends MongoRepository<Region, String> {

    Region findByState(String state);
}
