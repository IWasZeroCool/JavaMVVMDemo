package com.justincamp.demo.ui.detailsScreen;

import androidx.lifecycle.MutableLiveData;

import com.justincamp.demo.api.DiscogsAPI;
import com.justincamp.demo.api.Response;
import com.justincamp.demo.models.ArtistProfile;
import com.justincamp.demo.models.ArtistReleases;
import com.justincamp.demo.models.Release;
import com.justincamp.demo.ui.BaseViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DetailsViewModel extends BaseViewModel implements DetailsViewModelContract {

    @Inject
    DiscogsAPI discogsAPI;

    private int artistId;
    private CompositeDisposable disposable = new CompositeDisposable();
    MutableLiveData<ArtistProfile> artistProfile = new MutableLiveData<>();
    MutableLiveData<ArrayList<Release>> releaseResults = new MutableLiveData<>(new ArrayList<>());
    MutableLiveData<Boolean> isLoadingArtists = new MutableLiveData<>(false);
    MutableLiveData<Boolean> isLoadingReleases = new MutableLiveData<>(false);
    MutableLiveData<Integer> currentReleasePage = new MutableLiveData<>(0);
    MutableLiveData<Integer> totalReleasePages = new MutableLiveData<>(0);
    MutableLiveData<Throwable> error = new MutableLiveData<>();

    @Override
    protected void onCleared() {
        disposable.dispose();
    }

    @Override
    public void onDelayedInit() {
        if (initted) return;
        super.onDelayedInit();
        currentReleasePage.setValue(1);
        getArtists();
        getReleasesPage();
    }

    @Override
    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    @Override
    public void releasesNextPage() {
        if (currentReleasePage.getValue() >= totalReleasePages.getValue() || isLoadingReleases.getValue()) return;
        currentReleasePage.setValue(currentReleasePage.getValue() + 1);
        getReleasesPage();
    }

    @Override
    public void releasesPrevPage() {
        if (currentReleasePage.getValue() <= 1 || isLoadingReleases.getValue()) return;
        currentReleasePage.setValue(currentReleasePage.getValue() - 1);
        getReleasesPage();
    }

    private void getArtists() {
        isLoadingArtists.setValue(true);
        disposable.add(discogsAPI.artistProfile(artistId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleArtists));
    }

    private void getReleasesPage() {
        isLoadingReleases.setValue(true);
        disposable.add(discogsAPI.artistReleases(artistId, currentReleasePage.getValue())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleReleasePage));
    }

    private void handleArtists(Response<ArtistProfile> resp) {
        isLoadingArtists.setValue(false);
        if (resp.isSuccessful) {
            artistProfile.setValue(resp.response);
        } else {
            error.setValue(resp.error);
        }
    }

    private void handleReleasePage(Response<ArtistReleases> resp) {
        isLoadingReleases.setValue(false);
        if (resp.isSuccessful) {
            releaseResults.setValue(resp.response.releases);
            totalReleasePages.setValue(resp.response.pagination.pages);
        } else {
            error.setValue(resp.error);
        }
    }

}
