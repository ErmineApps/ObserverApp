package kondratkov.ermineapps.observerapp.view.references;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kondratkov.ermineapps.observerapp.MyApplication;
import kondratkov.ermineapps.observerapp.R;
import kondratkov.ermineapps.observerapp.model.City;
import kondratkov.ermineapps.observerapp.model.Comment;
import kondratkov.ermineapps.observerapp.model.LabelsMap;
import kondratkov.ermineapps.observerapp.model.Message;
import kondratkov.ermineapps.observerapp.model.Region;
import kondratkov.ermineapps.observerapp.model.User;
import kondratkov.ermineapps.observerapp.model.Violation;


public class ReferencesListActivity extends AppCompatActivity {

    private List<Violation> mViolationsList;

    @BindView(R.id.recyclerView_references) RecyclerView recyclerView_references;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_references_list);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Обращения");

        MyApplication.getInstance().getNavigationViewMyApp().setAppCompatActivity(ReferencesListActivity.this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView_references.setLayoutManager(layoutManager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        startAdapterList();
    }

    private void startAdapterList() {

        TestAddList();

        ReferencesListAdapter mReferencesListAdapter = new ReferencesListAdapter(getApplicationContext(), mViolationsList);
        recyclerView_references.setAdapter(mReferencesListAdapter);
        recyclerView_references.getAdapter().notifyDataSetChanged();
    }

    private void TestAddList() {
        mViolationsList = new ArrayList<Violation>();

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
        labelsMap.setDate_creation("2017-11-26 12:23");
        labelsMap.setViolation_id(0);
        LabelsMap []labelsMaps = {labelsMap};

        Message [] messages = new Message[11];
        for(int i = 0; i<messages.length; i++){
            Message message = new Message();
            message.setId(i);
            message.setBody("Просто так и надо все время делать");
            message.setDate("2017-11-26 12:23");
            message.setLike(12);
            message.setUser_name("Vasya");
            Comment []comments = new Comment[3];
            for(int j = 0; j<comments.length; j++){
                Comment comment = new Comment();
                comment.setId(j);
                comment.setDate("2017-11-26 12:23");
                comment.setBody("Commetn hdljo dana;;fasd;fasf");
                comment.setMessage_id(i);
                comment.setUser_name("kolya");
                comments[j]= comment;
            }
            message.setComments(comments);
        }
        for(int i = 0; i<12; i++){
            Violation violation = new Violation();
            violation.setId(i);
            violation.setDate("2017-11-26 12:23");
            violation.setLabelsMaps(labelsMaps);
            violation.setMessages(messages);
            violation.setType_violation("1");
            violation.setUser_id(0);
            violation.setBody_observation("Просто нужно понять и все!");
            violation.setUser_name("Kolzy");

            mViolationsList.add(violation);
        }

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
}
