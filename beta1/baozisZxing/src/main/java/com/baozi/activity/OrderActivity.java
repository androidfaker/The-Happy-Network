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
    // ��ǰѡ�е�Tab���  
    private int mCurSelectTabIndex = 0;
    // Ĭ��ѡ�е�һ��tabҳ �ƶ���־����  
    private final int INIT_SELECT = 0;
    // ��������ִ��ʱ��  
    private final int DELAY_TIME = 500;
    private TabHost mTabHost;
    // ���Tabҳ��ImageView��Ϣ  
//    public List<ImageView> imageList = new ArrayList<ImageView>();
    private List<TextView> textList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        // ȡ��TabHost����  
        mTabHost = (TabHost) findViewById(R.id.tabHost);
        mTabHost.setup();            //��ʼ��TabHost����  
//        mTabHost = getTabHost();  
        /* ΪTabHost��ӱ�ǩ */
        mTabHost.addTab(mTabHost.newTabSpec("tab_1").setIndicator(composeLayout("ȫ��", R.color.orange)).setContent(R.id.tab1));
        mTabHost.addTab(mTabHost.newTabSpec("tab_2").setIndicator(composeLayout("������", R.color.gray4)).setContent(R.id.tab2));
        mTabHost.addTab(mTabHost.newTabSpec("tab_3").setIndicator(composeLayout("���ջ�", R.color.gray4)).setContent(R.id.tab3));
        mTabHost.addTab(mTabHost.newTabSpec("tab_4").setIndicator(composeLayout("������", R.color.gray4)).setContent(R.id.tab4));
        mTabHost.addTab(mTabHost.newTabSpec("tab_5").setIndicator(composeLayout("��Ч", R.color.gray4)).setContent(R.id.tab5));
        // ����TabHost�ı�����ɫ  
        mTabHost.setBackgroundColor(Color.argb(150, 22, 70, 150));
        // ���õ�ǰѡ�е�Tabҳ  
        mTabHost.setCurrentTab(0);
        // TabHost����¼�  
        mTabHost.setOnTabChangedListener(this);
        // ��ʼ���ƶ���ʶ�����Ƶ���һ��tabҳ  
        initCurSelectTab();
    }

    public void fanhui(View view) {
        finish();
    }

    /**
     * ��ʼ��ѡ��Tab����ͼƬ��Handler
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
     * ��ʼ��ѡ��Tab����ͼƬ
     */
    public void initCurSelectTab() {
        // Ĭ��ѡ���ƶ�ͼƬλ��  
        Message msg = new Message();
        msg.what = INIT_SELECT;
        initSelectTabHandle.sendMessageDelayed(msg, DELAY_TIME);
    }


    /**
     * Tabҳ�ı�
     */
    public void onTabChanged(String tabId) {
        // ��������ѡ���ͼƬΪδѡ��ͼƬ  
//        imageList.get(0).setImageDrawable(getResources().getDrawable(R.drawable.xiahuaxian));
//        imageList.get(1).setImageDrawable(getResources().getDrawable(R.drawable.xiahuaxian));
//        imageList.get(2).setImageDrawable(getResources().getDrawable(R.drawable.xiahuaxian));
        textList.get(0).setTextColor(getResources().getColor(R.color.gray4));
        textList.get(1).setTextColor(getResources().getColor(R.color.gray4));
        textList.get(2).setTextColor(getResources().getColor(R.color.gray4));
        textList.get(3).setTextColor(getResources().getColor(R.color.gray4));
        textList.get(4).setTextColor(getResources().getColor(R.color.gray4));
        // ����ѡ�е�ѡ���ͼƬΪѡ��ͼƬ
        if (tabId.equalsIgnoreCase("tab_1")) {
//            imageList.get(0).setImageDrawable(getResources().getDrawable(R.drawable.xiahuaxian));
            textList.get(0).setTextColor(getResources().getColor(R.color.orange));
            // �ƶ��ײ�����ͼƬ  
            moveTopSelect(0);
        } else if (tabId.equalsIgnoreCase("tab_2")) {
//            imageList.get(1).setImageDrawable(getResources().getDrawable(R.drawable.xiahuaxian));
            textList.get(1).setTextColor(getResources().getColor(R.color.orange));
            // �ƶ��ײ�����ͼƬ  
            moveTopSelect(1);
        } else if (tabId.equalsIgnoreCase("tab_3")) {
//            imageList.get(2).setImageDrawable(getResources().getDrawable(R.drawable.xiahuaxian));
            textList.get(2).setTextColor(getResources().getColor(R.color.orange));
            // �ƶ��ײ�����ͼƬ  
            moveTopSelect(2);
        } else if (tabId.equalsIgnoreCase("tab_4")) {
//            imageList.get(2).setImageDrawable(getResources().getDrawable(R.drawable.xiahuaxian));
            textList.get(3).setTextColor(getResources().getColor(R.color.orange));
            // �ƶ��ײ�����ͼƬ  
            moveTopSelect(3);
        } else if (tabId.equalsIgnoreCase("tab_5")) {
//            imageList.get(2).setImageDrawable(getResources().getDrawable(R.drawable.xiahuaxian));
            textList.get(4).setTextColor(getResources().getColor(R.color.orange));
            // �ƶ��ײ�����ͼƬ  
            moveTopSelect(4);
        }
    }

    /**
     * �ƶ�tabѡ�б�ʶͼƬ
     * selectIndex
     * curIndex
     */
    public void moveTopSelect(int selectIndex) {
        ImageView topSelect = (ImageView) findViewById(R.id.tab_select);

        // ��ʼλ�����ĵ�  
        int startMid = (mTabHost.getTabWidget().getChildAt(mCurSelectTabIndex)).getLeft() + (mTabHost.getTabWidget().getChildAt(mCurSelectTabIndex)).getWidth() / 2;
        // ��ʼλ�����λ������  
        int startLeft = startMid - topSelect.getWidth() / 2;

        // Ŀ��λ�����ĵ�  
        int endMid = (mTabHost.getTabWidget().getChildAt(selectIndex)).getLeft() + (mTabHost.getTabWidget().getChildAt(selectIndex)).getWidth() / 2;
        // Ŀ��λ�����λ������  
        int endLeft = endMid - topSelect.getWidth() / 2;

        TranslateAnimation animation = new TranslateAnimation(startLeft, endLeft - topSelect.getLeft(), 0, 0);
        animation.setDuration(200);
        animation.setFillAfter(true);
        topSelect.bringToFront();
        topSelect.startAnimation(animation);

        // �ı䵱ǰѡ�а�ť����  
        mCurSelectTabIndex = selectIndex;

        Log.i("fs", "endMid  " + endMid + "  startLeft  " + startLeft + "  endLeft" + (endLeft - topSelect.getLeft()));
    }

    /**
     * �������Tab��ǩ����Ĳ��֣���ҪTextView��ImageView�����غ� s:���ı���ʾ������ i:��ImageView��ͼƬλ��
     */
    public View composeLayout(String s, int i) {
        // ����һ��LinearLayout����  
        LinearLayout layout = new LinearLayout(this);
        // ���ò��ִ�ֱ��ʾ  
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
//        ImageView iv = new ImageView(this);
//        imageList.add(iv);
//        iv.setImageResource(i);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        lp.setMargins(0, 5, 0, 0);
//        layout.addView(iv, lp);
        // ����TextView  
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