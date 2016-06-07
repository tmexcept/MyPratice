package com.hu.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public abstract class BaseAdapterCommon<T> extends BaseAdapter {

    protected List<T> mDataList;

    private int mViewTypeCount;

    private LayoutInflater mInflater;

    protected BaseAdapterCommon(List<T> data) {
        this(data, 1);
    }

    protected BaseAdapterCommon(List<T> data, int viewTypeCount) {
        mDataList = data;
        mViewTypeCount = viewTypeCount;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public T getItem(int position) {
        return mDataList.get(position);
    }

    /**
     * 可以被复写用于单条刷新等
     */
    public void updateData(List<T> data) {
        mDataList = data;
        notifyDataSetChanged();
    }

    /**
     * 可以被复写用于单条刷新等
     */
    public void addListData(List<T> data) {
        mDataList.addAll(data);
        notifyDataSetChanged();
    }

    public List<T> getDataList() {
        return mDataList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return mViewTypeCount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        
        BaseAdapterItemCommon<T> item;
        if (convertView == null) {
            item = getItemView(getItemViewType(position));
            convertView = mInflater.inflate(item.getLayoutResId(), parent, false);
            convertView.setTag(item);
            item.onBindViews(convertView);
            item.onSetViews();
        } else {
            item = (BaseAdapterItemCommon<T>) convertView.getTag();
        }

        if(position < mDataList.size()) {
            item.setItemModelPosition(mDataList.get(position), position);
            item.onUpdateViews(mDataList.get(position), position);
        }else{
            item.setItemModelPosition(null, position);
            item.onUpdateViews(null, position);
        }
        return convertView;
    }

    public abstract BaseAdapterItemCommon<T> getItemView(int itemViewType);
}
