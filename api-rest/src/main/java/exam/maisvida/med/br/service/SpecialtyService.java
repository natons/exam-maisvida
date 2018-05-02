package exam.maisvida.med.br.service;

import exam.maisvida.med.br.exception.ObjectAlreadyExistsException;
import exam.maisvida.med.br.exception.ObjectIncorrectException;
import exam.maisvida.med.br.exception.ObjectNotFoundException;
import exam.maisvida.med.br.model.Specialty;
import exam.maisvida.med.br.repository.SpecialtyRepository;
import exam.maisvida.med.br.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SpecialtyService {

    private SpecialtyRepository repository;

    @Autowired
    public  SpecialtyService(SpecialtyRepository repository) { this.repository = repository; }

    public Specialty specialtySave(Specialty specialty) {
        if (!Validator.specialtyIsValid(specialty)) {
            throw new ObjectIncorrectException("All fields need to be filled in!");
        }

        Specialty specialtyFind = repository.findByName(specialty.getName());

        if(specialtyFind != null){
            throw new ObjectAlreadyExistsException("Specialty Already Exists!");
        }

        try {
            this.repository.save(specialty);
        } catch (Exception e) {
            throw new MultipartException("Error in create specialty: " + e.getMessage());
        }

        return specialty;
    }

    public Specialty specialtyDelete(String id) {
        Optional<Specialty> optional = this.repository.findById(id);
        if (!optional.isPresent()) {
            throw new ObjectNotFoundException("Specialty not found");
        }

        Specialty specialty = optional.get();

        try {
            this.repository.deleteById(specialty.getId());
        } catch (Exception e) {
            throw new MultipartException("Error in delete specialty: " + e.getMessage());
        } finally {
            return specialty;
        }
    }

    public List<Specialty> specialtyAll() {
        List<Specialty> specialties = new ArrayList<>();
        try {
            specialties = this.repository.findAll();
        } catch (Exception e) {
            throw new MultipartException("Error in get all specialties: " + e.getMessage());
        } finally {
            return specialties;
        }
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
