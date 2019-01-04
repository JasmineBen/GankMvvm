package com.conan.gankmvvm.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.conan.gankmvvm.R;
import com.conan.gankmvvm.databinding.ItemWelfareBinding;
import com.conan.gankmvvm.model.GankList;
import com.conan.gankmvvm.view.activities.BaseActivity;
import com.conan.gankmvvm.viewmodel.ViewModelFactory;
import com.conan.gankmvvm.viewmodel.WelfareViewModel;

import javax.inject.Inject;

/**
 * Description：福利Adapter
 * Created by：JasmineBen
 * Time：2017/2017/11/7
 */

public class WelfareAdapter extends RecyclerView.Adapter<WelfareAdapter.BindingHolder> {

    private GankList mWelfareData;
    private BaseActivity mActivity;

    @Inject
    public WelfareAdapter(BaseActivity activity) {
        this.mActivity = activity;
    }

    public void setData(GankList data, boolean refresh) {
        Log.i("zpy","setData:"+refresh);
        int position = 0;
        if(mWelfareData == null) {
            this.mWelfareData = data;
        }else{
            if(refresh){
                mWelfareData.clear();
            }
            position = mWelfareData.size();
            mWelfareData.addItems(data.getGankDatas());
        }
        if(refresh){
            notifyDataSetChanged();
        }else {
            notifyItemInserted(position);
        }
    }

    @Override
    public int getItemCount() {
        return mWelfareData == null ? 0 : mWelfareData.size();
    }


    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemWelfareBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_welfare,
                parent, false);
        return new BindingHolder(binding);

    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        ItemWelfareBinding binding = holder.binding;
        WelfareViewModel viewModel = ViewModelFactory.getInstance(mActivity.getApplication()).create(WelfareViewModel.class);
        viewModel.setData(mWelfareData.getItem(position));
        binding.setViewmodel(viewModel);
    }




    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ItemWelfareBinding binding;

        public BindingHolder(ItemWelfareBinding binding) {
            super(binding.containerItem);
            this.binding = binding;
        }
    }
}
