package com.hu.customview.slidingmenu;

import android.widget.ImageView;
import android.widget.TextView;

import com.hu.R;
import com.hu.adapter.BaseAdapterCommon;
import com.hu.adapter.BaseAdapterItemCommon;

import java.util.List;

/**
 * Created by user on 2016/2/19.
 */
public class ContentAdapter extends BaseAdapterCommon<ContentItem> {
    
    public ContentAdapter(List<ContentItem> datas){
        super(datas);
    }

    @Override
    public BaseAdapterItemCommon<ContentItem> getItemView(int itemViewType) {
        return new AdapterItem();
    }
    
    class AdapterItem extends BaseAdapterItemCommon<ContentItem>{

        @Override
        public int getLayoutResId() {
            return R.layout.slidingmenu_content_item;
        }

        @Override
        public void onSetViews() {

        }

        @Override
        public void onUpdateViews(ContentItem model, int position) {
            ((ImageView)getView(R.id.content_imageview)).setImageResource(model.getResId());
            ((TextView)getView(R.id.content_textview)).setText(model.getName());
        }
    }
}
