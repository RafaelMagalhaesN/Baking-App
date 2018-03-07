package rmagalhaes.com.baking.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Rafael Magalhães on 28/02/18.
 */

public class LoaderInternalJSON {

    public static String load(Context context, String assetFileName) {
        String stringify = null;

        try {
            InputStream is = context.getAssets().open(assetFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            stringify = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return stringify;
    }
}
