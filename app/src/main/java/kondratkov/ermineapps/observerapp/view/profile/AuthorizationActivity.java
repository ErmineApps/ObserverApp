package kondratkov.ermineapps.observerapp.view.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kondratkov.ermineapps.observerapp.MyApplication;
import kondratkov.ermineapps.observerapp.R;
import kondratkov.ermineapps.observerapp.api.ApiInterface;
import kondratkov.ermineapps.observerapp.api.Controller;
import kondratkov.ermineapps.observerapp.model.User;
import kondratkov.ermineapps.observerapp.view.addviolation.AddViolationActivity;
import kondratkov.ermineapps.observerapp.view.maplabels.MapLabelsActivity;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class AuthorizationActivity extends AppCompatActivity {

    @BindView(R.id.editText_authorization_name)EditText editText_authorization_name;
    @BindView(R.id.editText_authorization_Password)EditText editText_authorization_Password;
    @BindView(R.id.checkBox_authorization_avto)CheckBox checkBox_authorization_avto;
    @BindView(R.id.checkBox_authorization_save)CheckBox checkBox_authorization_save;

    @BindView(R.id.frameLayout_authorization)FrameLayout frameLayout_authorization;

    private static ApiInterface mApiInterface;

    public SharedPreferences sPref;
    public String USER_NAME;
    public String PASSWORD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        overridePendingTransition(R.anim.add_activity_alpha_show, R.anim.add_activity_alpha_hide);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuthorizationActivity.this, MapLabelsActivity.class);
                startActivity(intent);
                AuthorizationActivity.this.finish();
            }
        });

        mApiInterface = Controller.getApi();
        ButterKnife.bind(this);

        frameLayout_authorization.setVisibility(View.GONE);

        sPref = PreferenceManager.getDefaultSharedPreferences(this);

        checkBox_authorization_save.setChecked(sPref.getBoolean("boolean_authorization_save", false));
        checkBox_authorization_save.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SharedPreferences.Editor ed = sPref.edit();
                ed.putBoolean("boolean_authorization_save", isChecked);
                ed.commit();
            }
        });

        checkBox_authorization_avto.setChecked(sPref.getBoolean("boolean_authorization_avto", false));
        checkBox_authorization_avto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SharedPreferences.Editor ed = sPref.edit();
                ed.putBoolean("boolean_authorization_avto", isChecked);
                ed.commit();
            }
        });

        authorizationSave();
        authorizationAvto();
    }

    private void authorizationSave() {
        if(sPref.getBoolean("boolean_authorization_save", false)){
            editText_authorization_name.setText(sPref.getString("authorization_name", ""));
            editText_authorization_Password.setText(sPref.getString("authorization_password", ""));
        }
    }

    private void authorizationAvto() {
        if(sPref.getBoolean("boolean_authorization_avto", false)){
            frameLayout_authorization.setVisibility(View.VISIBLE);
            new UrlConnectionTask().execute("grant_type=password&username="+String.valueOf(editText_authorization_name.getText())+
                    "&password="+String.valueOf(editText_authorization_Password.getText()));
        }
    }

    @OnClick(R.id.button_authorization_sign)
    public void onClickSign(View view){


        if(String.valueOf(editText_authorization_name.getText()).replaceAll(" ", "").length()==0){
            Toast.makeText(AuthorizationActivity.this, "Введите имя", Toast.LENGTH_SHORT).show();
        }else if(String.valueOf(editText_authorization_Password.getText()).replaceAll(" ", "").length()==0){
            Toast.makeText(AuthorizationActivity.this, "Введите пароль", Toast.LENGTH_SHORT).show();
        }else {
            frameLayout_authorization.setVisibility(View.VISIBLE);

            if(sPref.getBoolean("boolean_authorization_save", false)){
                //MyApplication.getInstance().getUser().setToken("233");
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString("authorization_name", String.valueOf(editText_authorization_name.getText()));
                ed.putString("authorization_password", String.valueOf(editText_authorization_Password.getText()));
                ed.commit();

                new UrlConnectionTask().execute("grant_type=password&username="+String.valueOf(editText_authorization_name.getText())+
                        "&password="+String.valueOf(editText_authorization_Password.getText()));

            }else{
                MyApplication.getInstance().getUser().setToken("233");
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString("authorization_name", "");
                ed.putString("authorization_password", "");
                ed.commit();

                new UrlConnectionTask().execute("grant_type=password&username="+String.valueOf(editText_authorization_name.getText())+
                        "&password="+String.valueOf(editText_authorization_Password.getText()));
            }
        }
    }

    public void enterUser(String result){
        User user = new User();
        try {
            JSONObject json = new JSONObject(result);
            user.setToken(json.getString("token_type")+" "+json.getString("access_token"));
            MyApplication.getInstance().setUser(user);

            onRequest();
        } catch (JSONException e) {
            e.printStackTrace();

            MyApplication.getInstance().setUser(user);

            Intent intent = new Intent(AuthorizationActivity.this, MapLabelsActivity.class);
            startActivity(intent);
            AuthorizationActivity.this.finish();
        }
    }

    public void onRequest(){
        mApiInterface.getUser(MyApplication.getInstance().getUser().getToken()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                String s = String.valueOf(response.body());
                int is = response.code();
                if(response.code()>199 && response.code()<300 && response.body()!=null){
                    final User user = response.body();
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {

                            MyApplication.getInstance().getUser().setName(user.getName());
                            MyApplication.getInstance().getUser().setCityName(user.getCityName());
                            MyApplication.getInstance().getUser().setId(user.getId());

                            Intent intent = new Intent(AuthorizationActivity.this, MapLabelsActivity.class);
                            startActivity(intent);
                            AuthorizationActivity.this.finish();
                        }
                    });
                }else{
                    frameLayout_authorization.setVisibility(View.GONE);
                    Toast.makeText(AuthorizationActivity.this, "ошибка связи!", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<User>call, Throwable t) {
                frameLayout_authorization.setVisibility(View.GONE);
                Toast.makeText(AuthorizationActivity.this, "ошибка связи!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.button_authorization_Registr)
    public void onClickRegistr(View view){
        Intent intent = new Intent(AuthorizationActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.add_activity_alpha_show, R.anim.add_activity_alpha_hide);
    }

    class UrlConnectionTask extends AsyncTask<String, Void, String> {
        public int code=0;
        @Override
        protected String doInBackground(String... params) {

            String result = "";
            OkHttpClient client = new OkHttpClient();

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody formBody = RequestBody.create(JSON, params[0]);

            Request request = new Request.Builder()
                    .header("Content-Type", "x-www-form-urlencoded")
                    .url("http://46.164.249.114/token")
                    .post(formBody)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                result = response.body().string();
                code = response.code();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if(code>=200 && code<300){
                enterUser(result);
            }else {
                frameLayout_authorization.setVisibility(View.GONE);
                Toast.makeText(AuthorizationActivity.this, "Ошибка сети!", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }
}
