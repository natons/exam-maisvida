package exam.maisvida.med.br.controller;

import exam.maisvida.med.br.model.User;
import exam.maisvida.med.br.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/save")
    public ResponseEntity<User> userSave(@RequestBody User user) {
        HttpStatus status = user.getId() == null ? HttpStatus.CREATED : HttpStatus.OK;
        user = service.userSave(user);

        return new ResponseEntity<>(user, status);
    }
}
