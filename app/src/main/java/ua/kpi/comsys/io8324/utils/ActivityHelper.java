package ua.kpi.comsys.io8324.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import ua.kpi.comsys.io8324.R;


public class ActivityHelper {
    public static final String TAG = "ActivityHelper";

    private ActivityHelper() {
    }

    public static InputStream getInputStreamFromFile(Context context, String fileName) throws IOException {
        return context.getAssets().open(fileName);
    }

    public static boolean isPortraitOrientation(Activity activity) {
        return activity.getResources()
                .getConfiguration()
                .orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    public static int getResourceByString(String resourceName) {
        try {
            Field field = R.id.class.getDeclaredField(resourceName);
            return field.getInt(field);
        } catch (NoSuchFieldException e) {
            Log.e(TAG, "error at "+e.getMessage());
            return -1;
        } catch (IllegalAccessException e) {
            Log.e(TAG, "error at "+e.getMessage());
            return -1;
        }
    }
}
