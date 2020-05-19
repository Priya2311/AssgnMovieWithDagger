package com.imdb.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.imdb.R;
import com.imdb.databinding.ActivityHomeBinding;
import com.imdb.di.HomeComponent;
import com.imdb.ui.base.App;
import com.imdb.ui.base.BaseActivity;
import com.imdb.ui.login.LoginActivity;
import com.imdb.ui.logout.LogoutDialogFragment;

import javax.inject.Inject;


public class HomeActivity extends BaseActivity {

    @Inject
    HomeViewModel homeViewModel;
    private ActivityHomeBinding mHomeBinding;
    HomeComponent homeComponent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Creation of the home graph using the application graph
        homeComponent = ((App) getApplicationContext())
                .mAppComponent.homeComponent().create();

        // Make Dagger instantiate @Inject fields in HomeActivity
        homeComponent.inject(this);
        super.onCreate(savedInstanceState);
        mHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        mHomeBinding.toolbar.setTitle(getString(R.string.top_movie_list));
        mHomeBinding.toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mHomeBinding.toolbar);
        setFragment(HomeFragment.newInstance(), false);
        mHomeBinding.tvLogout.setOnClickListener(getLogoutClick());
    }

    private View.OnClickListener getLogoutClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LogoutDialogFragment dialogFragment = new LogoutDialogFragment();
                dialogFragment.setOkClick(new LogoutDialogFragment.DialogCallBack() {
                    @Override
                    public void okPressed() {
                        dialogFragment.dismiss();
                        mPrefMgr.setIsLogIn(false);
                        mPrefMgr.clear();
                        Intent intent=new Intent(HomeActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                dialogFragment.show(getSupportFragmentManager(), getResources().getString(R.string.logout));
            }
        };
    }
}
