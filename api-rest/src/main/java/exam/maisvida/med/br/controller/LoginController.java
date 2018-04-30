package exam.maisvida.med.br.controller;

import exam.maisvida.med.br.exception.ObjectAlreadyExistsException;
import exam.maisvida.med.br.exception.ObjectIncorrectException;
import exam.maisvida.med.br.exception.ObjectNotFoundException;
import exam.maisvida.med.br.model.Doctor;
import exam.maisvida.med.br.model.User;
import exam.maisvida.med.br.repository.DoctorRepository;
import exam.maisvida.med.br.repository.UserRepository;
import exam.maisvida.med.br.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserRepository repository;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<User> authenticate(@RequestBody User user) {

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


        return new ResponseEntity<>(userFind, HttpStatus.CREATED);

    }
}
