package exam.maisvida.med.br.controller;

import exam.maisvida.med.br.model.Role;
import exam.maisvida.med.br.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService service;

    @GetMapping("/get/all")
    public ResponseEntity<List<Role>> roleAll() {
        List<Role> roles = service.roleAll();

        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
