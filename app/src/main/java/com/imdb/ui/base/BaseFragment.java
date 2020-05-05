package com.imdb.ui.base;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.imdb.prefutil.PreferenceManager;
import com.imdb.utility.AppUtils;
import com.imdb.utility.DialogUtil;

import java.lang.reflect.Field;

/**
 * A simple {@link androidx.fragment.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link BaseFragment newInstance} factory method to
 * create an instance of this fragment.
 */
public class BaseFragment extends DialogFragment {
    protected BaseActivity mBaseActivity;
    protected String TAG = "";
    protected Resources mRes;
    private ProgressDialog mProgressDialog;
    protected PreferenceManager mPrefMgr;
    protected View mView;


    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
        mBaseActivity = (BaseActivity) getActivity();
        mPrefMgr = PreferenceManager.get();
        mRes = getResources();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    @Override
    public void onDestroyView() {
        AppUtils.hideKeyboard(getContext());
        super.onDestroyView();
    }

    protected void closeProgressDialog() {

        DialogUtil.hideProgressDialog(mProgressDialog);
    }

    protected void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = DialogUtil.showProgressDialog(getContext());
        }
        mProgressDialog.show();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
