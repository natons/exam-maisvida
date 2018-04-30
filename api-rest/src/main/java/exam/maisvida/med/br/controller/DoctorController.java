package exam.maisvida.med.br.controller;

import exam.maisvida.med.br.exception.ObjectAlreadyExistsException;
import exam.maisvida.med.br.exception.ObjectIncorrectException;
import exam.maisvida.med.br.exception.ObjectNotFoundException;
import exam.maisvida.med.br.model.Doctor;
import exam.maisvida.med.br.model.Region;
import exam.maisvida.med.br.repository.DoctorRepository;
import exam.maisvida.med.br.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorRepository repository;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Doctor> doctorSave(@RequestBody Doctor doctor) {

        if (!Validator.doctorIsValid(doctor)) {
            throw new ObjectIncorrectException("All fields need to be filled in!");
        }

        if (Validator.doctorExistsByEmail(repository, doctor)) {
            throw new ObjectAlreadyExistsException("Email Already Exists!");
        }

        try {
            this.repository.save(doctor);
        } catch (Exception e) {
            throw new MultipartException("Error in create doctor: " + e.getMessage());
        }


        return new ResponseEntity<>(doctor, HttpStatus.CREATED);

    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Doctor> doctorDelete(@RequestParam(value = "id", defaultValue = "") String id) {

        Optional<Doctor> optional = this.repository.findById(id);
        if(!optional.isPresent()){
            throw new ObjectNotFoundException("Doctor not found");
        }

        Doctor doctor = optional.get();

        try {
            this.repository.deleteById(doctor.getId());
        } catch (Exception e) {
            throw new MultipartException("Error in delete doctor: " + e.getMessage());
        } finally {
            return new ResponseEntity<>(doctor, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public ResponseEntity<List<Doctor>> doctorAll() {
        List<Doctor> doctors = new ArrayList<>();
        try {
            doctors = this.repository.findAll();
        } catch (Exception e) {
            throw new MultipartException("Error in get all doctors: " + e.getMessage());
        } finally {
            return new ResponseEntity<>(doctors, HttpStatus.OK);
        }
    }

    @RequestMapping("/get/email")
    public ResponseEntity<Doctor> doctorFindByEmail(@RequestParam String email) {

        if(!Validator.fieldIsValid(email)){
            throw new ObjectIncorrectException("email is necessary!");
        }

        Doctor doctor = null;
        try {
            doctor = this.repository.findByEmail(email);
        } catch (Exception e) {
            throw new MultipartException("Error in get doctor by email: " + e.getMessage());
        } finally {
            if(doctor == null){
                throw new ObjectNotFoundException("Doctor not found");
            }
            return new ResponseEntity<>(doctor, HttpStatus.OK);
        }
    }

    @RequestMapping("/get/firstName")
    public ResponseEntity<List<Doctor>> doctorFindByFirstName(@RequestParam(value = "firstName") String firstName) {
        if(!Validator.fieldIsValid(firstName)){
            throw new ObjectIncorrectException("first name is necessary!");
        }

        List<Doctor> doctors = new ArrayList<>();
        try {
            doctors = this.repository.findByFirstName(firstName);
        } catch (Exception e) {
            throw new MultipartException("Error in get all doctors: " + e.getMessage());
        } finally {
            if(doctors.isEmpty() || doctors.size() == 0){
                throw new ObjectNotFoundException("Doctors not found");
            }
            return new ResponseEntity<>(doctors, HttpStatus.OK);
        }
    }

    @RequestMapping("/get/lastName")
    public ResponseEntity<List<Doctor>> doctorFindByLastName(@RequestParam(value = "lastName") String lastName) {
        if(!Validator.fieldIsValid(lastName)){
            throw new ObjectIncorrectException("last name is necessary!");
        }

        List<Doctor> doctors = new ArrayList<>();
        try {
            doctors = this.repository.findByLastName(lastName);
        } catch (Exception e) {
            throw new MultipartException("Error in get doctor by last name: " + e.getMessage());
        } finally {
            if(doctors.isEmpty() || doctors.size() == 0){
                throw new ObjectNotFoundException("Doctors not found");
            }
            return new ResponseEntity<>(doctors, HttpStatus.OK);
        }
    }

    @RequestMapping("/get/status")
    public ResponseEntity<List<Doctor>> doctorFindByStatus(@RequestParam(value = "status") Boolean status) {
        List<Doctor> doctors = new ArrayList<>();
        try {
            doctors = this.repository.findByStatus(status);
        } catch (Exception e) {
            throw new MultipartException("Error in get doctor by status: " + e.getMessage());
        } finally {
            if(doctors.isEmpty() || doctors.size() == 0){
                throw new ObjectNotFoundException("Doctors not found");
            }
            return new ResponseEntity<>(doctors, HttpStatus.OK);
        }
    }

    @RequestMapping("/get/active")
    public ResponseEntity<List<Doctor>> doctorFindByActive(@RequestParam(value = "active") Boolean active) {
        List<Doctor> doctors = new ArrayList<>();
        try {
            doctors = this.repository.findByActive(active);
        } catch (Exception e) {
            throw new MultipartException("Error in get doctor by active: " + e.getMessage());
        } finally {
            if(doctors.isEmpty() || doctors.size() == 0){
                throw new ObjectNotFoundException("Doctors not found");
            }
            return new ResponseEntity<>(doctors, HttpStatus.OK);
        }
    }


    @RequestMapping("/get/state")
    public ResponseEntity<List<Doctor>> doctorFindByState(@RequestParam(value = "state") String state) {
        if(!Validator.fieldIsValid(state)){
            throw new ObjectIncorrectException("state is necessary!");
        }
        List<Doctor> doctors = new ArrayList<>();
        try {
            doctors = this.repository.findByState(state);
        } catch (Exception e) {
            throw new MultipartException("Error in get doctor by state: " + e.getMessage());
        } finally {
            return new ResponseEntity<>(doctors, HttpStatus.OK);
        }
    }

    @RequestMapping("/get/city")
    public ResponseEntity<List<Doctor>> doctorFindByCity(@RequestParam(value = "city") String city) {
        if(!Validator.fieldIsValid(city)){
            throw new ObjectIncorrectException("city is necessary!");
        }
        List<Doctor> doctors = new ArrayList<>();
        try {
            doctors = this.repository.findByCity(city);
        } catch (Exception e) {
            throw new MultipartException("Error in get doctor by city: " + e.getMessage());
        } finally {
            return new ResponseEntity<>(doctors, HttpStatus.OK);
        }
    }
}