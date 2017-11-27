package kondratkov.ermineapps.observerapp.model;

/**
 * Created by kondratkov on 26.11.2017.
 */

public class LabelsMap {

    private int Id;
    private int City_id;
    private double Latitude;
    private double Longitude;
    private String Date_creation;
    private int Violation_id;

    private City Cities;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getCity_id() {
        return City_id;
    }

    public void setCity_id(int city_id) {
        City_id = city_id;
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

    public String getDate_creation() {
        return Date_creation;
    }

    public void setDate_creation(String date_creation) {
        Date_creation = date_creation;
    }

    public int getViolation_id() {
        return Violation_id;
    }

    public void setViolation_id(int violation_id) {
        Violation_id = violation_id;
    }

    public City getCities() {
        return Cities;
    }

    public void setCities(City cities) {
        Cities = cities;
    }
}
