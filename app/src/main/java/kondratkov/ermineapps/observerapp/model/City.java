package kondratkov.ermineapps.observerapp.model;

/**
 * Created by kondratkov on 26.11.2017.
 */

public class City {

    public int Id;
    public String Name;
    public int Region_id;
    public double Latitude;
    public double Longitude;

    public Region Region;

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

    public int getRegion_id() {
        return Region_id;
    }

    public void setRegion_id(int region_id) {
        Region_id = region_id;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public kondratkov.ermineapps.observerapp.model.Region getRegion() {
        return Region;
    }

    public void setRegion(kondratkov.ermineapps.observerapp.model.Region region) {
        Region = region;
    }
}
