package com.baozi.activity;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.example.baoziszxing.R;

import java.util.ArrayList;
import java.util.List;


public class OrderActivity extends Activity implements OnTabChangeListener {
    // 当前选中的Tab标号  
    private int mCurSelectTabIndex = 0;
    // 默认选中第一个tab页 移动标志操作  
    private final int INIT_SELECT = 0;
    // 滑动动画执行时间  
    private final int DELAY_TIME = 500;
    private TabHost mTabHost;
    // 存放Tab页中ImageView信息  
//    public List<ImageView> imageList = new ArrayList<ImageView>();
    private List<TextView> textList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        // 取得TabHost对象  
        mTabHost = (TabHost) findViewById(R.id.tabHost);
        mTabHost.setup();            //初始化TabHost容器  
//        mTabHost = getTabHost();  
        /* 为TabHost添加标签 */
        mTabHost.addTab(mTabHost.newTabSpec("tab_1").setIndicator(composeLayout("全部", R.color.orange)).setContent(R.id.tab1));
        mTabHost.addTab(mTabHost.newTabSpec("tab_2").setIndicator(composeLayout("待付款", R.color.gray4)).setContent(R.id.tab2));
        mTabHost.addTab(mTabHost.newTabSpec("tab_3").setIndicator(composeLayout("待收货", R.color.gray4)).setContent(R.id.tab3));
        mTabHost.addTab(mTabHost.newTabSpec("tab_4").setIndicator(composeLayout("待评价", R.color.gray4)).setContent(R.id.tab4));
        mTabHost.addTab(mTabHost.newTabSpec("tab_5").setIndicator(composeLayout("无效", R.color.gray4)).setContent(R.id.tab5));
        // 设置TabHost的背景颜色  
        mTabHost.setBackgroundColor(Color.argb(150, 22, 70, 150));
        // 设置当前选中的Tab页  
        mTabHost.setCurrentTab(0);
        // TabHost添加事件  
        mTabHost.setOnTabChangedListener(this);
        // 初始化移动标识这里移到第一个tab页  
        initCurSelectTab();
    }

    public void fanhui(View view) {
        finish();
    }

    /**
     * 初始化选中Tab覆盖图片的Handler
     */
    private Handler initSelectTabHandle = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INIT_SELECT:
                    moveTopSelect(INIT_SELECT);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 初始化选中Tab覆盖图片
     */
    public void initCurSelectTab() {
        // 默认选中移动图片位置  
        Message msg = new Message();
        msg.what = INIT_SELECT;
        initSelectTabHandle.sendMessageDelayed(msg, DELAY_TIME);
    }


    /**
     * Tab页改变
     */
    public void onTabChanged(String tabId) {
        // 设置所有选项卡的图片为未选中图片  
//        imageList.get(0).setImageDrawable(getResources().getDrawable(R.drawable.xiahuaxian));
//        imageList.get(1).setImageDrawable(getResources().getDrawable(R.drawable.xiahuaxian));
//        imageList.get(2).setImageDrawable(getResources().getDrawable(R.drawable.xiahuaxian));
        textList.get(0).setTextColor(getResources().getColor(R.color.gray4));
        textList.get(1).setTextColor(getResources().getColor(R.color.gray4));
        textList.get(2).setTextColor(getResources().getColor(R.color.gray4));
        textList.get(3).setTextColor(getResources().getColor(R.color.gray4));
        textList.get(4).setTextColor(getResources().getColor(R.color.gray4));
        // 设置选中的选项卡的图片为选中图片
        if (tabId.equalsIgnoreCase("tab_1")) {
//            imageList.get(0).setImageDrawable(getResources().getDrawable(R.drawable.xiahuaxian));
            textList.get(0).setTextColor(getResources().getColor(R.color.orange));
            // 移动底部背景图片  
            moveTopSelect(0);
        } else if (tabId.equalsIgnoreCase("tab_2")) {
//            imageList.get(1).setImageDrawable(getResources().getDrawable(R.drawable.xiahuaxian));
            textList.get(1).setTextColor(getResources().getColor(R.color.orange));
            // 移动底部背景图片  
            moveTopSelect(1);
        } else if (tabId.equalsIgnoreCase("tab_3")) {
//            imageList.get(2).setImageDrawable(getResources().getDrawable(R.drawable.xiahuaxian));
            textList.get(2).setTextColor(getResources().getColor(R.color.orange));
            // 移动底部背景图片  
            moveTopSelect(2);
        } else if (tabId.equalsIgnoreCase("tab_4")) {
//            imageList.get(2).setImageDrawable(getResources().getDrawable(R.drawable.xiahuaxian));
            textList.get(3).setTextColor(getResources().getColor(R.color.orange));
            // 移动底部背景图片  
            moveTopSelect(3);
        } else if (tabId.equalsIgnoreCase("tab_5")) {
//            imageList.get(2).setImageDrawable(getResources().getDrawable(R.drawable.xiahuaxian));
            textList.get(4).setTextColor(getResources().getColor(R.color.orange));
            // 移动底部背景图片  
            moveTopSelect(4);
        }
    }

    /**
     * 移动tab选中标识图片
     * selectIndex
     * curIndex
     */
    public void moveTopSelect(int selectIndex) {
        ImageView topSelect = (ImageView) findViewById(R.id.tab_select);

        // 起始位置中心点  
        int startMid = (mTabHost.getTabWidget().getChildAt(mCurSelectTabIndex)).getLeft() + (mTabHost.getTabWidget().getChildAt(mCurSelectTabIndex)).getWidth() / 2;
        // 起始位置左边位置坐标  
        int startLeft = startMid - topSelect.getWidth() / 2;

        // 目标位置中心点  
        int endMid = (mTabHost.getTabWidget().getChildAt(selectIndex)).getLeft() + (mTabHost.getTabWidget().getChildAt(selectIndex)).getWidth() / 2;
        // 目标位置左边位置坐标  
        int endLeft = endMid - topSelect.getWidth() / 2;

        TranslateAnimation animation = new TranslateAnimation(startLeft, endLeft - topSelect.getLeft(), 0, 0);
        animation.setDuration(200);
        animation.setFillAfter(true);
        topSelect.bringToFront();
        topSelect.startAnimation(animation);

        // 改变当前选中按钮索引  
        mCurSelectTabIndex = selectIndex;

        Log.i("fs", "endMid  " + endMid + "  startLeft  " + startLeft + "  endLeft" + (endLeft - topSelect.getLeft()));
    }

    /**
     * 这个设置Tab标签本身的布局，需要TextView和ImageView不能重合 s:是文本显示的内容 i:是ImageView的图片位置
     */
    public View composeLayout(String s, int i) {
        // 定义一个LinearLayout布局  
        LinearLayout layout = new LinearLayout(this);
        // 设置布局垂直显示  
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
//        ImageView iv = new ImageView(this);
//        imageList.add(iv);
//        iv.setImageResource(i);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        lp.setMargins(0, 5, 0, 0);
//        layout.addView(iv, lp);
        // 定义TextView  
        TextView tv = new TextView(this);
        textList.add(tv);

        tv.setGravity(Gravity.CENTER);
        tv.setSingleLine(true);
        tv.setText(s);
        tv.setTextColor(getResources().getColor(i));
        tv.setTextSize(15);
        layout.addView(tv);
        return layout;
    }
}  