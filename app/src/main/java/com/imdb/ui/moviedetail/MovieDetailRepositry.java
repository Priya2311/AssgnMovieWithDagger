package com.imdb.ui.moviedetail;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.imdb.AppExecutors;
import com.imdb.api.ApiResponse;
import com.imdb.api.AppRetrofit;
import com.imdb.repository.NetworkBoundWtDbRes;
import com.imdb.vo.Resource;

public class MovieDetailRepositry {

    private final AppExecutors appExecutors;

    MovieDetailRepositry() {
        this.appExecutors = new AppExecutors();
    }

    LiveData<Resource<MovieDetailResponse>> getMovies(final MovieDetailRequest req) {
        return new NetworkBoundWtDbRes<MovieDetailResponse, MovieDetailResponse>(appExecutors) {
            @NonNull
            @Override
            protected LiveData<ApiResponse<MovieDetailResponse>> createCall() {
                return AppRetrofit.getInstance().getAppService().getMovieDetail(req.getMovie_id(), req.getApi_key());
            }
        }.asLiveData();
    }
}
