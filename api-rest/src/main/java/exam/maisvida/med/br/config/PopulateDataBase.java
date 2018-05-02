package exam.maisvida.med.br.config;

import exam.maisvida.med.br.model.*;
import exam.maisvida.med.br.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class PopulateDataBase implements CommandLineRunner {

    @Autowired
    private CustomUserDetailsService service;

    @Autowired
    private SpecialtyService specialtyService;

    @Autowired
    private CityService cityService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        this.populateRole();
        this.populateUser();
        this.populateCity();
        this.populateRegion();
        this.populateSpecialty();
    }

    private void populateRole(){
        roleService.deleteAll();
        roleService.roleSave(new Role("ROLE_USER"));
        roleService.roleSave(new Role("ROLE_ADMIN"));
    }

    private void populateUser(){
        service.deleteAll();
        service.save(
                new User("Roy", "roy", "spring",
                        Arrays.asList(
                                roleService.findByName("ROLE_USER"),
                                roleService.findByName("ROLE_ADMIN")
                        )));
    }

    private void populateCity(){
        cityService.deleteAll();
        cityService.citySave(new City("Brasília"));
        cityService.citySave(new City("São Paulo"));
    }

    private void populateRegion(){
        regionService.deleteAll();
        regionService.save(new Region("DF", Arrays.asList(cityService.findByName("Brasília"))));
        regionService.save(new Region("SP", Arrays.asList(cityService.findByName("São Paulo"))));
    }

    private void populateSpecialty(){
        specialtyService.deleteAll();
        specialtyService.specialtySave(new Specialty("Pediatra"));
        specialtyService.specialtySave(new Specialty("Cardiologista"));
        specialtyService.specialtySave(new Specialty("Ginecologista"));
        specialtyService.specialtySave(new Specialty("Ortopedista"));
    }
}
