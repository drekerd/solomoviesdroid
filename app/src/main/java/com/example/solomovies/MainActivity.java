package com.example.solomovies;

import android.os.Bundle;

import com.example.solomovies.movie.Movie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements GetMovieJsonData.OnDataAvailable {
    private final String TAG = this.getClass().getSimpleName();
    private TextView bestMovies;
    private final String PROTOCOL = "http://";
    private final String IP = "192.168.1.75";
    private final String PORT = ":8080";
    private final String RESOURCE = "/best/year";
    private final String PARAMS = "?year=2010";
    private final String URI = PROTOCOL + IP + PORT + RESOURCE + PARAMS;
    private final String BASE_URI = PROTOCOL + IP + PORT + RESOURCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bestMovies = findViewById(R.id.bestMovies);
        //GetBestMoviesByYear getBestMoviesByYear = new GetBestMoviesByYear(this);

        //Log.d(TAG, "onCreate: URI=" + URI);

        //Log.d(TAG, "onCreate: ended");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: starts");
        GetMovieJsonData getMovieJsonData = new GetMovieJsonData(this, BASE_URI);
        getMovieJsonData.execute("year" + "2019");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.d(TAG, "onCreateOptionsMenu() returned: " + true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        Log.d(TAG, "onOptionsItemSelected() returned: returned");
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataAvailable(List<Movie> data, DownloadStatus status) {
        if (status == DownloadStatus.OK) {
            Log.d(TAG, "onDataAvailable: data is " + data);
        } else {
            Log.e(TAG, "onDataAvailable: failed with status" + status);
        }
    }

}
