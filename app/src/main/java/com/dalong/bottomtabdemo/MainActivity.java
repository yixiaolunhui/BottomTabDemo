package com.dalong.bottomtabdemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dalong.buttomtablayout.BottomTab;
import com.dalong.buttomtablayout.BottomTabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String[] mTabNames = {"首页", "消息", "搜索", "我的"};

    /**
     * 默认状态下的本地图片
     */
    private int[] mUnSelectIcons = {
            R.mipmap.tabbar_home,
            R.mipmap.tabbar_message_center,
            R.mipmap.tabbar_discover,
            R.mipmap.tabbar_profile};

    /**
     * 默认状态下的本地图片
     */
    private int[] mSelectIcons = {
            R.mipmap.tabbar_home_highlighted,
            R.mipmap.tabbar_message_center_highlighted,
            R.mipmap.tabbar_discover_highlighted,
            R.mipmap.tabbar_profile_highlighted};

    /**
     * 默认状态下的网络图片
     */
    private String[] mUnSelectUrls={
            "http://www.androidstudy.cn/img/tabbar_home.png",
            "http://www.androidstudy.cn/img/tabbar_message_center.png",
            "http://www.androidstudy.cn/img/tabbar_discover.png",
            "http://www.androidstudy.cn/img/tabbar_profile.png"};

    /**
     * 选中状态下的网络图片
     */
    private String[] mSelectUrls={
            "http://www.androidstudy.cn/img/tabbar_home_selected.png",
            "http://www.androidstudy.cn/img/tabbar_message_center_highlighted.png",
            "http://www.androidstudy.cn/img/tabbar_discover_highlighted.png",
            "http://www.androidstudy.cn/img/tabbar_profile_highlighted.png"};


    /**
     * 未选中的颜色
     */
    private int mUnSelectColor = R.color.unSelectColor;

    /**
     * 选中的颜色
     */
    private int mSelectColor = R.color.SelectColor;

    private BottomTabLayout mBottomTabLayout;
    private List<BottomTab> mBottomTabs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBottomTabLayout=(BottomTabLayout)findViewById(R.id.mBottomTabLayout);
        mBottomTabLayout.setOnTabChangeListener(new BottomTabLayout.OnTabChangeListener() {
            @Override
            public void onTabSelect(int position) {
                Toast.makeText(MainActivity.this, "您选择了"+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabSelected(int position) {
                Toast.makeText(MainActivity.this, "您已经选择了"+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化数据
     * @param isUrl
     */
    private void initData(boolean isUrl) {
        mBottomTabs.clear();
        for (int i=0;i<mUnSelectIcons.length;i++){
            BottomTab mBottomTab=new BottomTab(mTabNames[i],mUnSelectColor,
                    mSelectColor,mUnSelectIcons[i],mSelectIcons[i],isUrl?mUnSelectUrls[i]:null,isUrl?mSelectUrls[i]:null);
            mBottomTabs.add(mBottomTab);
        }
        mBottomTabLayout.setBottomTabData(mBottomTabs);
    }

    /**
     * 设置本地图片
     * @param view
     */
    public void onLocalIcon(View view){
        initData(false);
    }

    /**
     * 设置网络图片
     * @param view
     */
    public void onNetIcon(View view){
        initData(true);
    }

    /**
     * 设置小红点
     * @param view
     */
    public void onTabNum(View view){
        mBottomTabLayout.setBottomTabNum(new Random().nextInt(4),new Random().nextInt(100));
    }

    /**
     * 取消小红点
     * @param view
     */
    public void onClearTabNum(View view){
        mBottomTabLayout.clearAllBottomTabNum();
    }
}
