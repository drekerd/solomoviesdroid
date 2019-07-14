package com.example.solomovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.solomovies.movie.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetMovieJsonData extends AsyncTask<String, Void, List<Movie>> implements GetBestMoviesByYear.OnDownloadComplete {

    private static final String TAG = "GetMovieJsonData";

    private List<Movie> mMoviesList = null;
    private String mBaseURL;
    private String mLanguage;
    private boolean mMatchAll;
    private boolean runningOnSameThread = false;

    private final OnDataAvailable mCallBack;

    interface OnDataAvailable {
        void onDataAvailable(List<Movie> data, DownloadStatus status);
    }

    public GetMovieJsonData(OnDataAvailable mCallBack, String mBaseURL) {
        Log.d(TAG, "GetMovieJsonData: Called");
        this.mMoviesList = mMoviesList;
        this.mBaseURL = mBaseURL;
        this.mCallBack = mCallBack;
    }

    void executreOnSameThread(String searchCriteria, String year){
        Log.d(TAG, "executreOnSameThread: starts");
        runningOnSameThread = true;
        String destinationURI = createURI(searchCriteria, year);
        Log.d(TAG, "executreOnSameThread: Destination URI " + destinationURI);

        GetBestMoviesByYear getMovies = new GetBestMoviesByYear(this);
        getMovies.execute(destinationURI);

        Log.d(TAG, "executreOnSameThread: ends");
    }

    @Override
    protected void onPreExecute() {
        Log.d(TAG, "onPreExecute: starts");

        if(mCallBack != null){
            mCallBack.onDataAvailable(mMoviesList, DownloadStatus.OK);
        }
    }

    @Override
    protected List<Movie> doInBackground(String... params) {
        Log.d(TAG, "doInBackground: starts");
        String destinationURI = createURI(params[0], "2019");
        GetBestMoviesByYear getBestMoviesByYear = new GetBestMoviesByYear(this);
        getBestMoviesByYear.runInSameThread(destinationURI);

        return null;
    }

    private String createURI(String searchCriteria, String year) {
        Log.d(TAG, "createURI: starts");

        return Uri.parse(mBaseURL).buildUpon()
                .appendQueryParameter(searchCriteria, year)
                .build().toString();
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete: starts. status " + status);

        if (status == DownloadStatus.OK) {
            mMoviesList = new ArrayList<>();

            try {
                JSONArray jsonArray = new JSONArray(data);
                Log.d(TAG, "onDownloadComplete: ENTERED ARRAY" +  jsonArray.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonMovie = jsonArray.getJSONObject(i);

                    mMoviesList
                            .add(new Movie(
                                    jsonMovie.getLong("id"),
                                    jsonMovie.getString("name"),
                                    jsonMovie.getString("description"),
                                    "none"));

                    Log.d(TAG, "onDownloadComplete: movie " + jsonMovie.toString());
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "onDownloadComplete: error parsing json data" + e.getMessage());
                status = DownloadStatus.FAILED_OR_EMPTY;
            }

        }

        if(runningOnSameThread && mCallBack != null){
            mCallBack.onDataAvailable(mMoviesList, status);
        }

        Log.d(TAG, "onDownloadComplete: ends");
    }
}
