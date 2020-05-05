package com.imdb.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.imdb.R;
import com.imdb.databinding.ActivityHomeBinding;
import com.imdb.logout.LogoutDialogFragment;
import com.imdb.ui.base.BaseActivity;
import com.imdb.ui.login.LoginActivity;


public class HomeActivity extends BaseActivity {


    private ActivityHomeBinding mHomeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        mHomeBinding.toolbar.setTitle("Top Movie List");
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
