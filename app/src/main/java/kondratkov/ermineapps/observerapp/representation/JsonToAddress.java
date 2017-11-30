package kondratkov.ermineapps.observerapp.representation;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kondratkov on 30.11.2017.
 */

public class JsonToAddress {

    public static String getAddress(String s){
        String ADDRESS = "";
        try {
            JSONObject json = new JSONObject(s);
            ADDRESS =
                    //город
                    json.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(3).getString("short_name")+" "+
                            // улица
                            json.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(1).getString("short_name")+" "+
                            // дом
                            json.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(0).getString("short_name")
                    ;
            String STREET = json.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(1).getString("short_name") ;
            String STREETNUMBER = json.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(0).getString("short_name");

            for (int j =0 ; j<json.getJSONArray("results").length(); j++ ){
                Log.d("qwerty", "");
                Log.d("qwerty", "j " + String.valueOf(json.getJSONArray("results").getJSONObject(j).getJSONArray("address_components")));
                for(int i = 0; i<json.getJSONArray("results").getJSONObject(j).getJSONArray("address_components").length(); i++){
                    Log.d("qwerty", "");
                    Log.d("qwerty", i+" "+String.valueOf(json.getJSONArray("results").getJSONObject(j).getJSONArray("address_components").getJSONObject(i)));
                }
            }/**/
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ADDRESS;
    }
}
