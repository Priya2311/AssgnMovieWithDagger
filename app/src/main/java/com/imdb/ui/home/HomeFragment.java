package com.imdb.ui.home;


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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.imdb.R;
import com.imdb.api.ApiConstant;
import com.imdb.databinding.FragmentHomeBinding;
import com.imdb.ui.App;
import com.imdb.ui.OnPosterClick;
import com.imdb.ui.base.BaseFragment;
import com.imdb.ui.moviedetail.MovieDetailActivity;
import com.imdb.ui.web.MovieWebActivity;
import com.imdb.utility.AppConstants;
import com.imdb.utility.Lg;
import com.imdb.vo.Resource;
import com.imdb.vo.Status;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements OnPosterClick {

    private FragmentHomeBinding fragmentHomeBinding;
    private HomeViewModel homeViewModel;
    HomeAdapter mAdapter;
    private List<HomeResponse.ResultsBean> mMovieData = new ArrayList<HomeResponse.ResultsBean>();


    public HomeFragment() {
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return fragmentHomeBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        mAdapter = new HomeAdapter(getContext(), mMovieData);
        fragmentHomeBinding.movieRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentHomeBinding.movieRecycler.setAdapter(mAdapter);

        callMovieList();
    }

    private void callMovieList() {
        if (!App.isConnected(fragmentHomeBinding.tvWatermark, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMovieList();
            }
        })) return;

        homeViewModel.setMovieRequest(getRequest());
        fragmentHomeBinding.progress.setVisibility(View.VISIBLE);
        fragmentHomeBinding.progress.spin();
        homeViewModel.getMovieeResponse().observe(this, new Observer<Resource<HomeResponse>>() {
            @Override
            public void onChanged(@Nullable final Resource<HomeResponse> resp) {
                if (resp != null && resp.status != Status.LOADING) {
                    fragmentHomeBinding.progress.stopSpinning();
                    fragmentHomeBinding.progress.setVisibility(View.GONE);

                }
                if (resp != null && resp.data != null) {
                    if (resp != null && resp.status == Status.SUCCESS  ) {
                        if(resp.data.getResults() != null)
                        {
                            fragmentHomeBinding.tvWatermark.setVisibility(View.GONE);
                            Lg.e("resultdata",resp.data.getResults().toString());
                            setData(resp.data.getResults());
                        }
                        else fragmentHomeBinding.tvWatermark.setVisibility(View.VISIBLE);
                    }

                }


            }
        });
    }

    private void setData(List<HomeResponse.ResultsBean> results) {

        mAdapter.clear();
        mAdapter.addAll(results);
        mAdapter.setListener(this);
        mAdapter.notifyDataSetChanged();
    }

    private HomeRequest getRequest() {
        HomeRequest req = new HomeRequest();
        req.setApi_key(AppConstants.APIKEY);
        req.setSort_by(AppConstants.SORT_BY);
        return req;
    }

    @Override
    public void onPosterItemClick(int position) {
//        Intent intent = new Intent(getContext(), MovieWebActivity.class);
//        intent.putExtra(AppConstants.POSTER_URL, AppConstants.IMAGEURL + mMovieData.get(position).getPoster_path());
//        startActivity(intent);
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra(ApiConstant.MOVIE_ID, mMovieData.get(position).getId());
        intent.putExtra(ApiConstant.MOVIE_DATA, (Serializable) mMovieData.get(position));
        startActivity(intent);
    }
}
