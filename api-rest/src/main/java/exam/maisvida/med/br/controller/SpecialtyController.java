package exam.maisvida.med.br.controller;

import exam.maisvida.med.br.model.Specialty;
import exam.maisvida.med.br.service.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/specialty")
public class SpecialtyController {

    @Autowired
    private SpecialtyService service;

    @PostMapping("/save")
    public ResponseEntity<Specialty> specialtySave(@RequestBody Specialty specialty) {
        HttpStatus status = specialty.getId() == null ? HttpStatus.CREATED : HttpStatus.OK;
        specialty = service.specialtySave(specialty);

        return new ResponseEntity<>(specialty, status);

    }

    @DeleteMapping("/delete")
    public ResponseEntity<Specialty> specialtyDelete(@RequestParam(value = "id", defaultValue = "") String id) {
        Specialty specialty = service.specialtyDelete(id);

        return new ResponseEntity<>(specialty, HttpStatus.OK);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Specialty>> specialtyAll() {
        List<Specialty> specialties = service.specialtyAll();

        return new ResponseEntity<>(specialties, HttpStatus.OK);
    }
}
