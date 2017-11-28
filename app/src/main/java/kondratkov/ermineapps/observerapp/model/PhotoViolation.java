package kondratkov.ermineapps.observerapp.model;

import android.media.Image;

import java.io.File;

/**
 * Created by kondratkov on 26.11.2017.
 */

public class PhotoViolation {

    private int Id;
    private int Violation_id;
    private File Photo;

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

    public File getPhoto() {
        return Photo;
    }

    public void setPhoto(File photo) {
        Photo = photo;
    }
}
