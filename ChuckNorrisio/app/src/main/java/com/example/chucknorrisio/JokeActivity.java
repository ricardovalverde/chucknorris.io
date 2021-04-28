package com.example.chucknorrisio;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chucknorrisio.datasource.JokeRemoteDataSource;
import com.example.chucknorrisio.model.Joke;
import com.example.chucknorrisio.presentation.JokePresenter;

public class JokeActivity extends AppCompatActivity {
    static final String CATEGORY_KEY = "category_key";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().getExtras() != null) {
            String category = getIntent().getExtras().getString(CATEGORY_KEY);
            Log.i("teste", category);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(category);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                JokeRemoteDataSource jokeRemoteDataSource = new JokeRemoteDataSource();
                JokePresenter jokePresenter = new JokePresenter(this, jokeRemoteDataSource);
                jokePresenter.findJokeBy(category);

                FloatingActionButton fab = findViewById(R.id.fab);
                fab.setOnClickListener(view -> {
                    jokePresenter.findJokeBy(category);


                });
            }

        }
    }

    public void showProgressBar() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }
        progressDialog.show();

    }

    public void hideProgressBar() {
        if (progressDialog != null) {
            progressDialog.hide();
        }
    }

    public void showJoke(Joke joke) {
        TextView txtJoke = findViewById(R.id.txt_joke);
        txtJoke.setText(joke.getJoke());

        ImageView imageView = findViewById(R.id.img_jokeLayout);
    }

    public void showFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}