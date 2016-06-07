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
public class MenuAdapter extends BaseAdapterCommon<MenuItem> {
    public MenuAdapter(List<MenuItem> datas){
        super(datas);
    }

    @Override
    public BaseAdapterItemCommon<MenuItem> getItemView(int itemViewType) {
        return new AdapterItem();
    }

    class AdapterItem extends BaseAdapterItemCommon<MenuItem>{

        @Override
        public int getLayoutResId() {
            return R.layout.slidingmenu_left_menu_item;
        }

        @Override
        public void onSetViews() {

        }

        @Override
        public void onUpdateViews(MenuItem model, int position) {
            ((ImageView)getView(R.id.menu_imageview)).setImageResource(model.getResId());
            ((TextView)getView(R.id.menu_textview)).setText(model.getName());
        }
    }
}
