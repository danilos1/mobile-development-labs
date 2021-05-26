package ua.kpi.comsys.io8324.http.image;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ua.kpi.comsys.io8324.entity.image.JSONImage;

public class ImageHttpRepository {
    public static final String TAG = "ImageHttpRepository";
    public static int COUNT = 18;
    public static String REQUEST = "small+animals";

    @SuppressLint("DefaultLocale")
    private static String imageApiUrl = String.format(
        "https://pixabay.com/api/?key=19193969-87191e5db266905fe8936d565&q=%s&image_type=photo&per_page=%d",
            REQUEST,
            COUNT
    );


    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<JSONImage> fetchImages() {
        Log.i(TAG, String.format("Sending GET request to %s server", imageApiUrl));

        ImageAsyncTask imageAsyncTask = new ImageAsyncTask();
        imageAsyncTask.execute(imageApiUrl);

        List<JSONImage> fetchedImages = new ArrayList<>();
        try {
            fetchedImages = imageAsyncTask.get().getImages();
            return fetchedImages;
        } catch (ExecutionException | InterruptedException e) {
            Log.e(TAG, "Getting an error during sending GET request: "+e.getMessage());
        }

        return fetchedImages;
    }
}
