package com.hu.customview.slidingmenu;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.hu.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 修改自https://github.com/nugongshou110/SlidingMenu
 */
public class SlidingMenuActivity extends Activity {
    private ViewGroup mMenu;
    private ViewGroup mContent;
    private SlidingMenu mSlidingMenu;
    private ListView mContentListView;
    private ListView mMenuListView;
    private ImageView mMenuToggle;
    private MenuAdapter mMenuAdapter;
    private ContentAdapter mContentAdapter;
    private List<MenuItem> mMenuDatas;
    private List<ContentItem> mContentDatas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_slidingmenu);
        mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingmenu);
        mMenu = (ViewGroup) findViewById(R.id.menu);
        mContent = (ViewGroup) findViewById(R.id.content);

        mMenuListView = (ListView) mMenu.findViewById(R.id.menu_listview);
        initMenuDatas();
        initContentDatas();
        mMenuAdapter = new MenuAdapter(mMenuDatas);
        mMenuListView.setAdapter(mMenuAdapter);
        mMenuListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SlidingMenuActivity.this, "Clicked 菜单" + (position + 1), Toast.LENGTH_SHORT).show();
            }
        });

        mContentListView = (ListView) mContent.findViewById(R.id.content_listview);
        mMenuToggle = (ImageView) mContent.findViewById(R.id.menu_toggle);
        mContentAdapter = new ContentAdapter(mContentDatas);
        mContentListView.setAdapter(mContentAdapter);
        mContentListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mContentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SlidingMenuActivity.this,"Clicked Content - "+(position+1),Toast.LENGTH_SHORT).show();
            }
        });

        mMenuToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlidingMenu.toggleMenu();
            }
        });

        ViewPager viewPager = (ViewPager) LayoutInflater.from(this).inflate(R.layout.slidingmenu_menu_header, null);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mDatas.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(SlidingMenuActivity.this);
                imageView.setImageResource(mDatas.get(position));
                return imageView;
            }
        });
    }


    private List<Integer> mDatas = new ArrayList<Integer>(Arrays.asList(
            R.drawable.menu_1, R.drawable.content_1, R.drawable.menu_1, R.drawable.content_1,
            R.drawable.menu_1, R.drawable.content_1,R.drawable.menu_1, R.drawable.content_1,
            R.drawable.menu_1, R.drawable.content_1));

    private void initMenuDatas(){
        mMenuDatas = new ArrayList<>();
        mMenuDatas.add(new MenuItem(R.drawable.menu_1 , "菜单1"));
        mMenuDatas.add(new MenuItem(R.drawable.menu_1 , "菜单2"));
        mMenuDatas.add(new MenuItem(R.drawable.menu_1, "菜单3"));
        mMenuDatas.add(new MenuItem(R.drawable.menu_1, "菜单4"));
        mMenuDatas.add(new MenuItem(R.drawable.menu_1, "菜单5"));
        mMenuDatas.add(new MenuItem(R.drawable.menu_1 , "菜单6"));
        mMenuDatas.add(new MenuItem(R.drawable.menu_1 , "菜单7"));
        mMenuDatas.add(new MenuItem(R.drawable.menu_1, "菜单8"));
        mMenuDatas.add(new MenuItem(R.drawable.menu_1 , "菜单9"));
        mMenuDatas.add(new MenuItem(R.drawable.menu_1, "菜单10"));
    }
    
    private void initContentDatas(){
        mContentDatas = new ArrayList<>();
        mContentDatas.add(new ContentItem(R.drawable.content_1 , "Content - 1"));
        mContentDatas.add(new ContentItem(R.drawable.content_1 , "Content - 2"));
        mContentDatas.add(new ContentItem(R.drawable.content_1 , "Content - 3"));
        mContentDatas.add(new ContentItem(R.drawable.content_1 , "Content - 4"));
        mContentDatas.add(new ContentItem(R.drawable.content_1 , "Content - 5"));
        mContentDatas.add(new ContentItem(R.drawable.content_1 , "Content - 6"));
        mContentDatas.add(new ContentItem(R.drawable.content_1 , "Content - 7"));
        mContentDatas.add(new ContentItem(R.drawable.content_1 , "Content - 8"));
        mContentDatas.add(new ContentItem(R.drawable.content_1 , "Content - 9"));
        mContentDatas.add(new ContentItem(R.drawable.content_1 , "Content - 10"));
        mContentDatas.add(new ContentItem(R.drawable.content_1 , "Content - 11"));
        mContentDatas.add(new ContentItem(R.drawable.content_1 , "Content - 12"));
        mContentDatas.add(new ContentItem(R.drawable.content_1 , "Content - 13"));
    }

}
