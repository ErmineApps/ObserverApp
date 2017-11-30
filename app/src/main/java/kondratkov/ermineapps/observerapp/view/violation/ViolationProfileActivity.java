package kondratkov.ermineapps.observerapp.view.violation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kondratkov.ermineapps.observerapp.R;
import kondratkov.ermineapps.observerapp.model.AllComments;
import kondratkov.ermineapps.observerapp.model.City;
import kondratkov.ermineapps.observerapp.model.Comment;
import kondratkov.ermineapps.observerapp.model.LabelsMap;
import kondratkov.ermineapps.observerapp.model.Message;
import kondratkov.ermineapps.observerapp.model.PhotoViolation;
import kondratkov.ermineapps.observerapp.model.Region;
import kondratkov.ermineapps.observerapp.model.User;
import kondratkov.ermineapps.observerapp.model.Violation;
import kondratkov.ermineapps.observerapp.representation.Convector_DP_PX;
import kondratkov.ermineapps.observerapp.representation.DateTimePepresentation;
import kondratkov.ermineapps.observerapp.representation.DecodeImage;
import kondratkov.ermineapps.observerapp.representation.TypeViolationToString;

public class ViolationProfileActivity extends AppCompatActivity {

    @BindView(R.id.linearLayout_violation_profile_image)LinearLayout linearLayout_violation_profile_image;
    @BindView(R.id.textView_violation_profile_address)TextView textView_violation_profile_address;
    @BindView(R.id.textView_violation_profile_body)TextView textView_violation_profile_body;
    @BindView(R.id.textView_violation_profile_date)TextView textView_violation_profile_date;
    @BindView(R.id.textView_violation_profile_number_message)TextView textView_violation_profile_number_message;
    @BindView(R.id.recyclerView_violation_profile_message)RecyclerView recyclerView_violation_profile_message;
    @BindView(R.id.scrollView_violation_profile)ScrollView scrollView_violation_profile;

