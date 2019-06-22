package com.justincamp.demo.ui.detailsScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.justincamp.demo.R;
import com.justincamp.demo.databinding.DetailsFragmentBinding;
import com.justincamp.demo.ui.BaseFragment;
import com.justincamp.demo.ui.MainActivity;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsFragment extends BaseFragment {

    @BindView(R.id.mainImage)
    SimpleDraweeView mainImage;
//    @BindView(R.id.title)
//    TextView title;
//    @BindView(R.id.bio)
//    TextView bio;
    @BindView(R.id.membersList)
    RecyclerView membersList;
    @BindView(R.id.releasesProgress)
    ProgressBar releasesProgress;
//    @BindView(R.id.page)
//    TextView page;
    @BindView(R.id.releasesList)
    RecyclerView releasesList;

    private DetailsFragmentBinding binding;
    private DetailsViewModel detailsVM;
    private MembersListAdapter membersListAdapter = new MembersListAdapter();
    private ReleasesListAdapter releasesListAdapter = new ReleasesListAdapter();

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DetailsFragmentBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        detailsVM = ViewModelProviders.of(this).get(DetailsViewModel.class);
        ((MainActivity)getActivity()).getActivityComponent().inject(detailsVM);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle(R.string.artist_details);
        detailsVM.setArtistId(DetailsFragmentArgs.fromBundle(getArguments()).getArtistId());
        super.connectBases(detailsVM);
        detailsVM.onDelayedInit();
        binding.setItem(detailsVM.artistProfile);
        binding.setCurrentPage(detailsVM.currentReleasePage);
        binding.setTotalPages(detailsVM.totalReleasePages);
        membersList.setLayoutManager(new LinearLayoutManager(getContext()));
        releasesList.setLayoutManager(new LinearLayoutManager(getContext()));
        membersList.setAdapter(membersListAdapter);
        releasesList.setAdapter(releasesListAdapter);
        detailsVM.artistProfile.observe(this, artistProfile -> {
            membersListAdapter.submitList(artistProfile.members);
            mainImage.setImageURI(artistProfile.getMainImageUri());
            binding.executePendingBindings();
        });
        detailsVM.releaseResults.observe(this, releases -> releasesListAdapter.submitList(releases));
        detailsVM.error.observe(this, error -> {
            if (getContext() == null) return;
            Toast.makeText(getContext(), getContext().getString(R.string.search_error, error.getLocalizedMessage()), Toast.LENGTH_SHORT).show();
        });
        detailsVM.isLoadingArtists.observe(this, isLoading -> {
            if (isLoading) {
                showSpinner();
            } else {
                hideSpinner();
            }
        });
        detailsVM.isLoadingReleases.observe(this, isLoading -> releasesProgress.setVisibility(isLoading ? View.VISIBLE : View.GONE));
    }

    @OnClick(R.id.pageLeft)
    void onPageLeftClicked() {
        detailsVM.releasesPrevPage();
    }

    @OnClick(R.id.pageRight)
    void onPageRightClicked() {
        detailsVM.releasesNextPage();
    }
}
