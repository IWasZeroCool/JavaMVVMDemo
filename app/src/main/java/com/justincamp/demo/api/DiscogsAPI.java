package com.justincamp.demo.api;

import com.justincamp.demo.models.ArtistProfile;
import com.justincamp.demo.models.ArtistReleases;
import com.justincamp.demo.models.SearchResults;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class DiscogsAPI {

    @Inject
    Discogs discogs;

    @Inject
    public DiscogsAPI() {}

    /**
     * Fetch a Single<Response<>> wrapped search result
     * @param searchTerm    Term to search for
     * @param page          Current page of search results
     * @return              Single<Response<SearchResults>>
     */
    public Single<Response<SearchResults>> search(String searchTerm, int page) {
        return discogs.search(searchTerm, "artist", 10, page)
                .map(Response::new)
                .onErrorReturn(Response::new);
    }

    /**
     * Fetch a Single<Response<>> wrapped artist profile
     * @param artistId      Discogs artist ID
     * @return              Single<Response<ArtistProfile>>
     */
    public Single<Response<ArtistProfile>> artistProfile(int artistId) {
        return discogs.artistProfile(artistId)
                .map(Response::new)
                .onErrorReturn(Response::new);
    }

    /**
     * Fetch a Single<Response<>> wrapped list of artist releases
     * @param artistId      Discogs artist ID
     * @param page          Current page of releases
     * @return              Single<Response<ArtistReleases>>
     */
    public Single<Response<ArtistReleases>> artistReleases(int artistId, int page) {
        return discogs.artistReleases(artistId, "year", 10, page)
                .map(Response::new)
                .onErrorReturn(Response::new);
    }
}
