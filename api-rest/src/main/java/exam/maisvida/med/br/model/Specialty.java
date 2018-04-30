package exam.maisvida.med.br.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class Specialty {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    public  Specialty(){}

    public Specialty(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
