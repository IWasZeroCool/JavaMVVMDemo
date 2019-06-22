package com.justincamp.demo.ui;

import androidx.lifecycle.ViewModel;
import androidx.navigation.NavDirections;

import com.justincamp.demo.util.NavigationCommand;
import com.justincamp.demo.util.SingleLiveEvent;

public class BaseViewModel extends ViewModel implements BaseViewModelContract {

    protected SingleLiveEvent<NavigationCommand> navCommand = new SingleLiveEvent<>();
    protected boolean initted = false;

    @Override
    public void onDelayedInit() {
        initted = true;
    }

    protected void navigateTo(NavDirections destination) {
        navCommand.postValue(NavigationCommand.To(destination));
    }

    protected void navigateBack() {
        navCommand.postValue(NavigationCommand.Back());
    }

    protected void navigateBackTo(int destination) {
        navCommand.postValue(NavigationCommand.BackTo(destination));
    }

    protected void navigateToRoot() {
        navCommand.postValue(NavigationCommand.toRoot());
    }

}
