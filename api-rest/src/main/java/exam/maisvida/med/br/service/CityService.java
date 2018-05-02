package exam.maisvida.med.br.service;

import exam.maisvida.med.br.exception.ObjectAlreadyExistsException;
import exam.maisvida.med.br.exception.ObjectIncorrectException;
import exam.maisvida.med.br.exception.ObjectNotFoundException;
import exam.maisvida.med.br.model.City;
import exam.maisvida.med.br.repository.CityRepository;
import exam.maisvida.med.br.validator.Validator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;

@Service
public class CityService {

    private CityRepository repository;

    public CityService(CityRepository repository) { this.repository = repository; }

    public void deleteAll() {
        repository.deleteAll();
    }

    public City citySave(City city) {
        if (!Validator.cityIsValid(city)) {
            throw new ObjectIncorrectException("All fields need to be filled in!");
        }

        City cityFind = repository.findByName(city.getName());

        if(cityFind != null){
            throw new ObjectAlreadyExistsException("Specialty Already Exists!");
        }

        try {
            this.repository.save(city);
        } catch (Exception e) {
            throw new MultipartException("Error in create specialty: " + e.getMessage());
        }

        return city;
    }

    public City findByName(String name) {
        if(!Validator.fieldIsValid(name)){
            throw new ObjectIncorrectException("name is necessary!");
        }

        City city = null;
        try {
            city = this.repository.findByName(name);
        } catch (Exception e) {
            throw new MultipartException("Error in get role by name: " + e.getMessage());
        } finally {
            if(city == null){
                throw new ObjectNotFoundException("Role not found");
            }
            return city;
        }
    }
}
