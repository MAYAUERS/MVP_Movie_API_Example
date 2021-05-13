package com.example.myapplicationapicallingexm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.myapplicationapicallingexm.contract.MovieListContract;
import com.example.myapplicationapicallingexm.model.Movie;
import com.example.myapplicationapicallingexm.presenter.MoviePresenter;
import com.example.myapplicationapicallingexm.view.MovieListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieListContract.View {

    private MoviePresenter moviePresenter;
    private RecyclerView rvMovieList;
    private List<Movie> movieList;
    private MovieListAdapter movieListAdapter;
    private ProgressBar pbLoading;
    private int pageNo = 1;

    private LinearLayoutManager layoutManager;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMovieList = findViewById(R.id.rvMovieList);
        pbLoading = findViewById(R.id.pbLoading);

        movieList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        rvMovieList.setLayoutManager(layoutManager);
        rvMovieList.setHasFixedSize(true);

        moviePresenter = new MoviePresenter(this);


        button =(Button)findViewById(R.id.btn1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //meera13may  call presenter
                moviePresenter.requestDataFromServer();
            }
        });

    }

    @Override
    public void showProgress() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbLoading.setVisibility(View.GONE);
    }

    //meera13may add data to adapter
    @Override
    public void setDataToRecyclerview(List<Movie> movieListArray) {

        movieList.addAll(movieListArray);
        movieListAdapter = new MovieListAdapter(movieList, MainActivity.this);
        rvMovieList.setAdapter(movieListAdapter);

    }

    @Override
    public void onResponseFailure(Throwable throwable) {

        Log.e("ERROR:", throwable.getMessage());
        Toast.makeText(MainActivity.this, "Error in getting data", Toast.LENGTH_LONG).show();

    }
}
