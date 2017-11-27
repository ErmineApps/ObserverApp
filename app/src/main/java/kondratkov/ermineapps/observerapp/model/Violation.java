package kondratkov.ermineapps.observerapp.model;

/**
 * Created by kondratkov on 26.11.2017.
 */

public class Violation {

    private int Id;
    private double Rating;
    private int User_id;
    private String User_name;
    private int Like;
    private int Dislaid;
    private String Date;
    private String Type_violation;
    private String Body_observation;

    private LabelsMap[] LabelsMaps;
    private Message[] Messages;
    private PhotoViolation[] PhotoViolations;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public double getRating() {
        return Rating;
    }

    public void setRating(double rating) {
        Rating = rating;
    }

    public int getUser_id() {
        return User_id;
    }

    public void setUser_id(int user_id) {
        User_id = user_id;
    }

    public int getLike() {
        return Like;
    }

    public void setLike(int like) {
        Like = like;
    }

    public int getDislaid() {
        return Dislaid;
    }

    public void setDislaid(int dislaid) {
        Dislaid = dislaid;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getType_violation() {
        return Type_violation;
    }

    public void setType_violation(String type_violation) {
        Type_violation = type_violation;
    }

    public LabelsMap[] getLabelsMaps() {
        return LabelsMaps;
    }

    public void setLabelsMaps(LabelsMap[] labelsMaps) {
        LabelsMaps = labelsMaps;
    }

    public Message[] getMessages() {
        return Messages;
    }

    public void setMessages(Message[] messages) {
        Messages = messages;
    }

    public PhotoViolation[] getPhotoViolations() {
        return PhotoViolations;
    }

    public void setPhotoViolations(PhotoViolation[] photoViolations) {
        PhotoViolations = photoViolations;
    }

    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public String getBody_observation() {
        return Body_observation;
    }

    public void setBody_observation(String body_observation) {
        Body_observation = body_observation;
    }
}

