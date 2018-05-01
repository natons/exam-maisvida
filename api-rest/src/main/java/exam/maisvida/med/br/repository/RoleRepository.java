package exam.maisvida.med.br.repository;

import exam.maisvida.med.br.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String > {

    public Role findByName(String name);
}
