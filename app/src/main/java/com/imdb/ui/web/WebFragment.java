package com.imdb.ui.web;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.imdb.R;
import com.imdb.databinding.FragmentWebBinding;
import com.imdb.ui.base.BaseFragment;
import com.imdb.utility.AppConstants;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebFragment extends BaseFragment {


    private FragmentWebBinding fragmentWebBinding;
    private String mImageUrl;

    public WebFragment() {
        // Required empty public constructor
    }

    public static WebFragment newInstance(String imageUrl) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.POSTER_URL, imageUrl);
        WebFragment fragment = new WebFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fragmentWebBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_web, container, false);
        return fragmentWebBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().containsKey(AppConstants.POSTER_URL)) {
                mImageUrl = getArguments().getString(AppConstants.POSTER_URL);
            }
        }

        fragmentWebBinding.webviewPoster.setWebViewClient(new myWebViewClient());
        fragmentWebBinding.webviewPoster.getSettings().setJavaScriptEnabled(true);
        fragmentWebBinding.webviewPoster.getSettings().setLoadWithOverviewMode(true);
        fragmentWebBinding.webviewPoster.getSettings().setUseWideViewPort(true);
        fragmentWebBinding.webviewPoster.loadUrl(mImageUrl);

    }


    public class myWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            showProgressDialog();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            closeProgressDialog();
        }
    }


}
