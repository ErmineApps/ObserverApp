package kondratkov.ermineapps.observerapp.representation;

import java.util.ArrayList;

/**
 * Created by kondratkov on 27.11.2017.
 */

public class TypeViolationToString {

    public static String typeToString(String s, String[]ss, String[]ss2){
        String toType="";

        for(int i = 0; i<ss.length; i++){
            if(s.equals(ss[i])){
                toType = ss2[i];
                break;
            }
        }
        return toType;
    }
}
