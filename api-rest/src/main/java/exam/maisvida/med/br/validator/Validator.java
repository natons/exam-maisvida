package exam.maisvida.med.br.validator;

import exam.maisvida.med.br.model.*;
import exam.maisvida.med.br.repository.DoctorRepository;
import exam.maisvida.med.br.repository.RoleRepository;

public abstract class Validator {


    public static Boolean doctorExistsByEmail(DoctorRepository repository, Doctor doctor) {
        Doctor doctorFind = repository.findByEmail(doctor.getEmail());

        if(doctorFind == null){
            return Boolean.FALSE;
        } else if(doctor.getId() == null){
            return Boolean.TRUE;
        } else if(doctor.getId().equals(doctorFind.getId())){
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    public static Boolean roleExistsByEmail(RoleRepository repository, Role role) {
        Role roleFind = repository.findByName(role.getName());

        if(roleFind == null){
            return Boolean.FALSE;
        } else if(roleFind.getId() == null){
            return Boolean.TRUE;
        } else if(roleFind.getId().equals(roleFind.getId())){
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

    public static boolean roleIsValid(Role role) {
        return fieldsWereFilled(role.getName());
    }

    public static boolean cityIsValid(City city) {
        return fieldsWereFilled(city.getName());
    }
}
