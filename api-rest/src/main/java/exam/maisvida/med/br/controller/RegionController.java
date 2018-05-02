package exam.maisvida.med.br.controller;

import exam.maisvida.med.br.model.Region;
import exam.maisvida.med.br.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {

    @Autowired
    RegionService service;

    @PostMapping("/save")
    public ResponseEntity<Region> regionSave(@RequestBody Region region) {
        region = service.regionSave(region);

        return new ResponseEntity<>(region, HttpStatus.CREATED);

    }

    @DeleteMapping("/delete")
    public ResponseEntity<Region> regionDelete(@RequestParam(value = "id", defaultValue = "") String id) {

        Region region = service.regionDelete(id);

        return new ResponseEntity<>(region, HttpStatus.OK);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Region>> regionAll() {
        List<Region> regions = service.regionAll();

        return new ResponseEntity<>(regions, HttpStatus.OK);
    }
}
