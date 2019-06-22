package com.justincamp.demo.injection;

import com.justincamp.demo.api.DiscogsAPI;
import com.justincamp.demo.ui.detailsScreen.DetailsViewModel;
import com.justincamp.demo.ui.searchScreen.SearchFragment;
import com.justincamp.demo.ui.searchScreen.SearchViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(DiscogsAPI obj);
    void inject(SearchFragment obj);
    void inject(SearchViewModel obj);
    void inject(DetailsViewModel obj);
}
