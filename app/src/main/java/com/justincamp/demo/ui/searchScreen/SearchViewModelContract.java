package com.justincamp.demo.ui.searchScreen;

import com.justincamp.demo.ui.BaseViewModelContract;

public interface SearchViewModelContract extends BaseViewModelContract {
    void search(String searchTerm);
    void loadMore();
    boolean getShouldShowInitialWarning();
    void initalWarningShowed();
    void artistChosen(int artistId);
}
