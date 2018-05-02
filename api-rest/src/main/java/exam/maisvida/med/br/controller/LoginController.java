package exam.maisvida.med.br.controller;

import exam.maisvida.med.br.model.User;
import exam.maisvida.med.br.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService service;

    @PostMapping("/authenticate")
    public ResponseEntity<User> authenticate(@RequestBody User user) {

        User userFind = service.authenticate(user);

        return new ResponseEntity<>(userFind, HttpStatus.CREATED);

    }
}
