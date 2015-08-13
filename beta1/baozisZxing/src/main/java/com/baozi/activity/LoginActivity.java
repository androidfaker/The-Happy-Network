package com.baozi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baozi.utils.Constants;
import com.baozi.utils.Encrypt.DES;
import com.baozi.utils.MyRequest;
import com.baozi.utils.SharedParam;
import com.example.baoziszxing.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;



public class LoginActivity extends Activity {
    private String Url = Constants.USER_URL;

    private EditText zhanghao;
    private EditText mima;
    private ImageView mimaxianshi;
    private Button denglu;

    private MyHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    //开始
    private void init() {
        myHandler = new MyHandler();

        mima = (EditText) findViewById(R.id.mima);
        mimaxianshi = (ImageView) findViewById(R.id.mimaxianshi);
        mimaxianshi.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //设置EditText文本为可见的
                        mima.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        mima.setSelection(mima.getText().length());
                        break;
                    case MotionEvent.ACTION_UP:
                        //设置EditText文本为隐藏的
                        mima.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        mima.setSelection(mima.getText().length());
                        break;
                }
                return false;
            }
        });

        zhanghao = (EditText) findViewById(R.id.zhanghao);
        denglu = (Button) findViewById(R.id.denglu);
        denglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmps = "NPCUserLogin?username=" + zhanghao.getText().toString() +
                        "&password=" + DES.getDes(mima.getText().toString());
                Log.i("tag--tmps", tmps);
                String s = DES.getDes(tmps);
                Log.i("tag--DES.getDes", s);
                RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
                MyRequest.requestPost(mQueue, Url, s, myHandler);
            }
        });
    }

    class MyHandler extends Handler {
        public MyHandler() {
        }

        // 子类必须重写此方法,接受数据  
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub  
            super.handleMessage(msg);
            switch (msg.what) {
                case Constants.REQUEST_OK:
                    try {
                        JSONTokener jsonData = new JSONTokener(msg.obj.toString());
                        JSONObject json = (JSONObject) jsonData.nextValue();
                        String state = json.optString("state");
                        JSONArray array = json.optJSONArray("data");
                        String id = ((JSONObject) array.opt(0)).optString("id");
                        Log.i("tag--id", id);
                        Log.i("tag--", state);
                        if ("Success".equals(state)) {//成功
                            SharedParam.setParam(getApplicationContext(), "username", zhanghao.getText().toString());
                            SharedParam.setParam(getApplicationContext(), "password", DES.getDes(mima.getText().toString()));
                            SharedParam.setParam(getApplicationContext(), "id", id);
                        } else if ("UnPassword".equals(state)) {//密码错误

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case Constants.REQUEST_SORRY:

                    break;
            }
        }
    }


    //结束 
    public void fanhui(View view) {
        finish();
    }

}
