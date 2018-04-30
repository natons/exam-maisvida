package exam.maisvida.med.br.repository;

import exam.maisvida.med.br.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {


    User findByLogin(String login);

    User findByLoginAndPassword(String login, String password);


}
