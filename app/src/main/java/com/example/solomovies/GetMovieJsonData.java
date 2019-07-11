package com.example.solomovies;

import android.net.Uri;
import android.util.Log;

import com.example.solomovies.movie.Movie;

import java.util.List;

public class GetMovieJsonData implements GetBestMoviesByYear.OnDownloadComplete{

    private static final String TAG = "GetMovieJsonData";

    private List<Movie> mMoviesList = null;
    private String mBaseURL;
    private String mLanguage;
    private boolean mMatchAll;

    private final OnDataAvailable mCallBack;

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {

    }

    interface OnDataAvailable{
        void OnDataAvailable(List<Movie> data, DownloadStatus status);
    }

    public GetMovieJsonData(List<Movie> mMoviesList, String mBaseURL, String mLanguage, boolean mMatchAll, OnDataAvailable mCallBack) {
        Log.d(TAG, "GetMovieJsonData: Called");
        this.mMoviesList = mMoviesList;
        this.mBaseURL = mBaseURL;
        this.mLanguage = mLanguage;
        this.mMatchAll = mMatchAll;
        this.mCallBack = mCallBack;
    }

    //@TODO for searc individual movies
    /*void executreOnSameThread(String searchCriteria){
        Log.d(TAG, "executreOnSameThread: starts");
        String destinationURI = createURI(searchCriteria);

        GetBestMoviesByYear getMovies = new GetBestMoviesByYear(this);
        getMovies.execute(destinationURI);
        Log.d(TAG, "executreOnSameThread: ends");
    }*/

    private String createURI(String searchCriteria, String name){
        Log.d(TAG, "createURI: starts");

        return Uri.parse(mBaseURL).buildUpon()
                .appendQueryParameter("tags",searchCriteria)
                .appendQueryParameter("tagmode",mMatchAll ? "ALL" : "ANY")
                .appendQueryParameter("lang", name)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback","1")
                .build().toString();
    }
}
