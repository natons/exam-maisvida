package exam.maisvida.med.br.service;

import exam.maisvida.med.br.exception.ObjectIncorrectException;
import exam.maisvida.med.br.exception.ObjectNotFoundException;
import exam.maisvida.med.br.model.User;
import exam.maisvida.med.br.repository.UserRepository;
import exam.maisvida.med.br.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;

@Service
public class LoginService {

    private UserRepository repository;

    @Autowired
    public LoginService(UserRepository repository) {
        this.repository = repository;
    }

    public User authenticate(User user) {
        if (!Validator.userIsValid(user)) {
            throw new ObjectIncorrectException("All fields need to be filled in!");
        }

        User userFind = null;

        try {
            userFind = this.repository.findByLoginAndPassword(user.getLogin(), user.getPassword());
        } catch (Exception e) {
            throw new MultipartException("Error in authenticate user: " + e.getMessage());
        }

        if(userFind == null)
            throw new ObjectNotFoundException("User not found!");

        return userFind;
    }
}
