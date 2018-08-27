package com.arctouch.codechallenge.ui.movieDetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.model.Movie;
import com.arctouch.codechallenge.util.MovieImageUrlBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView backdroPath;
    private TextView overviewTextView;
    private TextView titleTextView;
    private TextView genresTextView;
    private TextView releaseDateTextView;
    private ImageView posterImageView;
    private Toolbar toolbar;
    private AppBarLayout mAppBarLayout;
    private final MovieImageUrlBuilder movieImageUrlBuilder = new MovieImageUrlBuilder();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moviedetail_activity);

        initWidget();

        Movie movie = (Movie) getIntent().getParcelableExtra("movie");
        setContentDetailsMovie(movie);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(movie.title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    private void initWidget() {
        this.backdroPath = findViewById(R.id.appBarBackdropPath);
        this.titleTextView = findViewById(R.id.titleTextView);
        this.genresTextView = findViewById(R.id.genresTextView);
        this.releaseDateTextView = findViewById(R.id.releaseDateTextView);
        this.posterImageView = findViewById(R.id.posterImageView);
        this.overviewTextView = findViewById(R.id.overviewTextView);
        this.toolbar =  findViewById(R.id.toolbar);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void setContentDetailsMovie(Movie movie){
        titleTextView.setText(movie.title);
        genresTextView.setText(TextUtils.join(", ", movie.genres));
        releaseDateTextView.setText(movie.releaseDate);
        overviewTextView.setText(movie.overview);

        String backPath = movie.backdropPath;
        if (TextUtils.isEmpty(backPath) == false) {
            Glide.with(this)
                    .load(movieImageUrlBuilder.buildPosterUrl(backPath))
                    .apply(new RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                    .into(backdroPath);
        }

        String posterPath = movie.posterPath;
        if (TextUtils.isEmpty(posterPath) == false) {
            Glide.with(this)
                    .load(movieImageUrlBuilder.buildPosterUrl(posterPath))
                    .apply(new RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                    .into(posterImageView);
        }

    }
}
