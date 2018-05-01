package exam.maisvida.med.br.controller;

import exam.maisvida.med.br.exception.ObjectIncorrectException;
import exam.maisvida.med.br.model.User;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository repository;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<User> userSave(@RequestBody User user) {

        if (!Validator.userIsValid(user)) {
            throw new ObjectIncorrectException("All fields need to be filled in!");
        }

        try {
            this.repository.save(user);
        } catch (Exception e) {
            throw new MultipartException("Error in create user: " + e.getMessage());
        }


        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
