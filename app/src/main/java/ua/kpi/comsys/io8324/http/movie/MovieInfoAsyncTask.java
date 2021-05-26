package ua.kpi.comsys.io8324.http.movie;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ua.kpi.comsys.io8324.entity.movie.Movie;
import ua.kpi.comsys.io8324.entity.movie.MovieInfo;

public class MovieInfoAsyncTask extends AsyncTask<String, Void, InputStream> {
    private static HttpURLConnection urlConnection;

    @Override
    protected InputStream doInBackground(String... imdbIDs) {
        InputStream data = null;
        try {
            URL url = new URL(imdbIDs[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            data = urlConnection.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return data;
    }

    @Override
    protected void onPostExecute(InputStream inputStream) {
        super.onPostExecute(inputStream);
    }
}
