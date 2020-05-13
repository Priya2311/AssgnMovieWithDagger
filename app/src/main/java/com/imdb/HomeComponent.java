package com.imdb;

import com.imdb.ui.home.HomeActivity;
import com.imdb.ui.home.HomeFragment;

import dagger.Subcomponent;

@Subcomponent
public interface HomeComponent {
    @Subcomponent.Factory
    interface Factory {
        HomeComponent create();
    }

    void inject(HomeActivity homeActivity);
    void inject(HomeFragment homeFragment);
}
