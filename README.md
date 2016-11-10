# BottomTabDemo
app底部切换tab控件，本代码加载网络图片使用开源glide框架。

#效果图

![image](https://github.com/dalong982242260/BottomTabDemo/blob/master/img/bottomtab.gif?raw=true)

#优点
        1、动态设置tab，方便易用
        2、可以加载网络icon
        3、可以设置消息数量

#使用

 xml：
 
               <com.dalong.buttomtablayout.BottomTabLayout
                     android:id="@+id/mBottomTabLayout"
                     android:layout_width="match_parent"
                     android:layout_alignParentBottom="true"
                     android:layout_height="wrap_content"/>             


 java：
 
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

       mBottomTabLayout.setBottomTabNum(new Random().nextInt(4),new Random().nextInt(100));//设置对应index的小红点数量
       
       mBottomTabLayout.clearAllBottomTabNum();//清除所有的小红点


#感谢
[glide](https://github.com/bumptech/glide)