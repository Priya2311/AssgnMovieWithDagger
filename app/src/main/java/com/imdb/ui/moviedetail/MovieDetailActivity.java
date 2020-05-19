package com.imdb.ui.moviedetail;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.imdb.R;
import com.imdb.api.ApiConstant;
import com.imdb.databinding.ActivityMovieDetailBinding;
import com.imdb.ui.base.BaseActivity;
import com.imdb.ui.home.HomeResponse;

public class MovieDetailActivity extends BaseActivity {

    private ActivityMovieDetailBinding mMovieBinding;
    private int mMovieId;
    private HomeResponse.ResultsBean mMovieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        mMovieId = getIntent().getExtras().getInt(ApiConstant.MOVIE_ID);
        mMovieData = (HomeResponse.ResultsBean) getIntent().getSerializableExtra(ApiConstant.MOVIE_DATA);
        setSupportActionBar(mMovieBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mMovieBinding.toolbar.setTitle(mMovieData.getTitle());
        mMovieBinding.toolbar.setTitleTextColor(Color.WHITE);
        mMovieBinding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24px);
        mMovieBinding.toolbar.setNavigationOnClickListener(getNavigationClick());
        // at first passed the movie id (which is actually the record id) but now passing the movie data
        setFragment(MovieDetailFragment.newInstance(mMovieId,mMovieData), false);
    }

    private View.OnClickListener getNavigationClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
    }
}
