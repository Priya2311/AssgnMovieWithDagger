package com.imdb.di;

import com.imdb.api.AppRetrofit;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={AppModule.class, AppRetrofit.class, AppExecutors.class})
public interface AppComponent {
    HomeComponent.Factory homeComponent();
}