    private Violation violation;
    private Toolbar toolbar;
    private PhotoViolation [] photoViolations=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violation_profile);
        
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViolationProfileActivity.this.finish();
            }
        });

        ButterKnife.bind(this);


        testCreateViolation();
        testCreatePhoto();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView_violation_profile_message.setLayoutManager(layoutManager);

        displayDataViolation();
        scrollView_violation_profile.scrollTo(0, 0);
    }

    @Override
    public void onStart() {
        super.onStart();
        scrollView_violation_profile.scrollTo(0, 0);
    }

    public synchronized void displayDataViolation(){

        toolbar.setTitle(TypeViolationToString.typeToString(violation.getType_violation(),
                getResources().getStringArray(R.array.array_violations_enum),
                getResources().getStringArray(R.array.array_violations)));

        textView_violation_profile_address.setText(violation.getAddress());
        textView_violation_profile_date.setText(DateTimePepresentation.dateDisplayFormat(violation.getDate(), this));
        textView_violation_profile_body.setText(violation.getBody_observation());
        textView_violation_profile_number_message.setText("Коменнтарии "+String.valueOf(violation.getMessages().length));

        List<AllComments>messages = new ArrayList<AllComments>();
        AllComments allComments;
        for(int i = 0; i<violation.getMessages().length; i++){
            allComments = new AllComments();
            allComments.setId(violation.getMessages()[i].getId());
            allComments.setBody(violation.getMessages()[i].getBody());
            allComments.setDate(violation.getMessages()[i].getDate());
            allComments.setLike(violation.getMessages()[i].getLike());
            allComments.setDislike(violation.getMessages()[i].getDislike());
            allComments.setUser_name(violation.getMessages()[i].getUser_name());
            allComments.setTypeMessage(true);
            messages.add(allComments);

            for(int j=0; j<violation.getMessages()[i].getComments().length; j++){
                allComments = new AllComments();
                allComments.setId(violation.getMessages()[i].getComments()[j].getId());
                allComments.setBody(violation.getMessages()[i].getComments()[j].getBody());
                allComments.setDate(violation.getMessages()[i].getComments()[j].getDate());
                allComments.setLike(violation.getMessages()[i].getComments()[j].getLike());
                allComments.setDislike(violation.getMessages()[i].getComments()[j].getDislike());
                allComments.setUser_name(violation.getMessages()[i].getComments()[j].getUser_name());
                allComments.setTypeMessage(false);
                messages.add(allComments);
            }
        }

        ViolationProfileAdapter mViolationProfileAdapter = new ViolationProfileAdapter(getApplicationContext(), messages);
        recyclerView_violation_profile_message.setAdapter(mViolationProfileAdapter);
        recyclerView_violation_profile_message.getAdapter().notifyDataSetChanged();
        scrollView_violation_profile.scrollTo(0, 0);

        displayPhoto();

    }
    public void displayPhoto(){

        if(photoViolations==null){
            ImageView imageView = new ImageView(ViolationProfileActivity.this);
            imageView.setImageResource(R.drawable.ic_nophoto);

            ViewGroup.LayoutParams imageViewLayoutParams = new ViewGroup.LayoutParams(Convector_DP_PX.dpToPx(150, this), Convector_DP_PX.dpToPx(150, this));
            imageView.setLayoutParams(imageViewLayoutParams);
            imageView.setPadding(Convector_DP_PX.dpToPx(5, this), Convector_DP_PX.dpToPx(5, this), Convector_DP_PX.dpToPx(5, this), Convector_DP_PX.dpToPx(5, this));
            linearLayout_violation_profile_image.addView(imageView);
        }else{
            for(int i=0; i<photoViolations.length; i++){

                ImageView imageView = new ImageView(ViolationProfileActivity.this);
                imageView.setImageBitmap(DecodeImage.decodeFileToBitmap(photoViolations[i].getPhoto()));

                ViewGroup.LayoutParams imageViewLayoutParams = new ViewGroup.LayoutParams(Convector_DP_PX.dpToPx(150, this), Convector_DP_PX.dpToPx(150, this));
                imageView.setLayoutParams(imageViewLayoutParams);
                imageView.setPadding(Convector_DP_PX.dpToPx(5, this), Convector_DP_PX.dpToPx(5, this), Convector_DP_PX.dpToPx(5, this), Convector_DP_PX.dpToPx(5, this));

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ViolationProfileActivity.this, "# (123123Long click)", Toast.LENGTH_SHORT).show();
                    }
                });

                linearLayout_violation_profile_image.addView(imageView);
            }
        }

    }

    @OnClick(R.id.textView_violation_profile_addcomment)
    public void onClick(View view){
        Intent intent = new Intent(ViolationProfileActivity.this, ViolationAddMessageActivity.class);
        startActivity(intent);
    }

    public void testCreatePhoto(){
        photoViolations = new PhotoViolation[4];
        for(int i = 0; i<photoViolations.length; i++){
            PhotoViolation photoViolation = new PhotoViolation();
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test_violation_photo);
            File file = getApplicationContext().getFilesDir();
            File imageFile = new File(file, "fff"+".jpg");
            OutputStream os;
            try{
                os = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                os.flush();
                os.close();
            }catch (Exception e){}
            photoViolation.setId(0);
            photoViolation.setPhoto(DecodeImage.decodeBitmapToFile(imageFile,150,150));
            photoViolation.setViolation_id(0);

            photoViolations[i]=photoViolation;
        }

    }
    public void testCreateViolation(){

        City city = new City();
        city.setId(0);
        city.setName("Воронеж12");
        Region region = new Region();
        region.setId(0);
        region.setName("Воронежская область");
        City [] cities = {city};
        region.setCities(cities);

        User user = new User();
        user.setId(0);
        user.setName("kolya");

        LabelsMap labelsMap = new LabelsMap();
        labelsMap.setId(0);
        labelsMap.setCities(city);
        labelsMap.setCity_id(0);
        labelsMap.setDate_creation("2017-11-27T02:11:25");
        labelsMap.setViolation_id(0);
        LabelsMap []labelsMaps = {labelsMap};

        Message[] messages = new Message[7];
        for(int i = 0; i<messages.length; i++){
            Message message = new Message();
            message.setId(i);
            message.setBody("Просто так и надо все время делать арывлоаод лдоывао шывао шаш щоывоа шщоывшщыао ывлоарр ывагш");
            message.setDate("2017-11-27T02:11:25");
            message.setLike(12);
            message.setUser_name("Vasya");
            Comment[]comments = new Comment[3];
            for(int j = 0; j<comments.length; j++){
                Comment comment = new Comment();
                comment.setId(j);
                comment.setDate("2017-11-27T02:11:25");
                comment.setBody("Commetn hdljo dana;;fasd;fasf");
                comment.setMessage_id(i);
                comment.setUser_name("kolya");
                comments[j]= comment;
            }
            message.setComments(comments);
            messages[i]=message;
        }

            violation = new Violation();
            violation.setId(0);
            violation.setDate("2017-11-27T02:11:25");
            violation.setLabelsMaps(labelsMaps);
            violation.setMessages(messages);
            violation.setType_violation(getResources().getStringArray(R.array.array_violations_enum)[2]);
            violation.setUser_id(0);
            violation.setBody_observation("Просто нужно понять и все!");
            violation.setUser_name("Kolzy");
            violation.setAddress("Воронеж, ул.Ворошилова 12");

    }
}
