package exam.maisvida.med.br;

import exam.maisvida.med.br.model.*;
import exam.maisvida.med.br.repository.CityRepository;
import exam.maisvida.med.br.repository.RegionRepository;
import exam.maisvida.med.br.repository.SpecialtyRepository;
import exam.maisvida.med.br.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Arrays;

@SpringBootApplication()
public class Application extends SpringBootServletInitializer implements CommandLineRunner{

    @Autowired
    private CustomUserDetailsService service;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private RegionRepository regionRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Delete and insert default user!");
        service.deleteAll();
        service.save(
                new User("Roy", "roy", "spring",
                        Arrays.asList(
                                new Role("ROLE_USER"),
                                new Role("ROLE_ADMIN")
                        )));


        this.populateCity();
        this.populateRegion();
        this.populateSpecialty();
    }

    private void populateCity(){
        cityRepository.deleteAll();
        cityRepository.save(new City("Brasília"));
        cityRepository.save(new City("São Paulo"));
    }

    private void populateRegion(){
        regionRepository.deleteAll();
        regionRepository.save(new Region("DF", Arrays.asList(cityRepository.findByName("Brasília"))));
        regionRepository.save(new Region("SP", Arrays.asList(cityRepository.findByName("São Paulo"))));
    }

    private void populateSpecialty(){
        specialtyRepository.deleteAll();
        specialtyRepository.save(new Specialty("Pediatra"));
        specialtyRepository.save(new Specialty("Cardiologista"));
        specialtyRepository.save(new Specialty("Ginecologista"));
        specialtyRepository.save(new Specialty("Ortopedista"));
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }
}
