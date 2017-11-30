package kondratkov.ermineapps.observerapp.view.addviolation;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kondratkov.ermineapps.observerapp.MyApplication;
import kondratkov.ermineapps.observerapp.R;
import kondratkov.ermineapps.observerapp.representation.Convector_DP_PX;
import kondratkov.ermineapps.observerapp.view.maplabels.MapLabelsActivity;

public class AddViolationActivity extends AppCompatActivity {

    @BindView(R.id.fab_add_violation_base)
    FloatingActionButton fab_add_violation_base;
    @BindView(R.id.fab_1)
    FloatingActionButton fab1;
    @BindView(R.id.fab_2)
    FloatingActionButton fab2;
    @BindView(R.id.fab_3)
    FloatingActionButton fab3;

    @BindView(R.id.linearLayout_add_violation_image)
    LinearLayout linearLayout_add_violation_image;

    @BindView(R.id.textView_add_violation_type)
    TextView textView_add_violation_type;
    @BindView(R.id.editText_add_violation_address)
    EditText editText_add_violation_address;
    @BindView(R.id.editText_add_violation_date)
    EditText editText_add_violation_date;
    @BindView(R.id.editText_add_violation_body)
    EditText editText_add_violation_body;


    private boolean FAB_Status = false;

    static final int GALLERY_REQUEST = 1;
    static final int CAMERA_RESULT = 2;
    //Animations
    Animation show_fab_1;
    Animation hide_fab_1;
    Animation show_fab_2;
    Animation hide_fab_2;
    Animation show_fab_3;
    Animation hide_fab_3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_violation);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Animations
        show_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_show);
        hide_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_hide);
        show_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_show);
        hide_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_hide);
        show_fab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_show);
        hide_fab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_hide);

        fab_add_violation_base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FAB_Status == false) {
                    //Display FAB menu
                    expandFAB();
                    FAB_Status = true;
                } else {
                    //Close FAB menu
                    hideFAB();
                    FAB_Status = false;
                }
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), "Floating Action Button 1", Toast.LENGTH_SHORT).show();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
                startActivityForResult(cameraIntent, CAMERA_RESULT);
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                Intent photoPickerIntent = new Intent();
                photoPickerIntent.setType("image/*");
                photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });

        editText_add_violation_body.setText(MyApplication.getInstance().getViolation().getBody_observation());
        editText_add_violation_address.setText(MyApplication.getInstance().getViolation().getAddress());
        editText_add_violation_date.setText(MyApplication.getInstance().getViolation().getDate());

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        ImageView imageView;
        ViewGroup.LayoutParams imageViewLayoutParams;
        Bitmap bitmap;

        switch (requestCode) {
            case CAMERA_RESULT:
                bitmap = (Bitmap) data.getExtras().get("data");
                //ImageView ivCamera = (ImageView) findViewById(R.id.iv_camera);
                //ivCamera.setImageBitmap(thumbnail);
                imageView = new ImageView(AddViolationActivity.this);
                imageView.setImageBitmap(bitmap);

                imageViewLayoutParams = new ViewGroup.LayoutParams(Convector_DP_PX.dpToPx(150, this), Convector_DP_PX.dpToPx(150, this));
                imageView.setLayoutParams(imageViewLayoutParams);
                imageView.setPadding(Convector_DP_PX.dpToPx(5, this), Convector_DP_PX.dpToPx(5, this), Convector_DP_PX.dpToPx(5, this), Convector_DP_PX.dpToPx(5, this));

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(AddViolationActivity.this, "# (123123Long click)", Toast.LENGTH_SHORT).show();
                    }
                });

                linearLayout_add_violation_image.addView(imageView);
                break;
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    imageView = new ImageView(AddViolationActivity.this);
                    imageView.setImageBitmap(bitmap);

                    imageViewLayoutParams = new ViewGroup.LayoutParams(Convector_DP_PX.dpToPx(150, this), Convector_DP_PX.dpToPx(150, this));
                    imageView.setLayoutParams(imageViewLayoutParams);
                    imageView.setPadding(Convector_DP_PX.dpToPx(5, this), Convector_DP_PX.dpToPx(5, this), Convector_DP_PX.dpToPx(5, this), Convector_DP_PX.dpToPx(5, this));

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(AddViolationActivity.this, "# (123123Long click)", Toast.LENGTH_SHORT).show();
                        }
                    });

                    linearLayout_add_violation_image.addView(imageView);
                }
                break;
        }

    }

    @OnClick(R.id.editText_add_violation_body)
    public void onClick(View view) {
        dialogEditingText();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @OnClick(R.id.button_add_violation)
    public void onClickAdd(View view){
        MyApplication.getInstance().getViolation().setBody_observation(String.valueOf(editText_add_violation_body.getText()));
        MyApplication.getInstance().getViolation().setAddress(String.valueOf(editText_add_violation_address.getText()));
        MyApplication.getInstance().getViolation().setDate(String.valueOf(editText_add_violation_date.getText()));
        MyApplication.getInstance().setNewViolationNewActivity(true);
        this.finish();
    }

    private void expandFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin += (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin += (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(show_fab_1);
        fab1.setClickable(true);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin += (int) (fab2.getWidth() * 1.5);
        layoutParams2.bottomMargin += (int) (fab2.getHeight() * 1.5);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(show_fab_2);
        fab2.setClickable(true);

        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
        layoutParams3.rightMargin += (int) (fab3.getWidth() * 0.25);
        layoutParams3.bottomMargin += (int) (fab3.getHeight() * 1.7);
        fab3.setLayoutParams(layoutParams3);
        fab3.startAnimation(show_fab_3);
        fab3.setClickable(true);
    }

    private void hideFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin -= (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin -= (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(hide_fab_1);
        fab1.setClickable(false);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin -= (int) (fab2.getWidth() * 1.5);
        layoutParams2.bottomMargin -= (int) (fab2.getHeight() * 1.5);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(hide_fab_2);
        fab2.setClickable(false);

        //Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
        layoutParams3.rightMargin -= (int) (fab3.getWidth() * 0.25);
        layoutParams3.bottomMargin -= (int) (fab3.getHeight() * 1.7);
        fab3.setLayoutParams(layoutParams3);
        fab3.startAnimation(hide_fab_3);
        fab3.setClickable(false);
    }


    public void dialogEditingText() {
        final Dialog dialog = new Dialog(AddViolationActivity.this);
        dialog.setTitle("");
        dialog.setContentView(R.layout.dialog_editing_text);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//        Button btnClose = (Button) dialog.getWindow().findViewById(
//                R.id.button_help_close);
//        btnClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
        final Button btnYes = (Button) dialog.getWindow().findViewById(
                R.id.button_dialog_set);

        final EditText et = (EditText) dialog.getWindow().findViewById(R.id.editText_dialog_set);
        et.setText(editText_add_violation_body.getText());
//
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_add_violation_body.setText(et.getText());

                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

                dialog.dismiss();
            }
        });
        dialog.show();

    }

}


//public Bitmap getThumbnail(Uri uri) throws FileNotFoundException, IOException{
//        InputStream input = this.getContentResolver().openInputStream(uri);
//
//        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
//        onlyBoundsOptions.inJustDecodeBounds = true;
//        onlyBoundsOptions.inDither=true;//optional
//        onlyBoundsOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
//        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
//        input.close();
//
//        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1)) {
//            return null;
//        }
//
//        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;
//
//        double ratio = (originalSize > 200) ? (originalSize / 200) : 1.0;
//
//        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
//        bitmapOptions.inDither = true; //optional
//        bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//
//        input = this.getContentResolver().openInputStream(uri);
//        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
//        input.close();
//        return bitmap;
//    }
//
//    private static int getPowerOfTwoForSampleRatio(double ratio){
//        int k = Integer.highestOneBit((int)Math.floor(ratio));
//        if(k==0) return 1;
//        else return k;
//    }