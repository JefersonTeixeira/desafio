package com.arctouch.codechallenge.ui.home;

import com.arctouch.codechallenge.model.Movie;
import com.arctouch.codechallenge.model.UpcomingMoviesResponse;

import java.util.List;

public class HomePresenterImpl implements IHomePresenter, IHomeInteractor.OnHomeListener{

    private IHomeView mHomeView;
    private IHomeInteractor mHomeInteractor;
    private boolean isLoading;


    public HomePresenterImpl(IHomeView homeView){
        this.mHomeView = homeView;
        this.mHomeInteractor = new HomeInteractorImpl();
    }


    @Override
    public void getMovies(Long currentPage) {

        if(mHomeView != null){
            mHomeView.showProgress();
        }
        mHomeInteractor.getMovies(this, currentPage);
    }

    @Override
    public void getGenres() {
        mHomeView.showProgress();
        mHomeInteractor.getGenres();
    }

    @Override
    public void onSuccess(UpcomingMoviesResponse upcomingMovies) {
        if(mHomeView != null) {
            mHomeView.setListMovies(upcomingMovies.results);
            mHomeView.setConfigPagination(upcomingMovies.page, upcomingMovies.totalPages, upcomingMovies.results.size());
        }
    }
}
