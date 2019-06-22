package com.justincamp.demo.ui.searchScreen;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.justincamp.demo.R;
import com.justincamp.demo.ui.BaseFragment;
import com.justincamp.demo.ui.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFragment extends BaseFragment {

    @BindView(R.id.searchList)
    RecyclerView list;
    @BindView(R.id.message)
    TextView message;
    @BindView(R.id.artistSearch)
    EditText searchText;

    private SearchViewModel searchVM;
    private SearchListAdapter adapter;
    private Timer searchTimer;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        searchVM = ViewModelProviders.of(this).get(SearchViewModel.class);
        ((MainActivity)getActivity()).getActivityComponent().inject(searchVM);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle(R.string.artist_search);
        searchVM.onDelayedInit();
        super.connectBases(searchVM);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SearchListAdapter(item -> searchVM.artistChosen(item.id));
        list.setAdapter(adapter);
        updateListUI();
        searchVM.searchResults.observe(this, searchResultItems -> adapter.submitList(searchResultItems, this::updateListUI));
        searchVM.error.observe(this, error -> {
            if (getContext() == null) return;
            Toast.makeText(getContext(), getContext().getString(R.string.search_error, error.getLocalizedMessage()), Toast.LENGTH_SHORT).show();
        });
        searchVM.isLoading.observe(this, isLoading -> {
            if (isLoading) {
                showSpinner();
            } else {
                hideSpinner();
            }
        });
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                runTimer();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (searchVM.getShouldShowInitialWarning()) {
            new AlertDialog.Builder(getContext())
                    .setMessage(R.string.initial_warning)
                    .setPositiveButton(R.string.gotcha, (dialogInterface, i) -> {
                        searchVM.initalWarningShowed();
                    })
                    .create().show();
        }
    }

    private void updateListUI() {
        if (adapter.getItemCount() > 0) {
            list.setVisibility(View.VISIBLE);
            message.setVisibility(View.GONE);
        } else {
            list.setVisibility(View.GONE);
            message.setVisibility(View.VISIBLE);
            message.setText(searchVM.hasSearched ? R.string.no_matches : R.string.initial_search);
        }
    }

    private void runTimer() {
        if (searchTimer != null) {
            searchTimer.cancel();
        }
        searchTimer = new Timer();
        searchTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                searchTimer = null;
                if (searchText.getText().length() == 0 || getActivity() == null) return;
                getActivity().runOnUiThread(() -> searchVM.search(searchText.getText().toString()));
            }
        }, 500);
    }

}
