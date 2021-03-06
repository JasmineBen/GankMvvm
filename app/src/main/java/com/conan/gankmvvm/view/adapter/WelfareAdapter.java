package com.conan.gankmvvm.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.conan.gankmvvm.R;
import com.conan.gankmvvm.databinding.ItemWelfareBinding;
import com.conan.gankmvvm.model.GankEntity;
import com.conan.gankmvvm.view.activities.BaseActivity;
import com.conan.gankmvvm.viewmodel.ViewModelFactory;
import com.conan.gankmvvm.viewmodel.WelfareViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Description：福利Adapter
 * Created by：JasmineBen
 * Time：2017/2017/11/7
 */

public class WelfareAdapter extends RecyclerView.Adapter<WelfareAdapter.BindingHolder> {

    private List<GankEntity> mWelfareData;
    private BaseActivity mActivity;

    @Inject
    public WelfareAdapter(BaseActivity activity) {
        this.mActivity = activity;
        mWelfareData = new ArrayList<>();
    }

    public void setData(List<GankEntity> data, boolean refresh) {
        Log.i("zpy","setData:"+refresh);
        int position = 0;
        if(refresh){
            mWelfareData.clear();
        }
        position = mWelfareData.size();
        mWelfareData.addAll(data);
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
        viewModel.setData(mWelfareData.get(position));
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
