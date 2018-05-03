package exam.maisvida.med.br.service;

import exam.maisvida.med.br.exception.ObjectAlreadyExistsException;
import exam.maisvida.med.br.exception.ObjectIncorrectException;
import exam.maisvida.med.br.exception.ObjectNotFoundException;
import exam.maisvida.med.br.model.City;
import exam.maisvida.med.br.model.Region;
import exam.maisvida.med.br.repository.CityRepository;
import exam.maisvida.med.br.repository.RegionRepository;
import exam.maisvida.med.br.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {

    private RegionRepository repository;

    private CityRepository cityRepository;

    @Autowired
    public RegionService(RegionRepository repository, CityRepository cityRepository) { this.repository=repository; this.cityRepository=cityRepository; }

    public Region regionSave(Region region) {
        if (!Validator.regionIsValid(region)) {
            throw new ObjectIncorrectException("All fields need to be filled in!");
        }

        Region regionFind = this.repository.findByState(region.getState());
        if(regionFind != null && region.getId() != null && regionFind.getId().equals(region.getId())){
            City cityFind = null;
            for (int index = 0; index < region.getCities().size() && cityFind == null; index++){
                cityFind = cityRepository.findByName(region.getCities().get(index).getName());
            }

            if(cityFind != null){
                throw new ObjectAlreadyExistsException("City Already Exists!");
            }

            region.getCities().addAll(citiesToAdd(region, regionFind));
        } else if(regionFind != null){
            throw new ObjectAlreadyExistsException("Region Already Exists!");
        }

        region = executeSave(region);

        return region;
    }

    private Region executeSave(Region region){
        try {
            for(City city: region.getCities()){
                this.cityRepository.save(city);
            }
            this.repository.save(region);
        } catch (Exception e) {
            throw new MultipartException("Error in create region: " + e.getMessage());
        }

        return region;
    }

    private List<City> citiesToAdd(Region region, Region regionFind){
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

        return citiesToAdd;
    }

    public Region save(Region region){
        Region regionFind = this.repository.findByState(region.getState());
        if(regionFind != null){
            region.getCities().addAll(citiesToAdd(region, regionFind));
        }

        region = executeSave(region);

        return region;
    }

    public Region regionDelete(String id) {
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
            return region;
        }
    }

    public List<Region> regionAll() {
        List<Region> regions = new ArrayList<>();
        try {
            regions = this.repository.findAll();
        } catch (Exception e) {
            throw new MultipartException("Error in get all regions: " + e.getMessage());
        } finally {
            return regions;
        }
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
