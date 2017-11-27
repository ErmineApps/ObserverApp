package kondratkov.ermineapps.observerapp.model;

/**
 * Created by kondratkov on 26.11.2017.
 */

public class Region {
    private int Id;
    private String Name;

    private City[] Cities;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public City[] getCities() {
        return Cities;
    }

    public void setCities(City[] cities) {
        Cities = cities;
    }
}
