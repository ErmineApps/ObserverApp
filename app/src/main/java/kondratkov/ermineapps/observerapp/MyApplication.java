package kondratkov.ermineapps.observerapp;

import android.app.Application;

import kondratkov.ermineapps.observerapp.view.navigation.NavigationViewMyApp;

/**
 * Created by kondratkov on 19.11.2017.
 */

public class MyApplication extends Application {

    private NavigationViewMyApp mNavigationViewMyApp;

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
}
