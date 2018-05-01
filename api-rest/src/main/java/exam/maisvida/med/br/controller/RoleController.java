package exam.maisvida.med.br.controller;

import exam.maisvida.med.br.model.Role;
import exam.maisvida.med.br.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleRepository repository;

    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public ResponseEntity<List<Role>> roleAll() {
        List<Role> roles = new ArrayList<>();
        try {
            roles = this.repository.findAll();
        } catch (Exception e) {
            throw new MultipartException("Error in get all roles: " + e.getMessage());
        } finally {
            return new ResponseEntity<>(roles, HttpStatus.OK);
        }
    }
}
