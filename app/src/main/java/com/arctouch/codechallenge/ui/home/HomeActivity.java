package com.arctouch.codechallenge.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.adapter.MovieAdapter;
import com.arctouch.codechallenge.model.Movie;
import com.arctouch.codechallenge.ui.movieDetails.MovieDetailActivity;
import com.arctouch.codechallenge.util.PaginationScrollListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements IHomeView {

    private IHomePresenter presenter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private MovieAdapter movieAdapter;
    private Toolbar toolbar;
    private static final Long page_start = 1L;
    public boolean isLoading = false;
    public boolean isLastPage = false;
    private Long currentPage = page_start;
    private int totalPages, totalResults;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        initWidget();
        setupRecycler();
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);
        presenter = new HomePresenterImpl(this);
        presenter.getGenres();
    }

    private void initWidget() {
        this.recyclerView = findViewById(R.id.recyclerView);
        this.progressBar = findViewById(R.id.progressBar);
        this.toolbar = findViewById(R.id.toolbar);
    }

    private void setupRecycler() {

        LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());

        recyclerView.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItens() {
                isLoading = true;
                currentPage++;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.getMovies(currentPage);
                        //isLoading = false;
                        if (currentPage == totalPages) {
                            isLastPage = true;
                        }
                    }
                }, 1000);

            }

            @Override
            public int getTotalPageCount() {
                return totalPages;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(movieAdapter != null) {
            outState.putParcelableArrayList("movies", new ArrayList<Movie>(movieAdapter.getMovies()));
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        movieAdapter = new MovieAdapter(savedInstanceState.getParcelableArrayList("movies"), onMovieClickListener());
        recyclerView.setAdapter(movieAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null) {
            if (movieAdapter == null) {
                presenter.getMovies(page_start);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

          MenuItem mSearch = menu.findItem(R.id.action_search);

          SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(mSearch);

          mSearchView.setQueryHint("Search");

          mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
              @Override
              public boolean onQueryTextSubmit(String query) {
                  return false;
              }

              @Override
              public boolean onQueryTextChange(String newText) {
                  if(TextUtils.isEmpty(newText)){
                      movieAdapter.setFilter("");
                  }else{
                      movieAdapter.setFilter(newText);
                  }
                  return true;
              }
          });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void setListMovies(List<Movie> movies) {
        if (isLoading) {
            movieAdapter.addMovies(movies);
            isLoading = false;
        } else {
            movieAdapter = new MovieAdapter(movies, onMovieClickListener());
            recyclerView.setAdapter(movieAdapter);

        }

        hideProgress();
    }

    @Override
    public void setConfigPagination(int page, int totalPages, int totalResults) {
        this.totalPages = totalPages;
        this.totalResults = totalResults;
        this.currentPage = Long.valueOf(page);

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    private MovieAdapter.OnMovieClickListener onMovieClickListener() {
        return new MovieAdapter.OnMovieClickListener() {
            @Override
            public void onClickMovie(View view, int position) {
                Movie movie = movieAdapter.getMovies().get(position);
                Intent intent = new Intent(getBaseContext(), MovieDetailActivity.class);
                intent.putExtra("movie", movie);
                startActivity(intent);
            }
        };
    }
}
