package ua.kpi.comsys.io8324.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;

import java.io.IOException;
import java.io.InputStream;


public class ActivityHelper {

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
}
