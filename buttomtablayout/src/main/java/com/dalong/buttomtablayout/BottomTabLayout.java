package com.dalong.buttomtablayout;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * 底部tab切换布局
 * Created by zhouweilong on 2016/11/10.
 */

public class BottomTabLayout extends LinearLayout {

    private  Context mContext;
    //底部tab数据
    private List<BottomTab> mBottomTabs=new ArrayList<>();
    //底部所有itemViews
    private List<View> mBottomTabViews=new ArrayList<>();
    //当前选择的tab index
    private int currentTab;
    //tab切换回调接口
    private OnTabChangeListener mOnTabChangeListener;

    public BottomTabLayout(Context context) {
        this(context,null);
    }

    public BottomTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BottomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        init();
    }

    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);
    }

    /**
     * 设置tab数据
     * @param mBottomTabs
     */
    public void setBottomTabData(List<BottomTab> mBottomTabs){
        if(mBottomTabs==null||mBottomTabs.size()==0){
          return;
        }
        this.mBottomTabViews.clear();
        //清除所有的子view
        removeAllViews();
        this.mBottomTabs=mBottomTabs;
        initTabViews();
    }

    /**
     * 设置底部小红点的数量
     * @param index
     * @param num
     */
    public void setBottomTabNum(int index,int num){
        for (int i=0;i<mBottomTabViews.size();i++){
            TextView mTabNum= (TextView) mBottomTabViews.get(i).findViewById(R.id.tab_item_num);
            if(index==i&&num>0){
                mTabNum.setVisibility(VISIBLE);
                mTabNum.setText(num>=100?"99+":String.valueOf(num));
            }
        }
    }

    /**
     * 清除所有的小红点的数量
     */
    public void clearAllBottomTabNum(){
        for (int i=0;i<mBottomTabViews.size();i++){
            TextView mTabNum= (TextView) mBottomTabViews.get(i).findViewById(R.id.tab_item_num);
            mTabNum.setText("");
            mTabNum.setVisibility(GONE);
        }
    }

    /**
     * 初始化tabviews
     */
    public void initTabViews(){
        View tabView;
        for (int index=0;index<mBottomTabs.size();index++){
            tabView= LayoutInflater.from(mContext).inflate(R.layout.view_bottom_tab,null);
            tabView.setTag(index);
            addTabView(index,tabView);
            mBottomTabViews.add(tabView);
        }
        setCurrentTab(0);
    }

    /**
     * 添加tabview
     * @param index
     * @param tabView
     */
    private void addTabView(int index, View tabView) {
        TextView mTabName= (TextView) tabView.findViewById(R.id.tab_item_name);
        ImageView mTabIcon= (ImageView) tabView.findViewById(R.id.tab_item_icon);
        mTabName.setText(mBottomTabs.get(index).getTabName());

        int tabUnSelectIcon=mBottomTabs.get(index).getTabUnSelectIcon();
        String tabUnSelectUrl=mBottomTabs.get(index).getTabUnSelectUrl();
        String tabSelectUrl=mBottomTabs.get(index).getTabSelectUrl();

        //如果网络图片中选中或者不选中的图片其中之一为空都使用本地图片
        if(TextUtils.isEmpty(tabUnSelectUrl)||TextUtils.isEmpty(tabSelectUrl)){
            mTabIcon.setImageResource(tabUnSelectIcon);
        }else{
            Glide.with(mContext)
                    .load(tabUnSelectUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(tabUnSelectIcon)//使用本地对应的图片作为默认加载图片
                    .into(mTabIcon);
        }

        //设置tab点击事件 其中多加了一个重复点击的回调 为了有的需求是点击多次也要刷新的奇葩需求  比如我们
        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                if (currentTab != position) {
                    setCurrentTab(position);
                    if (mOnTabChangeListener != null) {
                        mOnTabChangeListener.onTabSelect(position);
                    }
                } else {
                    if (mOnTabChangeListener != null) {
                        mOnTabChangeListener.onTabSelected(position);
                    }
                }
            }
        });
        LinearLayout.LayoutParams params =  new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        addView(tabView, index, params);
    }

    /**
     * 设置选中
     * @param currentTab
     */
    public void setCurrentTab(int currentTab) {
        this.currentTab = currentTab;
        updateTabState(currentTab);
    }


    /**
     * 更新选中的和未选中的样式
     * @param currentTab
     */
    private void updateTabState(int currentTab) {
        for (int index=0;index<mBottomTabs.size();index++){
            View tabView = getChildAt(index);
            final boolean isSelect = index == currentTab;
            BottomTab mBottomTab = mBottomTabs.get(index);
            TextView mTabName = (TextView) tabView.findViewById(R.id.tab_item_name);
            ImageView mTabIcon = (ImageView) tabView.findViewById(R.id.tab_item_icon);

            //根据判别是否是选择的哪一个来设置对应的颜色值
            mTabName.setTextColor(isSelect ? getResources().getColor(mBottomTab.getTabNameSelectColor())
                    : getResources().getColor(mBottomTab.getTabNameUnSelectColor()));

            //判别网络图片url是不是为空要是为空的话就是使用本地的图片如果不为空就用网络图片  默认加载的图片为对应的本地图片 避免加载空白问题
            if(android.text.TextUtils.isEmpty(mBottomTab.getTabSelectUrl())||
                    android.text.TextUtils.isEmpty(mBottomTab.getTabUnSelectUrl())){
                mTabIcon.setImageResource(isSelect ?mBottomTab.getTabSelectIcon(): mBottomTab.getTabUnSelectIcon());
            }else{
                Glide.with(mContext).load(isSelect?mBottomTab.getTabSelectUrl():mBottomTab.getTabUnSelectUrl()).
                        diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(isSelect?mBottomTab.getTabSelectIcon():mBottomTab.getTabUnSelectIcon())
                        .into(mTabIcon);
            }
        }
    }


    /**
     * 设置回调实现
     * @param mOnTabChangeListener
     */
    public void setOnTabChangeListener(OnTabChangeListener mOnTabChangeListener){
        this.mOnTabChangeListener=mOnTabChangeListener;
    }

    /**
     * tab切换回调接口
     */
    public interface OnTabChangeListener {
        //tab选择回调
        void onTabSelect(int position);
        //tab重复选择回调
        void onTabSelected(int position);
    }


}
