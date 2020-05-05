package com.imdb.ui.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.AnimRes;
import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.imdb.R;
import com.imdb.prefutil.PreferenceManager;
import com.imdb.utility.DialogUtil;

public class BaseActivity extends AppCompatActivity {
    protected Activity mThis;
    protected String TAG;
    protected PreferenceManager mPrefMgr;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mThis = this;
        TAG = getClass().getSimpleName();
        mPrefMgr = PreferenceManager.get();
    }

    public boolean toggleEmptyView(boolean isEmptyVisible, @IdRes int toBeHide, @IdRes int emptyView) {
        if (isEmptyVisible) {
            hideView(findViewById(toBeHide));
            showView(findViewById(emptyView));
            findViewById(emptyView).setAnimation(AnimationUtils.makeInAnimation(this, true));

        } else {
            showView(findViewById(toBeHide));
            findViewById(emptyView).setAnimation(AnimationUtils.makeOutAnimation(this, true));
        }
        return isEmptyVisible;
    }

    public boolean isShowingProgressDialog() {
        return mProgressDialog != null && mProgressDialog.isShowing();
    }

    protected void hideProgressDialog() {
        //DialogUtil.hideProgressDialog(mProgressDialog);
        if (mProgressDialog != null && mProgressDialog.isShowing() && !isFinishing()) {
            mProgressDialog.dismiss();
        }
    }

    protected void showProgressDialog() {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = DialogUtil.showProgressDialog(this);
            }
            if (!mProgressDialog.isShowing() && !isFinishing() /*&& !isDestroyed()*/)
                mProgressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void hideView(View view) {
        if (view != null)
            view.setVisibility(View.GONE);
    }

    protected void hideView(int id) {
        if (id != 0) {
            View v = findViewById(id);
            if (v != null) v.setVisibility(View.GONE);
        }
    }

    protected void enableView(View view) {
        view.setEnabled(true);
    }

    protected void disableView(View view) {
        view.setEnabled(false);
    }

    protected void showView(View view) {
        if (view != null)
            view.setVisibility(View.VISIBLE);
    }

    protected void inivisibleView(View view) {
        if (view != null)
            view.setVisibility(View.INVISIBLE);
    }

    public String getTrimmedText(TextView textView) {
        return textView.getText().toString().trim();
    }

    public String getTextValue(TextView textView) {
        return textView.getText().toString();
    }


    public void popLastFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
    }

    /**
     * This method adds fragment to the container
     *
     * @param fragment
     * @param isAddToBackStack
     */
    protected void setFragment(Fragment fragment, boolean isAddToBackStack) {
       /* setFragment(fragment, R.id.container, isAddToBackStack, R.anim.slide_in_right,
                R.anim.slide_out_right,
                R.anim.slide_in_left,
                R.anim.slide_out_left);*/
        setFragment(fragment, R.id.container, isAddToBackStack, 0,
                0,
                0,
                0);
    }

    protected void setFragment(Fragment fragment, int container, boolean isAddToBackStack) {
        setFragment(fragment, container, isAddToBackStack, 0,
                0,
                0,
                0);
    }

    protected void hideFragment(Class<? extends Fragment> fragment, @AnimRes int enter,
                                @AnimRes int exit, @AnimRes int popEnter, @AnimRes int popExit) {
        Fragment frag = getSupportFragmentManager().findFragmentByTag(fragment.getSimpleName());
        if (frag != null)
            getSupportFragmentManager().beginTransaction().setCustomAnimations(enter,
                    exit,
                    popEnter,
                    popExit).hide(frag).commit();
    }

    protected void hideFragment(Class<? extends Fragment> fragment) {
        hideFragment(fragment, 0,
                0,
                0,
                0);
    }

    protected void showFragment(Class<? extends Fragment> fragment, @AnimRes int enter,
                                @AnimRes int exit, @AnimRes int popEnter, @AnimRes int popExit) {
        Fragment frag = getSupportFragmentManager().findFragmentByTag(fragment.getSimpleName());
        if (frag != null) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(enter,
                    exit, popEnter, popExit).addToBackStack(fragment.getSimpleName()).show(frag).commit();
        }
    }


    protected void showFragment(Class<? extends Fragment> fragment) {
        if (isFinishing() /*|| isDestroyed()*/) return;
        try {
            Fragment frag = getSupportFragmentManager().findFragmentByTag(fragment.getSimpleName());
            if (frag != null)
                getSupportFragmentManager().beginTransaction().setCustomAnimations(0,
                        0,
                        0,
                        0).show(frag).commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void setFragment(Fragment fragment, int containerId, boolean isAddToBackStack, @AnimRes int enter,
                               @AnimRes int exit, @AnimRes int popEnter, @AnimRes int popExit) {


        if (isFinishing() /*|| isDestroyed()*/) return;
        String fragmentName = fragment.getClass().getSimpleName();
        if (isAddToBackStack) {

            popLastFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(enter,
                            exit, popEnter, popExit)
                    .add(containerId, fragment, fragmentName)
                    .addToBackStack(fragmentName)
                    .commitAllowingStateLoss();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(enter,
                            exit, popEnter, popExit)
                    .add(containerId, fragment, fragmentName)
                    .commitAllowingStateLoss();
        }
    }


    public Fragment getFragmentByTag(Class<? extends Fragment> fragment) {
        return getSupportFragmentManager().findFragmentByTag(fragment.getSimpleName());
    }


    protected void pushFragment(int containerId, Fragment fragment) {
        if (mThis != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(containerId, fragment, fragment.getClass().getSimpleName()).commit();
        }
    }


}


