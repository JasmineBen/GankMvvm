package com.conan.gankmvvm.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.conan.gankmvvm.R;
import com.conan.gankmvvm.model.GankList;
import com.conan.gankmvvm.databinding.GankItemLayoutBinding;
import com.conan.gankmvvm.view.activities.BaseActivity;
import com.conan.gankmvvm.view.listener.OnItemClickListener;
import com.conan.gankmvvm.viewmodel.GankItemViewModel;
import com.conan.gankmvvm.viewmodel.ViewModelFactory;
import com.conan.gankmvvm.model.GankList;
import com.conan.gankmvvm.view.activities.BaseActivity;
import com.conan.gankmvvm.viewmodel.ViewModelFactory;

import javax.inject.Inject;

public class GankListAdapter extends RecyclerView.Adapter<GankListAdapter.BindingHolder>{

    private BaseActivity mActivity;
    private GankList mData;
    private OnItemClickListener mItemClickListener;

    @Inject
    public GankListAdapter(BaseActivity activity) {
        mActivity = activity;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.mItemClickListener = itemClickListener;
    }

    public void setData(GankList data,boolean refresh){
        int position = 0;
        if(mData == null) {
            this.mData = data;
        }else{
            if(refresh){
                mData.clear();
            }
            position = mData.size();
            mData.addItems(data.getGankDatas());
        }
        if(refresh){
            notifyDataSetChanged();
        }else {
            notifyItemInserted(position);
        }
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
        viewModel.setData(mData.getItem(position));
        binding.setViewmodel(viewModel);
        if(mItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(mData.getItem(position));
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }



    public static class BindingHolder extends RecyclerView.ViewHolder {
        private GankItemLayoutBinding binding;

        public BindingHolder(GankItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
