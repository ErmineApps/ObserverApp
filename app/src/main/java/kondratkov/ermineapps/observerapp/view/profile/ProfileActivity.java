package kondratkov.ermineapps.observerapp.view.profile;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kondratkov.ermineapps.observerapp.MyApplication;
import kondratkov.ermineapps.observerapp.R;
import kondratkov.ermineapps.observerapp.model.PhotoViolation;
import kondratkov.ermineapps.observerapp.model.User;
import kondratkov.ermineapps.observerapp.representation.Convector_DP_PX;
import kondratkov.ermineapps.observerapp.view.addviolation.AddViolationActivity;
import kondratkov.ermineapps.observerapp.view.maplabels.MapLabelsActivity;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.imageView_profile_user)ImageView imageView_profile_user;
    @BindView(R.id.textView_profile_name)TextView textView_profile_name;
    @BindView(R.id.textView_profile_city)TextView textView_profile_city;
    @BindView(R.id.checkBox_profile_anonym)CheckBox checkBox_profile_anonym;
    @BindView(R.id.checkBox_profile_avto)CheckBox checkBox_profile_avto;

    static final int GALLERY_REQUEST = 1;
    static final int CAMERA_RESULT = 2;

    public SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        overridePendingTransition(R.anim.add_activity_alpha_show, R.anim.add_activity_alpha_hide);

        ButterKnife.bind(this);
        sPref = PreferenceManager.getDefaultSharedPreferences(this);

        checkBox_profile_anonym.setChecked(sPref.getBoolean("boolean_profile_anonym", false));
        checkBox_profile_anonym.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SharedPreferences.Editor ed = sPref.edit();
                ed.putBoolean("boolean_profile_anonym", isChecked);
                ed.commit();
            }
        });

        checkBox_profile_avto.setChecked(sPref.getBoolean("boolean_authorization_avto", false));
        checkBox_profile_avto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SharedPreferences.Editor ed = sPref.edit();
                ed.putBoolean("boolean_authorization_avto", isChecked);
                ed.commit();
            }
        });

        textView_profile_name.setText(MyApplication.getInstance().getUser().getName());
        textView_profile_city.setText(MyApplication.getInstance().getUser().getCityName());

        MyApplication.getInstance().getNavigationViewMyApp().setAppCompatActivity(ProfileActivity.this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @OnClick(R.id.button_profile_close_profile)
    public void onClick_close_profile(View view){
        Intent intent = new Intent(ProfileActivity.this, MapLabelsActivity.class);
        startActivity(intent);
        User user = new User();
        MyApplication.getInstance().setUser(user);
        ProfileActivity.this.finish();
    }

    @OnClick(R.id.button_profile_edit_name)
    public void onClick_edit_name(View view){
        start_dialog_new_name();
    }

    @OnClick(R.id.button_profile_edit_photo)
    public void onClick_edit_photo(View view){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
        startActivityForResult(cameraIntent, CAMERA_RESULT);
    }

    @OnClick(R.id.button_profile_edit_photo_gallery)
    public void onClick_photo_gallery(View view){
        Intent photoPickerIntent = new Intent();
        photoPickerIntent.setType("image/*");
        photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bitmap bitmap=null;

        switch (requestCode) {
            case CAMERA_RESULT:
                try{
                    bitmap = (Bitmap) data.getExtras().get("data");
                }catch (Exception e){}

                if(bitmap!=null){
                    imageView_profile_user.setImageBitmap(bitmap);

                    imageView_profile_user.setPadding(Convector_DP_PX.dpToPx(5, this), Convector_DP_PX.dpToPx(5, this), Convector_DP_PX.dpToPx(5, this), Convector_DP_PX.dpToPx(5, this));

                    imageView_profile_user.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(ProfileActivity.this, "# (123123Long click)", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if(bitmap!=null){
                        imageView_profile_user.setImageBitmap(bitmap);
                        imageView_profile_user.setPadding(Convector_DP_PX.dpToPx(5, this), Convector_DP_PX.dpToPx(5, this), Convector_DP_PX.dpToPx(5, this), Convector_DP_PX.dpToPx(5, this));

                        imageView_profile_user.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(ProfileActivity.this, "# (123123Long click)", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                break;
        }
    }

    private void start_dialog_new_name() {

        final Dialog dialog = new Dialog(ProfileActivity.this);
        dialog.setTitle("");
        dialog.setContentView(R.layout.dialog_new_name);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        Button btnClose = (Button) dialog.getWindow().findViewById(
                R.id.button_dialog_new_name_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final Button btnYes = (Button) dialog.getWindow().findViewById(
                R.id.button_dialog_new_name_yes);

        final EditText editText_dialog_new_name_name = (EditText) dialog.getWindow().findViewById(R.id.editText_dialog_new_name_name);

        final EditText editText_dialog_new_name_password = (EditText) dialog.getWindow().findViewById(R.id.editText_dialog_new_name_password);

//
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(String.valueOf(editText_dialog_new_name_name.getText()).replaceAll(" ", "").length()==0){
                    Toast.makeText(ProfileActivity.this, "Введите имя", Toast.LENGTH_SHORT).show();
                }else if(String.valueOf(editText_dialog_new_name_password.getText()).replaceAll(" ", "").length()==0){
                    Toast.makeText(ProfileActivity.this, "Введите пароль", Toast.LENGTH_SHORT).show();
                }else{
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.add_activity_alpha_show, R.anim.add_activity_alpha_hide);
    }

}
