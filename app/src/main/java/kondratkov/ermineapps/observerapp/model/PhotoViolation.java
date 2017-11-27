package kondratkov.ermineapps.observerapp.model;

/**
 * Created by kondratkov on 26.11.2017.
 */

public class PhotoViolation {

    public int Id;
    public int Violation_id;
    public byte[] Photo;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getViolation_id() {
        return Violation_id;
    }

    public void setViolation_id(int violation_id) {
        Violation_id = violation_id;
    }

    public byte[] getPhoto() {
        return Photo;
    }

    public void setPhoto(byte[] photo) {
        Photo = photo;
    }
}
