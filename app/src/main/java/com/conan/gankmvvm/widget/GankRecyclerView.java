package com.conan.gankmvvm.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * Description：自定义RecyclerView
 * Created by：JasmineBen
 * Time：2017/10/31
 */
public class GankRecyclerView extends RecyclerView {

    private static final String TAG = GankRecyclerView.class.getSimpleName();

    private boolean bLoadingMore;

    private OnLoadMoreListener moreListener;

    public GankRecyclerView(Context context) {
        super(context);
        init();
    }

    public GankRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GankRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = getLayoutManager();
                    if (layoutManager instanceof GridLayoutManager) {
                        GridLayoutManager glm = (GridLayoutManager) layoutManager;
                        int itemCount = glm.getItemCount();
                        int firstVisibleItemPosition = glm
                                .findFirstVisibleItemPosition();
                        if (firstVisibleItemPosition + getChildCount() >= itemCount
                                && firstVisibleItemPosition > 0) {
                            loadMore();
                        }

                    } else if (layoutManager instanceof LinearLayoutManager) {
                        LinearLayoutManager llm = (LinearLayoutManager) layoutManager;
                        int itemCount = llm.getItemCount();
                        int firstVisibleItemPosition = llm
                                .findFirstVisibleItemPosition();
                        if (firstVisibleItemPosition + getChildCount() >= itemCount
                                && firstVisibleItemPosition > 0) {
                            loadMore();
                        }
                    }else if(layoutManager instanceof StaggeredGridLayoutManager){
                        StaggeredGridLayoutManager sglm = (StaggeredGridLayoutManager)layoutManager;
                        int itemCount = sglm.getItemCount();
                        int[] lastPositions = sglm.findFirstVisibleItemPositions(new int[sglm.getSpanCount()]);
                        int firstVisibleItemPosition = getMinPositions(lastPositions);
                        if (firstVisibleItemPosition + getChildCount() >= itemCount
                                && firstVisibleItemPosition > 0) {
                            loadMore();
                        }
                    }
                }
            }
        });
    }

    private int getMinPositions(int[] positions) {
        int size = positions.length;
        int minPosition = Integer.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            minPosition = Math.min(minPosition, positions[i]);
        }
        return minPosition;
    }

    public int getFirstItemPosition() {
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager glm = (GridLayoutManager) layoutManager;
            return glm.findFirstVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager llm = (LinearLayoutManager) layoutManager;
            return llm.findFirstVisibleItemPosition();
        }
        return 0;
    }

    private void loadMore() {
        if (!bLoadingMore && moreListener != null) {
            bLoadingMore = true;
            moreListener.onLoadMore();
        }
    }

    public void setLoadMoreComplete() {
        bLoadingMore = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        moreListener = listener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

}
