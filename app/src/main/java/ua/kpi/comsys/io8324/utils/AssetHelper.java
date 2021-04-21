package ua.kpi.comsys.io8324.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class AssetHelper {

    private AssetHelper() {
    }

    public static InputStream getInputStreamFromFile(Context context, String fileName) throws IOException {
        return context.getAssets().open(fileName);
    }
}
