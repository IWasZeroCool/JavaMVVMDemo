package com.justincamp.demo.ui.detailsScreen;

public interface DetailsViewModelContract {
    void setArtistId(int artistId);
    void releasesNextPage();
    void releasesPrevPage();
}
