package kondratkov.ermineapps.observerapp.view.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kondratkov.ermineapps.observerapp.R;
import kondratkov.ermineapps.observerapp.view.addviolation.AddViolationActivity;
import kondratkov.ermineapps.observerapp.view.maplabels.MapLabelsActivity;

public class RegistrationActivity extends AppCompatActivity {

    @BindView(R.id.editText_registration_name)EditText editText_registration_name;
    @BindView(R.id.editText_registration_password)EditText editText_registration_password;
    @BindView(R.id.editText_registration_twopassword)EditText editText_registration_twopassword;
    @BindView(R.id.editText_registration_city)EditText editText_registration_city;

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
    }

    @OnClick(R.id.button_registration_sign_up)
    public void onClickSignUp(View view){
        if(String.valueOf(editText_registration_name.getText()).replaceAll(" ", "").length()==0){
            Toast.makeText(RegistrationActivity.this, "Введите имя", Toast.LENGTH_SHORT).show();
        }else if(String.valueOf(editText_registration_password.getText()).replaceAll(" ", "").length()==0){
            Toast.makeText(RegistrationActivity.this, "Введите пароль", Toast.LENGTH_SHORT).show();
        }else if(String.valueOf(editText_registration_password.getText()).equals(String.valueOf(editText_registration_twopassword.getText()))){
            Toast.makeText(RegistrationActivity.this, "Пароли не совподают", Toast.LENGTH_SHORT).show();
        }else if(String.valueOf(editText_registration_city.getText()).replaceAll(" ", "").length()==0){
            Toast.makeText(RegistrationActivity.this, "Введите город", Toast.LENGTH_SHORT).show();
        }else {

            RegistrationActivity.this.finish();
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
}
