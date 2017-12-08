package kondratkov.ermineapps.observerapp;

import android.app.Application;

import kondratkov.ermineapps.observerapp.model.LabelsMap;
import kondratkov.ermineapps.observerapp.model.User;
import kondratkov.ermineapps.observerapp.model.Violation;
import kondratkov.ermineapps.observerapp.view.navigation.NavigationViewMyApp;

/**
 * Created by kondratkov on 19.11.2017.
 */

public class MyApplication extends Application {

    private NavigationViewMyApp mNavigationViewMyApp;
    private Violation mViolation;
    private LabelsMap mLabelsMap;
    private boolean newViolationNewActivity = false;
    private User mUser;


    private static MyApplication singleton;
    // Возвращает экземпляр данного класса
    public static MyApplication getInstance() {
        return singleton;
    }

    @Override
    public final void onCreate() {
        super.onCreate();
        singleton = this;

    }

    public NavigationViewMyApp getNavigationViewMyApp() {
        return mNavigationViewMyApp;
    }

    public void setNavigationViewMyApp(NavigationViewMyApp navigationViewMyApp) {
        mNavigationViewMyApp = navigationViewMyApp;
    }

    public Violation getViolation() {
        return mViolation;
    }

    public void setViolation(Violation violation) {
        mViolation = violation;
    }

    public static MyApplication getSingleton() {
        return singleton;
    }

    public static void setSingleton(MyApplication singleton) {
        MyApplication.singleton = singleton;
    }

    public boolean isNewViolationNewActivity() {
        return newViolationNewActivity;
    }

    public void setNewViolationNewActivity(boolean newViolationNewActivity) {
        this.newViolationNewActivity = newViolationNewActivity;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public LabelsMap getLabelsMap() {
        return mLabelsMap;
    }

    public void setLabelsMap(LabelsMap labelsMap) {
        mLabelsMap = labelsMap;
    }
}
