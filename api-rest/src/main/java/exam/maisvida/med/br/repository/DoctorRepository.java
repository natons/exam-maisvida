package exam.maisvida.med.br.repository;

import java.util.List;

import exam.maisvida.med.br.model.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface DoctorRepository extends MongoRepository<Doctor, String> {


    public Doctor findByEmail(String email);

    public List<Doctor> findByFirstName(String firstName);

    public List<Doctor> findByActive(Boolean active);

    public List<Doctor> findByStatus(Boolean status);

    @Query(value = "{ 'region.cities.name' : ?0 }")
    List<Doctor> findByCity(String city);

    @Query(value = "{ 'region.state' : ?0 }")
    List<Doctor> findByState(String state);

    public List<Doctor> findByLastName(String lastName);

}