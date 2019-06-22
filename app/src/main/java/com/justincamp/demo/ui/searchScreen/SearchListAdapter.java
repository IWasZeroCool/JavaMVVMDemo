package com.justincamp.demo.ui.searchScreen;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.core.util.Consumer;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.justincamp.demo.BR;
import com.justincamp.demo.R;
import com.justincamp.demo.models.SearchResultItem;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchListAdapter extends ListAdapter<SearchResultItem, SearchListAdapter.ItemViewHolder> {

    private Consumer<SearchResultItem> clickHandler;

    public SearchListAdapter(Consumer<SearchResultItem> clickHandler) {
        super(new SearchResultItemDiff());
        this.clickHandler = clickHandler;
    }

    @NotNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.search_list_item, parent, false);
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

        void bind(SearchResultItem item) {
            binding.setVariable(BR.item, item);
            binding.executePendingBindings();
            binding.getRoot().setOnClickListener(view -> clickHandler.accept(item));
            image.setImageURI(item.thumbUrl);
        }

    }

    private static class SearchResultItemDiff extends DiffUtil.ItemCallback<SearchResultItem> {

        @Override
        public boolean areItemsTheSame(@NotNull SearchResultItem oldItem, @NotNull SearchResultItem newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NotNull SearchResultItem oldItem, @NotNull SearchResultItem newItem) {
            return oldItem.equals(newItem);
        }

    }

}
