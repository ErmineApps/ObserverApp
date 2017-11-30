package kondratkov.ermineapps.observerapp.model;

/**
 * Created by kondratkov on 28.11.2017.
 */

public class AllComments {
    private int Id;
    private int Message_id;
    private String Body;
    private int User_id;
    private String User_name;
    private String Date;
    private int Like;
    private int Dislike;
    private boolean TypeMessage;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getMessage_id() {
        return Message_id;
    }

    public void setMessage_id(int message_id) {
        Message_id = message_id;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public int getUser_id() {
        return User_id;
    }

    public void setUser_id(int user_id) {
        User_id = user_id;
    }

    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
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

    public boolean isTypeMessage() {
        return TypeMessage;
    }

    public void setTypeMessage(boolean typeMessage) {
        TypeMessage = typeMessage;
    }
}
