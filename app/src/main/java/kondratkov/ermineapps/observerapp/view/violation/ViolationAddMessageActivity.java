package kondratkov.ermineapps.observerapp.view.violation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import kondratkov.ermineapps.observerapp.R;

public class ViolationAddMessageActivity extends AppCompatActivity {

    @BindView(R.id.editText_add_message)EditText editText_add_message;
    @BindView(R.id.imageButton_add_message)ImageButton imageButton_add_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violation_add_message);
        overridePendingTransition(R.anim.add_activity_alpha_show, R.anim.add_activity_alpha_hide);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        ButterKnife.bind(this);

        editText_add_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                String s = String.valueOf(editText_add_message.getText());
                if(s.replaceAll(" ", "").length()==0){
                    imageButton_add_message.setVisibility(View.GONE);
                }else{
                    imageButton_add_message.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick(R.id.imageButton_add_message)
    public void onClickAdd(View view){
        Toast.makeText(ViolationAddMessageActivity.this, "# (click)", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.button_add_message_finish)
    public void onClickFinish(View view){
        ViolationAddMessageActivity.this.finish();
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.add_activity_alpha_show, R.anim.add_activity_alpha_hide);
    }
}
