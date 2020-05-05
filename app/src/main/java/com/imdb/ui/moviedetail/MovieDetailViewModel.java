package com.imdb.ui.moviedetail;

import android.os.Build;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.imdb.utility.AbsentLiveData;
import com.imdb.vo.Resource;

import java.util.Objects;

public class MovieDetailViewModel extends ViewModel {

    final MutableLiveData<MovieDetailRequest> movieReq = new MutableLiveData<>();
    private LiveData<Resource<MovieDetailResponse>> movieResp;

    private MovieDetailRepositry movieDetailRepositry;

    public MovieDetailViewModel() {
        movieDetailRepositry = new MovieDetailRepositry();
        movieResp = Transformations.switchMap(movieReq, new Function<MovieDetailRequest, LiveData<Resource<MovieDetailResponse>>>() {
            @Override
            public LiveData<Resource<MovieDetailResponse>> apply(MovieDetailRequest req) {
                if (req == null) {
                    return AbsentLiveData.create();
                } else
                    return movieDetailRepositry.getMovies(req);
            }
        });


    }


    public void setMovieDetailRequest(MovieDetailRequest movieReq) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Objects.equals(this.movieReq.getValue(), movieReq)) {
                return;
            }
        }
        this.movieReq.setValue(movieReq);
    }

    public LiveData<Resource<MovieDetailResponse>> getMovieDetailResponse() {
        return movieResp;
    }


}
