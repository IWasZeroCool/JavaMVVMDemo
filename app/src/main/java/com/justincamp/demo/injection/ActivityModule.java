package com.justincamp.demo.injection;

import android.content.Context;

import com.justincamp.demo.api.Discogs;
import com.justincamp.demo.api.DiscogsAPI;
import com.justincamp.demo.api.RestClient;
import com.justincamp.demo.util.SharedPrefs;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    protected ActivityComponent activityComponent;
    protected Context context;

    public ActivityModule(Context context) {
        this.context = context;
    }

    public void setActivityComponent(ActivityComponent component) {
        activityComponent = component;
    }

    @Provides
    @Singleton
    public SharedPrefs providesSharedPrefs() {
        return new SharedPrefs(context);
    }

    @Provides
    @Singleton
    public Discogs providesDiscogs() {
        return new RestClient().getDiscogsService();
    }

    @Provides
    @Singleton
    public DiscogsAPI providesDiscogsAPI() {
        DiscogsAPI api = new DiscogsAPI();
        activityComponent.inject(api);
        return api;
    }

}