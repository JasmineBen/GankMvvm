package com.conan.gankmvvm.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.conan.gankmvvm.R;
import com.conan.gankmvvm.databinding.GankItemLayoutBinding;
import com.conan.gankmvvm.model.GankEntity;
import com.conan.gankmvvm.view.activities.BaseActivity;
import com.conan.gankmvvm.view.listener.GankItemCallback;
import com.conan.gankmvvm.view.listener.OnItemClickListener;
import com.conan.gankmvvm.viewmodel.GankItemViewModel;
import com.conan.gankmvvm.viewmodel.ViewModelFactory;

import javax.inject.Inject;

import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class GankListAdapter extends PagedListAdapter<GankEntity,GankListAdapter.BindingHolder> {

    private BaseActivity mActivity;
    private OnItemClickListener mItemClickListener;

    @Inject
    public GankListAdapter(BaseActivity activity) {
        super(new GankItemCallback());
        mActivity = activity;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.mItemClickListener = itemClickListener;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GankItemLayoutBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.gank_item_layout,
                parent, false);
        return new BindingHolder(binding);

    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        GankItemLayoutBinding binding = holder.binding;
        GankItemViewModel viewModel = ViewModelFactory.getInstance(mActivity.getApplication()).create(GankItemViewModel.class);
        viewModel.setData(getItem(position));
        binding.setViewmodel(viewModel);
        if(mItemClickListener != null){
            holder.itemView.setOnClickListener(view ->  mItemClickListener.onItemClick(getItem(position)));
        }
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private GankItemLayoutBinding binding;

        public BindingHolder(GankItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
