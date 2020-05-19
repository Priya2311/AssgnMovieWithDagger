package com.imdb.ui.moviedetail;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.imdb.R;
import com.imdb.api.ApiConstant;
import com.imdb.databinding.FragmentMovieDetailBinding;
import com.imdb.ui.base.App;
import com.imdb.ui.base.BaseFragment;
import com.imdb.ui.home.HomeResponse;
import com.imdb.ui.web.MovieWebActivity;
import com.imdb.utility.AppConstants;
import com.imdb.vo.Resource;
import com.imdb.vo.Status;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 */

// @Jerry
// commented callMovieList() api as the movie listing api does not have movie id, it was record id. On record id, I am receiving different data.
// so I have passed the whole data from movie listing api. Initially I was opening it in webview but now showing a bunch of data from previous
// screen and showing the web view from this screen.


public class MovieDetailFragment extends BaseFragment {
    private FragmentMovieDetailBinding fragmentMovieBinding;
    private int mMovieId;
    private MovieDetailViewModel movieViewModel;
    private HomeResponse.ResultsBean mMovieData;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    public static MovieDetailFragment newInstance(int movieId, HomeResponse.ResultsBean movieData) {
        Bundle bundle = new Bundle();
        bundle.putInt(ApiConstant.MOVIE_ID, movieId);
        bundle.putSerializable(ApiConstant.MOVIE_DATA, (Serializable) movieData);
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMovieBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false);
        return fragmentMovieBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        movieViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
        if (getArguments() != null) {
            mMovieId = getArguments().getInt(ApiConstant.MOVIE_ID);
            mMovieData = (HomeResponse.ResultsBean) getArguments().getSerializable(ApiConstant.MOVIE_DATA);
        }

        setData(null);


        // callMovieList();
    }

    private void callMovieList() {
        if (!App.isConnected(fragmentMovieBinding.tvWatermark, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMovieList();
            }
        })) return;

        movieViewModel.setMovieDetailRequest(getRequest());
        fragmentMovieBinding.progress.setVisibility(View.VISIBLE);
        fragmentMovieBinding.progress.spin();
        movieViewModel.getMovieDetailResponse().observe(this, new Observer<Resource<MovieDetailResponse>>() {
            @Override
            public void onChanged(@Nullable final Resource<MovieDetailResponse> resp) {
                if (resp != null && resp.status != Status.LOADING) {
                    fragmentMovieBinding.progress.stopSpinning();
                    fragmentMovieBinding.progress.setVisibility(View.GONE);

                }
                if (resp != null && resp.data != null) {
                    if (resp != null && resp.status == Status.SUCCESS) {
                        if (resp.data != null) {
                            fragmentMovieBinding.tvWatermark.setVisibility(View.GONE);
                            setData(resp.data);
                        } else fragmentMovieBinding.tvWatermark.setVisibility(View.VISIBLE);
                    } else fragmentMovieBinding.tvWatermark.setVisibility(View.VISIBLE);

                }


            }
        });
    }

    private void setData(MovieDetailResponse resultdata) {
//        fragmentMovieBinding.tvMovieName.setText(resultdata.getTitle());
//        fragmentMovieBinding.tvReleaseDate.setText(resultdata.getRelease_date());
//        fragmentMovieBinding.tvVoteCount.setText(String.valueOf(resultdata.getVote_count()));
//        fragmentMovieBinding.tvOverview.setText(resultdata.getOverview());
//        Glide.with(getContext()).load(resultdata.getPoster_path()).into(fragmentMovieBinding.ivPoster);
        fragmentMovieBinding.tvMovieName.setText(mMovieData.getTitle());
        fragmentMovieBinding.tvReleaseDate.setText(mMovieData.getRelease_date());
        fragmentMovieBinding.tvVoteCount.setText(String.valueOf(mMovieData.getVote_count()));
        fragmentMovieBinding.tvOverview.setText(mMovieData.getOverview());
        fragmentMovieBinding.tvStatus.setText(getString(R.string.original_language) + " : " + mMovieData.getOriginal_language());
        fragmentMovieBinding.ivPoster.setOnClickListener(getMoviePosterClick(AppConstants.IMAGEURL + mMovieData.getPoster_path(),mMovieData.getTitle()));
        Glide.with(getContext())
                .load(AppConstants.IMAGEURL + mMovieData.getPoster_path())
                .into(fragmentMovieBinding.ivPoster);

    }

    private View.OnClickListener getMoviePosterClick(String imgUrl,String movieName) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MovieWebActivity.class);
                intent.putExtra(AppConstants.POSTER_URL, imgUrl);
                intent.putExtra(AppConstants.MOVIE_NAME, movieName);
                startActivity(intent);
            }
        };
    }

    private MovieDetailRequest getRequest() {
        MovieDetailRequest req = new MovieDetailRequest();
        req.setApi_key(AppConstants.APIKEY);
        req.setMovie_id(mMovieId);
        req.setLanguage(AppConstants.LANGUAGE);
        return req;
    }
}
