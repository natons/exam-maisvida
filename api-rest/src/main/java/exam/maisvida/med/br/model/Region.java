package exam.maisvida.med.br.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;

public class Region {

    @Id
    private String id;
    @Indexed(unique = true)
    private String state;
    private List<City> cities;

    public Region(){

    }

    public Region(String state, List<City> cities) {
        this.state = state;
        this.cities = cities;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
