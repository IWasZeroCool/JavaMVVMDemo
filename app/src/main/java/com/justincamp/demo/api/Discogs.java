package com.justincamp.demo.api;

import com.justincamp.demo.models.ArtistProfile;
import com.justincamp.demo.models.ArtistReleases;
import com.justincamp.demo.models.SearchResults;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Discogs {

    /**
     * Search the Discogs API
     * @param query     The search term to search for
     * @param type      The type of search to perform (one of "release", "master", "artist", "label")
     * @param perPage   How many results per page to return
     * @param page      Which page to return
     * @return          SearchResults object instance
     */
    @GET("/database/search")
    Single<SearchResults> search(@Query("q") String query,
                                 @Query("type") String type,
                                 @Query("per_page") Integer perPage,
                                 @Query("page") Integer page);

    /**
     * Fetch an artist's profile including bio & members
     * @param artistId  The Discogs artist ID
     * @return          ArtistProfile object instance
     */
    @GET("/artists/{artistId}")
    Single<ArtistProfile> artistProfile(@Path("artistId") int artistId);

    /**
     * Fetch a list of artist releases.
     * @param artistId  The Discogs artist ID
     * @param sortType  Sorting preference; one of ("year", "title", "format")
     * @param perPage   How many results per page to return
     * @param page      Which page to return
     * @return          ArtistReleases object instance
     */
    @GET("/artists/{artistId}/releases?sort=year")
    Single<ArtistReleases> artistReleases(@Path("artistId") int artistId,
                                          @Query("sort") String sortType,
                                          @Query("per_page") Integer perPage,
                                          @Query("page") Integer page);

}
