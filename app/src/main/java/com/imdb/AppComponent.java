package com.imdb;

import com.imdb.api.AppRetrofit;
import com.imdb.ui.home.HomeActivity;
import com.imdb.ui.home.HomeFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={AppModule.class, AppRetrofit.class,AppExecutors.class})
public interface AppComponent {
    HomeComponent.Factory homeComponent();
}
