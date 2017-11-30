package kondratkov.ermineapps.observerapp.view.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kondratkov.ermineapps.observerapp.MyApplication;
import kondratkov.ermineapps.observerapp.R;
import kondratkov.ermineapps.observerapp.view.addviolation.AddViolationActivity;
import kondratkov.ermineapps.observerapp.view.maplabels.MapLabelsActivity;

public class AuthorizationActivity extends AppCompatActivity {

    @BindView(R.id.editText_authorization_name)EditText editText_authorization_name;
    @BindView(R.id.editText_authorization_Password)EditText editText_authorization_Password;
    @BindView(R.id.checkBox_authorization_avto)CheckBox checkBox_authorization_avto;
    @BindView(R.id.checkBox_authorization_save)CheckBox checkBox_authorization_save;

    public SharedPreferences sPref;

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
        ButterKnife.bind(this);

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
            MyApplication.getInstance().getUser().setToken("233");
            Intent intent = new Intent(AuthorizationActivity.this, ProfileActivity.class);
            startActivity(intent);
            AuthorizationActivity.this.finish();
        }
    }

    @OnClick(R.id.button_authorization_sign)
    public void onClickSign(View view){
        if(String.valueOf(editText_authorization_name.getText()).replaceAll(" ", "").length()==0){
            Toast.makeText(AuthorizationActivity.this, "Введите имя", Toast.LENGTH_SHORT).show();
        }else if(String.valueOf(editText_authorization_Password.getText()).replaceAll(" ", "").length()==0){
            Toast.makeText(AuthorizationActivity.this, "Введите пароль", Toast.LENGTH_SHORT).show();
        }else {
            if(sPref.getBoolean("boolean_authorization_save", false)){
                MyApplication.getInstance().getUser().setToken("233");
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString("authorization_name", String.valueOf(editText_authorization_name.getText()));
                ed.putString("authorization_password", String.valueOf(editText_authorization_Password.getText()));
                ed.commit();
            }else{
                MyApplication.getInstance().getUser().setToken("233");
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString("authorization_name", "");
                ed.putString("authorization_password", "");
                ed.commit();
            }
            Intent intent = new Intent(AuthorizationActivity.this, MapLabelsActivity.class);
            startActivity(intent);
            AuthorizationActivity.this.finish();
        }
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
}
