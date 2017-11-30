package kondratkov.ermineapps.observerapp.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import kondratkov.ermineapps.observerapp.R;
import kondratkov.ermineapps.observerapp.view.addviolation.AddViolationActivity;
import kondratkov.ermineapps.observerapp.view.profile.ProfileActivity;

/**
 * Created by kondratkov on 30.11.2017.
 */

public class AddDialogs {

    public static void dialog_no_authorization(final AppCompatActivity appCompatActivity){
        final Dialog dialog = new Dialog(appCompatActivity);
        dialog.setTitle("");
        dialog.setContentView(R.layout.dialog_no_authorization);
        appCompatActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        Button btnClose = (Button) dialog.getWindow().findViewById(
               R.id.button_dialog_no_sign_cancel);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
