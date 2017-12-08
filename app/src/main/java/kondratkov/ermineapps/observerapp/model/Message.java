package kondratkov.ermineapps.observerapp.model;

import java.io.File;

/**
 * Created by kondratkov on 26.11.2017.
 */

public class Message {

    private int Id;
    private String Body;
    private String Date;
    private int User_id;
    private String User_name;
    private int Like;
    private int Dislike;
    private int Violation_id;
    private Comment[] Comments;
    private File Photo;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
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

    public int getDislike() {
        return Dislike;
    }

    public void setDislike(int dislike) {
        Dislike = dislike;
    }

    public int getViolation_id() {
        return Violation_id;
    }

    public void setViolation_id(int violation_id) {
        Violation_id = violation_id;
    }

    public Comment[] getComments() {
        return Comments;
    }

    public void setComments(Comment[] comments) {
        Comments = comments;
    }

    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public File getPhoto() {
        return Photo;
    }

    public void setPhoto(File photo) {
        Photo = photo;
    }
}
