package kondratkov.ermineapps.observerapp.view.profile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    @BindView(R.id.editText_registration_name)EditText editText_registration_name;
    @BindView(R.id.editText_registration_password)EditText editText_registration_password;
    @BindView(R.id.editText_registration_twopassword)EditText editText_registration_twopassword;
    @BindView(R.id.editText_registration_city)EditText editText_registration_city;

    @BindView(R.id.frameLayout_registration)FrameLayout frameLayout_registration;

    private static ApiInterface mApiInterface;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        overridePendingTransition(R.anim.add_activity_alpha_show, R.anim.add_activity_alpha_hide);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               RegistrationActivity.this.finish();
            }
        });

        ButterKnife.bind(this);

        frameLayout_registration.setVisibility(View.GONE);

        mApiInterface = Controller.getApi();
        user = new User();
    }

    @OnClick(R.id.button_registration_sign_up)
    public void onClickSignUp(View view){
        if(String.valueOf(editText_registration_name.getText()).replaceAll(" ", "").length()==0){
            Toast.makeText(RegistrationActivity.this, "Введите имя", Toast.LENGTH_SHORT).show();
        }else if(String.valueOf(editText_registration_password.getText()).replaceAll(" ", "").length()==0){
            Toast.makeText(RegistrationActivity.this, "Введите пароль", Toast.LENGTH_SHORT).show();
        }else if(String.valueOf(editText_registration_password.getText()).equals(String.valueOf(editText_registration_twopassword.getText()))==false){
            String s1 = String.valueOf(editText_registration_password.getText());
            String s2 = String.valueOf(editText_registration_twopassword.getText());

            Toast.makeText(RegistrationActivity.this, "Пароли не совподают", Toast.LENGTH_SHORT).show();
        }else if(String.valueOf(editText_registration_city.getText()).replaceAll(" ", "").length()==0){
            Toast.makeText(RegistrationActivity.this, "Введите город", Toast.LENGTH_SHORT).show();
        }else {
            user.setName(String.valueOf(editText_registration_name.getText()));
            user.setPassword(String.valueOf(editText_registration_password.getText()));
            user.setEmail("1");
            user.setCityName(String.valueOf(editText_registration_city.getText()));
            frameLayout_registration.setVisibility(View.VISIBLE);
            onRequest();
            //RegistrationActivity.this.finish();
        }
    }

    @OnClick(R.id.button_registration_cancel)
    public void onClickCancel(View view){
        RegistrationActivity.this.finish();
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.add_activity_alpha_show, R.anim.add_activity_alpha_hide);
    }

    public void onRequest(){
        frameLayout_registration.setVisibility(View.GONE);
        mApiInterface.register(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                String s = String.valueOf(response.body());
                int is = response.code();
                if(response.code()>199 && response.code()<300 && response.body()!=null){
                    user = response.body();
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {

                            MyApplication.getInstance().setUser(user);
                            RegistrationActivity.this.finish();
                        }
                    });
                }else{
                    Toast.makeText(RegistrationActivity.this, "ошибка связи!", Toast.LENGTH_SHORT).show();
                    frameLayout_registration.setVisibility(View.GONE);
                }

            }
            @Override
            public void onFailure(Call<User>call, Throwable t) {
                Toast.makeText(RegistrationActivity.this, "ошибка связи!", Toast.LENGTH_SHORT).show();
                frameLayout_registration.setVisibility(View.GONE);
            }
        });
    }
}
