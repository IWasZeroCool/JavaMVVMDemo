package com.justincamp.demo.ui.detailsScreen;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.justincamp.demo.BR;
import com.justincamp.demo.R;
import com.justincamp.demo.models.Artist;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MembersListAdapter extends ListAdapter<Artist, MembersListAdapter.ItemViewHolder> {

    public MembersListAdapter() {
        super(new SearchResultItemDiff());
    }

    @NotNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.members_list_item, parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NotNull ItemViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        SimpleDraweeView image;

        private ViewDataBinding binding;

        ItemViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            ButterKnife.bind(this, binding.getRoot());
        }

        void bind(Artist item) {
            binding.setVariable(BR.item, item);
            binding.executePendingBindings();
            image.setImageURI(item.thumbnail);
        }

    }

    private static class SearchResultItemDiff extends DiffUtil.ItemCallback<Artist> {

        @Override
        public boolean areItemsTheSame(@NotNull Artist oldItem, @NotNull Artist newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NotNull Artist oldItem, @NotNull Artist newItem) {
            return oldItem.equals(newItem);
        }

    }

}
