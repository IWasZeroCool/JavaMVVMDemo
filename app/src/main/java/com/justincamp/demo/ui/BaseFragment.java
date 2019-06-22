package com.justincamp.demo.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.justincamp.demo.R;

public class BaseFragment extends Fragment {

    protected AlertDialog spinnerDialog;

    protected void connectBases(BaseViewModel baseVM) {
        baseVM.navCommand.observe(this, navigationCommand -> {
            NavController controller = NavHostFragment.findNavController(BaseFragment.this);
            switch (navigationCommand.getType()) {
                case TO:
                    controller.navigate(navigationCommand.getDirections());
                    break;
                case BACK:
                    controller.popBackStack();
                    break;
                case BACK_TO:
                    controller.popBackStack(navigationCommand.getDestination(), false);
                    break;
                case TO_ROOT:
                    controller.popBackStack(R.id.search, false);
            }
        });
    }

    protected void showSpinner() {
        if (spinnerDialog == null && getContext() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setCancelable(false);
            builder.setView(R.layout.spinner_dialog);
            spinnerDialog = builder.create();
        }
        spinnerDialog.show();
    }

    protected void hideSpinner() {
        if (spinnerDialog == null) return;
        spinnerDialog.dismiss();
        spinnerDialog = null;
    }

}
