package exam.maisvida.med.br.validator;

import exam.maisvida.med.br.model.Doctor;
import exam.maisvida.med.br.model.Region;
import exam.maisvida.med.br.model.Specialty;
import exam.maisvida.med.br.model.User;
import exam.maisvida.med.br.repository.DoctorRepository;

public abstract class Validator {


    public static Boolean doctorExistsByEmail(DoctorRepository repository, Doctor doctor) {
        Doctor doctorFind = repository.findByEmail(doctor.getEmail());

        if(doctorFind == null){
            return Boolean.FALSE;
        } else if(doctor.getId().equals(doctorFind.getId())){
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    public static Boolean fieldsWereFilled(Object... fields){
        for(Object field: fields){
            String fieldString = field.toString();
            if(fieldString.isEmpty() || fieldString.trim().length() == 0){
                return Boolean.FALSE;
            }
        }

        return Boolean.TRUE;
    }

    public static Boolean fieldIsValid(String field) {
        if(field.isEmpty() || field.trim().length() == 0){
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    public static boolean doctorIsValid(Doctor doctor) {

        return fieldsWereFilled(doctor.getActive(), doctor.getEmail(), doctor.getFirstName(),
                doctor.getLastName(), doctor.getStatus(), doctor.getRegion().getState(),
                doctor.getSpecialty().getName(), doctor.getCity().getName());

    }

    public static boolean userIsValid(User user){
        return fieldsWereFilled(user.getLogin(), user.getPassword());
    }

    public static boolean regionIsValid(Region region) {
        return fieldsWereFilled(region.getState());
    }

    public static boolean specialtyIsValid(Specialty specialty) {
        return fieldsWereFilled(specialty.getName());
    }
}
