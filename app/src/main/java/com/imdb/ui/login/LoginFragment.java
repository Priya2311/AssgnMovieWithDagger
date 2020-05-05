package com.imdb.ui.login;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.imdb.R;
import com.imdb.databinding.FragmentLoginBinding;
import com.imdb.ui.base.BaseFragment;
import com.imdb.ui.home.HomeActivity;
import com.imdb.utility.ValidationHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment {

    FragmentLoginBinding fragmentLoginBinding;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        return fragmentLoginBinding.getRoot();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentLoginBinding.tvLogin.setOnClickListener(getLoginClick());
        fragmentLoginBinding.etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                // If user press done key
                if(i == EditorInfo.IME_ACTION_DONE){
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(fragmentLoginBinding.etPassword.getWindowToken(),0);
                    if (validateValues())
                        goToHome();
                    return true;
                }
                return false;
            }
        });
    }

    private View.OnClickListener getLoginClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateValues())
                    goToHome();
            }
        };
    }

    private void goToHome() {
        mPrefMgr.setIsLogIn(true);
        Intent intent = new Intent(getContext(), HomeActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    public boolean validateValues() {
        if (!ValidationHelper.isBlank(fragmentLoginBinding.etEmail, getString(R.string.please_email_address))) {
            if (ValidationHelper.isEmailValid(fragmentLoginBinding.etEmail, getString(R.string.please_email_vaild))) {
                if (!ValidationHelper.isBlank(fragmentLoginBinding.etPassword, getString(R.string.please_enter_password))) {
                    if (ValidationHelper.hasMinimumLength(fragmentLoginBinding.etPassword, 4, getString(R.string.enter_4_digit_password))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
