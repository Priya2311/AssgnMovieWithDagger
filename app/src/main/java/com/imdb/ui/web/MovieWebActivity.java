package com.imdb.ui.web;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import com.imdb.R;
import com.imdb.databinding.ActivityMovieWebBinding;
import com.imdb.ui.base.BaseActivity;
import com.imdb.utility.AppConstants;

public class MovieWebActivity extends BaseActivity {
    private ActivityMovieWebBinding mWebBinding;
    private String mImageUrl,mMovieName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_web);
        if (getIntent().getExtras() != null) {
            mImageUrl = getIntent().getExtras().getString(AppConstants.POSTER_URL);
            mMovieName = getIntent().getExtras().getString(AppConstants.MOVIE_NAME);
        }
        mWebBinding.toolbar.setTitle(mMovieName);
        mWebBinding.toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar( mWebBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mWebBinding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24px);
        mWebBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setFragment(WebFragment.newInstance(mImageUrl), false);
    }
}
