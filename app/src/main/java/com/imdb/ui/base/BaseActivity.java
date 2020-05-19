package com.imdb.ui.base;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.AnimRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.imdb.R;
import com.imdb.prefutil.PreferenceManager;

// Base activity class that will provide access of common method to its subclasses
public class BaseActivity extends AppCompatActivity {
    protected Activity mThis;
    protected String TAG;
    protected PreferenceManager mPrefMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mThis = this;
        TAG = getClass().getSimpleName();
        mPrefMgr = PreferenceManager.get();
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
        setFragment(fragment, R.id.container, isAddToBackStack, 0,
                0,
                0,
                0);
    }



    protected void setFragment(Fragment fragment, int containerId, boolean isAddToBackStack, @AnimRes int enter,
                               @AnimRes int exit, @AnimRes int popEnter, @AnimRes int popExit) {
        if (isFinishing()) return;
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
}


