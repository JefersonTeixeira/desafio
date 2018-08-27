package com.arctouch.codechallenge.ui.home;

import com.arctouch.codechallenge.model.Movie;

import java.util.List;

public interface IHomeView {

    void setListMovies(List<Movie> movies);
    void setConfigPagination(int page, int totalPages, int totalResults);
    void showProgress();
    void hideProgress();
}
