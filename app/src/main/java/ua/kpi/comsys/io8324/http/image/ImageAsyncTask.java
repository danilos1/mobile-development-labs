package ua.kpi.comsys.io8324.http.image;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import ua.kpi.comsys.io8324.entity.image.ImageResults;
import ua.kpi.comsys.io8324.entity.image.JSONImage;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ImageAsyncTask extends AsyncTask<String, Void, ImageResults> {
    private static HttpURLConnection urlConnection;

    @Override
    protected ImageResults doInBackground(String... urls) {
        ImageResults imgs = null;

        try {
            URL url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream data = urlConnection.getInputStream();

            ObjectMapper mapper = new ObjectMapper();
            try {
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                imgs = mapper.readValue(data, ImageResults.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return imgs;
    }

}
