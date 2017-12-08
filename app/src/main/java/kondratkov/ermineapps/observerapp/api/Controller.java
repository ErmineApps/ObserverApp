package kondratkov.ermineapps.observerapp.api;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import kondratkov.ermineapps.observerapp.model.User;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by kondratkov on 08.12.2017.
 */

public class Controller {
    static final String BASE_URL = "http://46.164.249.114/";
    static SharedPreferences mSharedPreferences;

    public static ApiInterface getApi()  {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        String d = retrofit.toString();

        ApiInterface umoriliApi = retrofit.create(ApiInterface.class);
        return umoriliApi;
    }

    public static ApiInterface postApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface umoriliApi = retrofit.create(ApiInterface.class);
        return umoriliApi;
    }
}
