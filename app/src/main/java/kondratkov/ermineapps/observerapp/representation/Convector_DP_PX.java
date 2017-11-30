package kondratkov.ermineapps.observerapp.representation;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by kondratkov on 28.11.2017.
 */

public class Convector_DP_PX {

    public static int dpToPx(int dp, Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px =Math.round(dp *(displayMetrics.xdpi /DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}
