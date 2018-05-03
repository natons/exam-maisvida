package exam.maisvida.med.br.controller;

import exam.maisvida.med.br.model.Doctor;
import exam.maisvida.med.br.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService service;

    @PostMapping("/save")
    public ResponseEntity<Doctor> doctorSave(@RequestBody Doctor doctor) {
        HttpStatus status = doctor.getId() == null ? HttpStatus.CREATED : HttpStatus.OK;
        doctor = service.doctorSave(doctor);

        return new ResponseEntity<>(doctor, status);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Doctor> doctorDelete(@RequestParam(value = "id", defaultValue = "") String id) {

        Doctor doctor = service.doctorDelete(id);

        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Doctor>> doctorAll() {

        List<Doctor> doctors = service.doctorAll();

        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @GetMapping("/get/email")
    public ResponseEntity<Doctor> doctorFindByEmail(@RequestParam String email) {

        Doctor doctor = service.doctorFindByEmail(email);

        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    @GetMapping("/get/firstName")
    public ResponseEntity<List<Doctor>> doctorFindByFirstName(@RequestParam(value = "firstName") String firstName) {

        List<Doctor> doctors = service.doctorFindByFirstName(firstName);

        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @GetMapping("/get/lastName")
    public ResponseEntity<List<Doctor>> doctorFindByLastName(@RequestParam(value = "lastName") String lastName) {

        List<Doctor> doctors = service.doctorFindByLastName(lastName);

        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @GetMapping("/get/status")
    public ResponseEntity<List<Doctor>> doctorFindByStatus(@RequestParam(value = "status") Boolean status) {

        List<Doctor> doctors = service.doctorFindByStatus(status);

        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @GetMapping("/get/active")
    public ResponseEntity<List<Doctor>> doctorFindByActive(@RequestParam(value = "active") Boolean active) {
        List<Doctor> doctors = service.doctorFindByActive(active);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @GetMapping("/get/state")
    public ResponseEntity<List<Doctor>> doctorFindByState(@RequestParam(value = "state") String state) {
        List<Doctor> doctors = service.doctorFindByState(state);

        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @GetMapping("/get/city")
    public ResponseEntity<List<Doctor>> doctorFindByCity(@RequestParam(value = "city") String city) {
        List<Doctor> doctors = service.doctorFindByCity(city);

        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }
}