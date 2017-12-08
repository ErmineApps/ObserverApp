package kondratkov.ermineapps.observerapp.api;

import kondratkov.ermineapps.observerapp.model.LabelsMap;
import kondratkov.ermineapps.observerapp.model.User;
import kondratkov.ermineapps.observerapp.model.Violation;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

/**
 * Created by kondratkov on 08.12.2017.
 */

public interface ApiInterface {

    //запрос на регистрацию
    @POST("api/Users/Registration")
    Call<User> register(@Body User user);

    //запрос профиля пользователя
    @GET("api/GetUsers")
    Call<User> getUser(@Header("Authorization") String authorization);

    //изменить имя пользователя
    @PUT("api/GetUsers/PutUsers")
    Call<User> putUser(@Header("Authorization") String authorization, @Body User user);

    //добавить новое обращение
    @POST("api/Violations/PostViolations")
    Call<Violation>postViolations(@Body Violation violation);

    //добавить новое обращение
    @POST("api/LabelsMaps/PostLabelsMap")
    Call<LabelsMap>PostLabelsMap(@Body LabelsMap labelsMap);
}
