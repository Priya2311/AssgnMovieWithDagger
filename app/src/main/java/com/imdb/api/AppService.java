package com.imdb.api;

import androidx.lifecycle.LiveData;

import com.imdb.ui.home.HomeResponse;
import com.imdb.ui.moviedetail.MovieDetailResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * All API services, with their Url, Request type and Request method(eg. GET, POST)
 */
public interface AppService {


    //method to call the api to get movies data list
    @GET(ApiConstant.HOME)
    LiveData<ApiResponse<HomeResponse>> getHome(@Query(ApiConstant.SORT_BY) String sortedBy, @Query(ApiConstant.API_KEY) String apiKey);

    //method to call api to get movies detail
    @GET(ApiConstant.MOVIE_DETAIL)
    LiveData<ApiResponse<MovieDetailResponse>> getMovieDetail(@Query(ApiConstant.MOVIE_ID) int movieId, @Query(ApiConstant.API_KEY) String apiKey);


}

