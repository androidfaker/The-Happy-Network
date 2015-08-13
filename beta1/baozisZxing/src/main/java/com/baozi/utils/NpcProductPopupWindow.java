package com.baozi.utils;

//NpcProductPopupWindow


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.example.baoziszxing.R;


public class NpcProductPopupWindow extends PopupWindow {


    private Button positive, negative;
    public EditText title,info;
    private View mMenuView;

    public NpcProductPopupWindow(Activity context,OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.npc_product_popup_window, null);
        title= (EditText) mMenuView.findViewById(R.id.title);
        info= (EditText) mMenuView.findViewById(R.id.info);
        positive = (Button) mMenuView.findViewById(R.id.positive);
        negative = (Button) mMenuView.findViewById(R.id.negative);
        //ȡ��ť  
        negative.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                //��ٵ�����  
                dismiss();
            }
        });
        //���ð�ť����  
        positive.setOnClickListener(itemsOnClick);
        //����SelectPicPopupWindow��View  
        this.setContentView(mMenuView);
        //����SelectPicPopupWindow��������Ŀ�  
        this.setWidth(LayoutParams.FILL_PARENT);
        //����SelectPicPopupWindow��������ĸ�  
        this.setHeight(LayoutParams.WRAP_CONTENT);
        //����SelectPicPopupWindow��������ɵ��  
        this.setFocusable(true);
        //����SelectPicPopupWindow�������嶯��Ч��  
        this.setAnimationStyle(R.style.PopupAnimation);
        //ʵ��һ��ColorDrawable��ɫΪ��͸��  
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        //����SelectPicPopupWindow��������ı���  
        this.setBackgroundDrawable(dw);
        //mMenuView���OnTouchListener�����жϻ�ȡ����λ�������ѡ�����������ٵ�����  
//        mMenuView.setOnTouchListener(new OnTouchListener() {
//
//            public boolean onTouch(View v, MotionEvent event) {
//
//                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
//                int y=(int) event.getY();
//                if(event.getAction()==MotionEvent.ACTION_UP){
//                    if(y<height){
//                        dismiss();
//                    }
//                }
//                return true;
//            }
//        });

    }

}  
