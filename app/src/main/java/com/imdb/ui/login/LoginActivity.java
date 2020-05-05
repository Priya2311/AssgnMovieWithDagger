package com.imdb.ui.login;

import android.graphics.Color;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import com.imdb.R;
import com.imdb.databinding.ActivityLoginBinding;
import com.imdb.ui.base.BaseActivity;

public class LoginActivity extends BaseActivity {
    ActivityLoginBinding mLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mLoginBinding.toolbar.setTitle(getString(R.string.login));
        mLoginBinding.toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mLoginBinding.toolbar);
        setFragment(LoginFragment.newInstance(), false);
    }
}
