package kondratkov.ermineapps.observerapp.representation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kondratkov on 26.11.2017.
 */

public class DataTimePepresentation {

    public String getData_ddMMyyyy(@NonNull String dateString1, Context context) throws Exception{

        String dated = dateString1.substring(0,10)+" "+dateString1.substring(11,19);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = dateFormat.parse(dated);

        java.text.DateFormat mediumDateFormat = DateFormat.getMediumDateFormat(context);

        return mediumDateFormat.format(date);
    }

    public String getData_HHmm(@NonNull String dateString1, Context context) throws Exception{

        String dated = dateString1.substring(0,10)+" "+dateString1.substring(11,19);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = dateFormat.parse(dated);

        java.text.DateFormat mediumDateFormat = DateFormat.getTimeFormat(context);

        return mediumDateFormat.format(date);
    }

    public String dateDisplayFormat(String sDate, Context context){
        String sDateDisplay="";
        long feedTime=0;
        long myTime = System.currentTimeMillis();
        String dated = sDate.substring(0,10)+" "+sDate.substring(11,19);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();
        try {
            date = sdf.parse(dated);
            feedTime = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(feedTime>0){
            if(myTime-feedTime<60000){
                sDateDisplay = DateUtils.formatDateTime(context, feedTime,
                        DateUtils.FORMAT_SHOW_TIME);
            }
            else if(myTime-feedTime<3600000){
                CharSequence timeago;
                timeago = DateUtils.getRelativeTimeSpanString(
                        feedTime, System.currentTimeMillis(),
                        DateUtils.SECOND_IN_MILLIS);
                sDateDisplay= (String) timeago;
            }else{
                sDateDisplay = DateUtils.formatDateTime(context, feedTime,
                        DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_SHOW_TIME);
            }
        }

        return sDateDisplay;
    }

    public String getDate_date_ddMMyyyy(@NonNull Date date, Context context) throws Exception{

        java.text.DateFormat mediumDateFormat = DateFormat.getMediumDateFormat(context);

        return mediumDateFormat.format(date);
    }

    public String getDate_date_HHmm(@NonNull Date date, Context context) throws Exception{

        java.text.DateFormat mediumDateFormat = DateFormat.getTimeFormat(context);

        return mediumDateFormat.format(date);
    }

    public String getDateToString(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(date);
    }

    public Date getStringToDate(@NonNull String dateString1) {
        String dated = dateString1.substring(0,10)+" "+dateString1.substring(11,19);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse(dated);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String getData_long_HHmm(@NonNull long dateL, Context context) throws Exception{
        Date date = new Date(dateL);
        java.text.DateFormat mediumDateFormat = DateFormat.getMediumDateFormat(context);

        return mediumDateFormat.format(date);
    }
}
