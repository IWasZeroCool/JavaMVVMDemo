package com.justincamp.demo.ui.searchScreen;

import androidx.lifecycle.MutableLiveData;

import com.justincamp.demo.api.DiscogsAPI;
import com.justincamp.demo.api.Response;
import com.justincamp.demo.models.SearchResultItem;
import com.justincamp.demo.models.SearchResults;
import com.justincamp.demo.ui.BaseViewModel;
import com.justincamp.demo.util.SharedPrefs;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SearchViewModel extends BaseViewModel implements SearchViewModelContract {

    @Inject
    DiscogsAPI discogsAPI;
    @Inject
    SharedPrefs sharedPrefs;

    private CompositeDisposable disposable = new CompositeDisposable();
    private int currentPage = 0;
    public MutableLiveData<ArrayList<SearchResultItem>> searchResults = new MutableLiveData<>(new ArrayList<>());
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    public MutableLiveData<Throwable> error = new MutableLiveData<>();
    private boolean showInitialWarning = false;
    boolean hasSearched = false;

    @Override
    protected void onCleared() {
        disposable.dispose();
    }

    @Override
    public void onDelayedInit() {
        if (initted) return;
        super.onDelayedInit();
        if (sharedPrefs.getShouldShowInitialMessage()) {
            showInitialWarning = true;
        }
    }

    @Override
    public void search(String searchTerm) {
        hasSearched = true;
        currentPage = 0;
        isLoading.setValue(true);
        disposable.add(discogsAPI.search(searchTerm, currentPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleSearch));
    }

    private void handleSearch(Response<SearchResults> resp) {
        isLoading.setValue(false);
        if (resp.isSuccessful) {
            searchResults.setValue(resp.response.results);
        } else {
            error.setValue(resp.error);
        }
    }

    @Override
    public void loadMore() {
        /*
        For the demo, search is limited to 10 results per search.
        This could be infinite scroll, with bottom-scroll-loading of new pages for more search
        results, backed by an lrucache or rooms/sqLite
         */
    }

    public boolean getShouldShowInitialWarning() {
        return showInitialWarning;
    }

    public void initalWarningShowed() {
        sharedPrefs.setShouldShowInitialMessage(false);
        showInitialWarning = false;
    }

    public void artistChosen(int artistId) {
        navigateTo(SearchFragmentDirections.actionHomeToDetails(artistId));
    }

}
