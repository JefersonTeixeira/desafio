package com.arctouch.codechallenge.ui.home;

import com.arctouch.codechallenge.model.Movie;
import com.arctouch.codechallenge.model.UpcomingMoviesResponse;

import java.util.List;

public interface IHomeInteractor {

    interface OnHomeListener{
        void onSuccess(UpcomingMoviesResponse upcomingMovies);
    }

    void getMovies(OnHomeListener listener, Long page);
    void getGenres();
}
