package com.imdb.ui.home;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.imdb.AppExecutors;
import com.imdb.api.ApiResponse;
import com.imdb.api.AppRetrofit;
import com.imdb.repository.NetworkBoundWtDbRes;
import com.imdb.vo.Resource;

public class HomeRepositry {

    private final AppExecutors appExecutors;

    HomeRepositry() {
        this.appExecutors = new AppExecutors();
    }

    LiveData<Resource<HomeResponse>> getMovies(final HomeRequest req) {
        return new NetworkBoundWtDbRes<HomeResponse, HomeResponse>(appExecutors) {
            @NonNull
            @Override
            protected LiveData<ApiResponse<HomeResponse>> createCall() {
                return AppRetrofit.getInstance().getAppService().getHome(req.getSort_by(), req.getApi_key());
            }
        }.asLiveData();
    }
}
