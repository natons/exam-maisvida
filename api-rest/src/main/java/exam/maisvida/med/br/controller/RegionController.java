package exam.maisvida.med.br.controller;

import exam.maisvida.med.br.exception.ObjectAlreadyExistsException;
import exam.maisvida.med.br.exception.ObjectIncorrectException;
import exam.maisvida.med.br.exception.ObjectNotFoundException;
import exam.maisvida.med.br.model.City;
import exam.maisvida.med.br.model.Region;
import exam.maisvida.med.br.repository.CityRepository;
import exam.maisvida.med.br.repository.RegionRepository;
import exam.maisvida.med.br.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/region")
public class RegionController {

    @Autowired
    private RegionRepository repository;

    @Autowired
    private CityRepository cityRepository;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Region> regionSave(@RequestBody Region region) {

        if (!Validator.regionIsValid(region)) {
            throw new ObjectIncorrectException("All fields need to be filled in!");
        }

        City cityFind = null;
        for (int index = 0; index < region.getCities().size() && cityFind == null; index++){
            cityFind = cityRepository.findByName(region.getCities().get(index).getName());
        }

        if(cityFind != null){
            throw new ObjectAlreadyExistsException("City Already Exists!");
        }

        Region regionFind = this.repository.findByState(region.getState());
        if(regionFind != null){
            List<City> citiesToAdd = new ArrayList<>();
            for(City city: regionFind.getCities()){
                boolean exists = false;
                for(City city1: region.getCities()){
                    if(city.getName() == city1.getName()){
                        exists = true;
                    }
                }
                if(!exists)
                    citiesToAdd.add(city);
                exists = false;
            }
            region.getCities().addAll(citiesToAdd);
        }

        try {
            for(City city: region.getCities()){
                this.cityRepository.save(city);
            }
            this.repository.save(region);
        } catch (Exception e) {
            throw new MultipartException("Error in create region: " + e.getMessage());
        }


        return new ResponseEntity<>(region, HttpStatus.CREATED);

    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Region> regionDelete(@RequestParam(value = "id", defaultValue = "") String id) {

        Optional<Region> optional = this.repository.findById(id);
        if(!optional.isPresent()){
            throw new ObjectNotFoundException("Region not found");
        }

        Region region = optional.get();

        try {
            this.repository.deleteById(region.getId());
        } catch (Exception e) {
            throw new MultipartException("Error in delete region: " + e.getMessage());
        } finally {
            return new ResponseEntity<>(region, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public ResponseEntity<List<Region>> regionAll() {
        List<Region> regions = new ArrayList<>();
        try {
            regions = this.repository.findAll();
        } catch (Exception e) {
            throw new MultipartException("Error in get all regions: " + e.getMessage());
        } finally {
            return new ResponseEntity<>(regions, HttpStatus.OK);
        }
    }
}
