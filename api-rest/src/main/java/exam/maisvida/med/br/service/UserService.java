package exam.maisvida.med.br.service;

import exam.maisvida.med.br.exception.ObjectIncorrectException;
import exam.maisvida.med.br.model.User;
import exam.maisvida.med.br.repository.UserRepository;
import exam.maisvida.med.br.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;

@Service
public class UserService {

    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) { this.repository = repository; }

    public User userSave(User user) {
        if (!Validator.userIsValid(user)) {
            throw new ObjectIncorrectException("All fields need to be filled in!");
        }

        try {
            this.repository.save(user);
        } catch (Exception e) {
            throw new MultipartException("Error in create user: " + e.getMessage());
        } finally {
            return user;
        }
    }
}
