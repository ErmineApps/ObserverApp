package kondratkov.ermineapps.observerapp.representation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by kondratkov on 27.11.2017.
 */

public class DecodeImage {

    private static File file;

    public static File decodeBitmapToFile(File mFile, int reqWidth, int reqHeight) {

        file = mFile;
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        try{
            bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
        }catch (Exception e){}

        OutputStream os;
        try{
            os = new FileOutputStream(file);
            Bitmap.createScaledBitmap(bitmap, reqWidth, reqHeight, false).compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        }catch (Exception e){}

        return file;
    }

    public static Bitmap decodeFileToBitmap(File file){
        Bitmap bitmap=null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        try{
            bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
        }catch (Exception e){}

        return  bitmap;
    }

}
