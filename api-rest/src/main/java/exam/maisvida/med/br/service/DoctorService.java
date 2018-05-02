package exam.maisvida.med.br.service;

import exam.maisvida.med.br.exception.ObjectAlreadyExistsException;
import exam.maisvida.med.br.exception.ObjectIncorrectException;
import exam.maisvida.med.br.exception.ObjectNotFoundException;
import exam.maisvida.med.br.model.Doctor;
import exam.maisvida.med.br.repository.DoctorRepository;
import exam.maisvida.med.br.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor doctorSave(Doctor doctor){
        if (!Validator.doctorIsValid(doctor)) {
            throw new ObjectIncorrectException("All fields need to be filled in!");
        }

        if (Validator.doctorExistsByEmail(doctorRepository, doctor)) {
            throw new ObjectAlreadyExistsException("Email Already Exists!");
        }

        try {
            this.doctorRepository.save(doctor);
        } catch (Exception e) {
            throw new MultipartException("Error in create doctor: " + e.getMessage());
        }

        return doctor;
    }

    public Doctor doctorDelete(String id) {
        Optional<Doctor> optional = this.doctorRepository.findById(id);
        if(!optional.isPresent()){
            throw new ObjectNotFoundException("Doctor not found");
        }

        Doctor doctor = optional.get();

        try {
            this.doctorRepository.deleteById(doctor.getId());
        } catch (Exception e) {
            throw new MultipartException("Error in delete doctor: " + e.getMessage());
        } finally {
            return doctor;
        }
    }

    public List<Doctor> doctorAll() {
        List<Doctor> doctors = new ArrayList<>();
        try {
            doctors = this.doctorRepository.findAll();
        } catch (Exception e) {
            throw new MultipartException("Error in get all doctors: " + e.getMessage());
        } finally {
            return doctors;
        }
    }

    public Doctor doctorFindByEmail(String email) {
        if(!Validator.fieldIsValid(email)){
            throw new ObjectIncorrectException("email is necessary!");
        }

        Doctor doctor = null;
        try {
            doctor = this.doctorRepository.findByEmail(email);
        } catch (Exception e) {
            throw new MultipartException("Error in get doctor by email: " + e.getMessage());
        } finally {
            if(doctor == null){
                throw new ObjectNotFoundException("Doctor not found");
            }
            return doctor;
        }
    }

    public List<Doctor> doctorFindByFirstName(String firstName) {
        if(!Validator.fieldIsValid(firstName)){
            throw new ObjectIncorrectException("first name is necessary!");
        }

        List<Doctor> doctors = new ArrayList<>();
        try {
            doctors = this.doctorRepository.findByFirstName(firstName);
        } catch (Exception e) {
            throw new MultipartException("Error in get all doctors: " + e.getMessage());
        } finally {
            if(doctors.isEmpty() || doctors.size() == 0){
                throw new ObjectNotFoundException("Doctors not found");
            }
            return doctors;
        }
    }

    public List<Doctor> doctorFindByLastName(String lastName) {
        if(!Validator.fieldIsValid(lastName)){
            throw new ObjectIncorrectException("last name is necessary!");
        }

        List<Doctor> doctors = new ArrayList<>();
        try {
            doctors = this.doctorRepository.findByLastName(lastName);
        } catch (Exception e) {
            throw new MultipartException("Error in get doctor by last name: " + e.getMessage());
        } finally {
            if(doctors.isEmpty() || doctors.size() == 0){
                throw new ObjectNotFoundException("Doctors not found");
            }
            return doctors;
        }
    }

    public List<Doctor> doctorFindByStatus(Boolean status) {
        List<Doctor> doctors = new ArrayList<>();
        try {
            doctors = this.doctorRepository.findByStatus(status);
        } catch (Exception e) {
            throw new MultipartException("Error in get doctor by status: " + e.getMessage());
        } finally {
            if(doctors.isEmpty() || doctors.size() == 0){
                throw new ObjectNotFoundException("Doctors not found");
            }
            return doctors;
        }
    }

    public List<Doctor> doctorFindByActive(Boolean active) {
        List<Doctor> doctors = new ArrayList<>();
        try {
            doctors = this.doctorRepository.findByActive(active);
        } catch (Exception e) {
            throw new MultipartException("Error in get doctor by active: " + e.getMessage());
        } finally {
            if(doctors.isEmpty() || doctors.size() == 0){
                throw new ObjectNotFoundException("Doctors not found");
            }
            return doctors;
        }
    }

    public List<Doctor> doctorFindByState(String state) {
        if(!Validator.fieldIsValid(state)){
            throw new ObjectIncorrectException("state is necessary!");
        }
        List<Doctor> doctors = new ArrayList<>();
        try {
            doctors = this.doctorRepository.findByState(state);
        } catch (Exception e) {
            throw new MultipartException("Error in get doctor by state: " + e.getMessage());
        } finally {
            return doctors;
        }
    }

    public List<Doctor> doctorFindByCity(String city) {
        if(!Validator.fieldIsValid(city)){
            throw new ObjectIncorrectException("city is necessary!");
        }
        List<Doctor> doctors = new ArrayList<>();
        try {
            doctors = this.doctorRepository.findByCity(city);
        } catch (Exception e) {
            throw new MultipartException("Error in get doctor by city: " + e.getMessage());
        } finally {
            return doctors;
        }
    }

    public void deleteAll() {
        this.doctorRepository.deleteAll();
    }
}
