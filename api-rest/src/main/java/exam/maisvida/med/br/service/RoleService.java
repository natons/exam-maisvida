package exam.maisvida.med.br.service;

import exam.maisvida.med.br.exception.ObjectAlreadyExistsException;
import exam.maisvida.med.br.exception.ObjectIncorrectException;
import exam.maisvida.med.br.exception.ObjectNotFoundException;
import exam.maisvida.med.br.model.Role;
import exam.maisvida.med.br.repository.RoleRepository;
import exam.maisvida.med.br.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {


    private RoleRepository repository;

    @Autowired
    public RoleService(RoleRepository repository){ this.repository = repository; }

    public Role roleSave(Role role){
        if (!Validator.roleIsValid(role)) {
            throw new ObjectIncorrectException("All fields need to be filled in!");
        }

        if (Validator.roleExistsByEmail(repository, role)) {
            throw new ObjectAlreadyExistsException("Role Already Exists!");
        }

        try {
            this.repository.save(role);
        } catch (Exception e) {
            throw new MultipartException("Error in create doctor: " + e.getMessage());
        }

        return role;
    }

    public List<Role> roleAll() {
        List<Role> roles = new ArrayList<>();
        try {
            roles = this.repository.findAll();
        } catch (Exception e) {
            throw new MultipartException("Error in get all roles: " + e.getMessage());
        } finally {
            return roles;
        }
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public Role findByName(String name) {
        if(!Validator.fieldIsValid(name)){
            throw new ObjectIncorrectException("name is necessary!");
        }

        Role role = null;
        try {
            role = this.repository.findByName(name);
        } catch (Exception e) {
            throw new MultipartException("Error in get role by name: " + e.getMessage());
        } finally {
            if(role == null){
                throw new ObjectNotFoundException("Role not found");
            }
            return role;
        }
    }
}

