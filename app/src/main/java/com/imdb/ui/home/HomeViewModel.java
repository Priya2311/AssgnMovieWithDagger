package com.imdb.ui.home;

import android.os.Build;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.imdb.utility.AbsentLiveData;
import com.imdb.vo.Resource;

import java.util.Objects;

import javax.inject.Inject;

public class HomeViewModel extends ViewModel {

    final MutableLiveData<HomeRequest> movieReq = new MutableLiveData<>();
    private LiveData<Resource<HomeResponse>> movieResp;

    //private HomeRepositry homeRepositry;

    @Inject
    public HomeViewModel(HomeRepositry homeRepositry) {
       // homeRepositry = new HomeRepositry();
        movieResp = Transformations.switchMap(movieReq, new Function<HomeRequest, LiveData<Resource<HomeResponse>>>() {
            @Override
            public LiveData<Resource<HomeResponse>> apply(HomeRequest req) {
                if (req == null) {
                    return AbsentLiveData.create();
                } else
                    return homeRepositry.getMovies(req);
            }
        });


    }


    public void setMovieRequest(HomeRequest movieReq) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Objects.equals(this.movieReq.getValue(), movieReq)) {
                return;
            }
        }
        this.movieReq.setValue(movieReq);
    }

    public LiveData<Resource<HomeResponse>> getMovieeResponse() {
        return movieResp;
    }


}
