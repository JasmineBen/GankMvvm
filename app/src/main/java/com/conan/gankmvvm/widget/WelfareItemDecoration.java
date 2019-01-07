package com.conan.gankmvvm.widget;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Description：Decoration
 * Created by：JasmineBen
 * Time：2017/10/31
 */

public class WelfareItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int columns;

    public WelfareItemDecoration(int space, int columns) {
        this.space = space;
        this.columns = columns;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        outRect.left = 0;
        outRect.right = space;
        outRect.bottom = space;
        outRect.top = 0;
        if(isTopChild(position)){
            outRect.top = space;
        }
        if(isLeftChild(position)){
            outRect.left = space;
        }
    }

    public boolean isTopChild(int position){
        return position < columns;
    }

    public boolean isLeftChild(int position){
        return position % columns == 0;
    }

}
