package com.baozi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baozi.utils.Constants;
import com.baozi.utils.Encrypt.DES;
import com.baozi.utils.MyRequest;
import com.baozi.utils.SharedParam;
import com.example.baoziszxing.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends Activity implements View.OnFocusChangeListener {
    private String Url = Constants.USER_URL;

    private TimeCount time;

    private EditText zhanghao;
    private EditText shoujihao;
    private TextView shoujihaotishi;
    private EditText yanzhengma;
    private TextView huoquyanzhengma;
    private EditText mima;
    private ImageView mimaxianshi;
    private TextView mimatishi;
    private EditText querenmima;
    private TextView querenmimatishi;
    private Button lijizhuce;

    private MyHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
        init();
    }

    private void init() {
        myHandler = new MyHandler();

        zhanghao = (EditText) findViewById(R.id.zhanghao);

        shoujihao = (EditText) findViewById(R.id.shoujihao);
        shoujihao.setOnFocusChangeListener(this);
        shoujihaotishi = (TextView) findViewById(R.id.shoujihaotishi);

        yanzhengma = (EditText) findViewById(R.id.yanzhengma);
        yanzhengma.setOnFocusChangeListener(this);
        huoquyanzhengma = (TextView) findViewById(R.id.huoquyanzhengma);
        huoquyanzhengma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendyanzhengma();
                time.start();//开始计时
                //后台打印验证码
                Log.i("tag--yanzhengma", SharedParam.getParam(getApplicationContext(), "yanzhengma", "0"));
            }
        });


        mima = (EditText) findViewById(R.id.mima);
        mima.setOnFocusChangeListener(this);
        mimatishi = (TextView) findViewById(R.id.mimatishi);
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

        querenmima = (EditText) findViewById(R.id.querenmima);
        querenmima.setOnFocusChangeListener(this);
        querenmimatishi = (TextView) findViewById(R.id.querenmimatishi);

        lijizhuce = (Button) findViewById(R.id.lijizhuce);

        lijizhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lijizhuce.setFocusable(true);
                lijizhuce.setFocusableInTouchMode(true);
                lijizhuce.requestFocus();
                lijizhuce.setFocusable(false);
                if (isOk()) {
                    String tmps = "NPCUserRegister?username=" + zhanghao.getText().toString() +
                            "&password=" + DES.getDes(mima.getText().toString()) +
                            "&phone=" + shoujihao.getText().toString();
                    Log.i("tag--tmps", tmps);
                    String s = DES.getDes(tmps);
                    Log.i("tag--DES.getDes", s);
                    RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
                    MyRequest.requestPost(mQueue, Url, s, myHandler);
                }
            }
        });
    }

    public void fanhui(View view) {
        finish();
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
                        JSONTokener jsonParser = new JSONTokener(msg.obj.toString());
                        JSONObject person = (JSONObject) jsonParser.nextValue();
                        String state = person.optString("state");
                        Log.i("tag--", state);
                        if ("Success".equals(state)) {//成功

                        } else if ("ExistsUser".equals(state)) {//用户已存在

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case Constants.REQUEST_SORRY:

                    break;
            }
        }
    }

    //选择下一项 立即判断是否正确
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {//editext失去焦点时执行
            switch (v.getId()) {
                case R.id.shoujihao:
                    if (isMobile(shoujihao.getText().toString()) || shoujihao.getText().toString().length() == 0) {
                        shoujihaotishi.setText(null);
                    } else {
                        shoujihaotishi.setText("手机号格式不正确");
                    }
                    break;
                case R.id.yanzhengma:
                    if (!SharedParam.getParam(getApplicationContext(), "yanzhengma", "0").equals(yanzhengma.getText().toString()) && yanzhengma.getText().toString().length() != 0) {
                        yanzhengma.setText(null);
                        yanzhengma.setHint("验证码错误");
                    }
                    break;
                case R.id.mima:
                    if (isPwd(mima.getText().toString()) || mima.getText().toString().length() == 0) {
                        mimatishi.setText(null);
                    } else {
                        mimatishi.setText("密码格式错误");
                    }
                    break;
                case R.id.querenmima:
                    if (mima.getText().toString().equals(querenmima.getText().toString()) || querenmima.getText().toString().length() == 0) {
                        querenmimatishi.setText(null);
                    } else {
                        querenmimatishi.setText("两次密码不一致");
                    }
                    break;
            }
        }
    }
    //验证方法

    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    private boolean isMobile(String str) {
        Pattern p;
        Matcher m;
        boolean b;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号  
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 密码验证
     *
     * @param str
     * @return 验证通过返回true
     */
    private boolean isPwd(String str) {
        Pattern p;
        Matcher m;
        boolean b;
        p = Pattern.compile("^[^ ]+[\\s\\S]{4,14}[^ ]$"); // 验证密码 6-16
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    private void sendyanzhengma() {
        int yanzhengma = (int) (Math.random() * 9000 + 1000);
        SharedParam.setParam(this, "yanzhengma", String.valueOf(yanzhengma));
//        fasong()
    }

    private boolean isOk() {
        boolean b = true;
        if (zhanghao.getText().toString().trim().length() == 0) {
            b = false;
            zhanghao.setText(null);
            zhanghao.setHint("帐号不能为空");
        }
        if (shoujihao.getText().toString().trim().length() == 0) {
            b = false;
            shoujihao.setText(null);
            shoujihao.setHint("手机号不能为空");
        }
        if (yanzhengma.getText().toString().trim().length() == 0) {
            b = false;
            yanzhengma.setText(null);
            yanzhengma.setHint("验证码错误");
        }
        if (mima.getText().toString().trim().length() == 0) {
            b = false;
            mima.setText(null);
            mima.setHint("密码不能为空");
        }
        if (querenmima.getText().toString().trim().length() == 0) {
            b = false;
            querenmima.setText(null);
            querenmima.setHint("确认密码不能为空");
        }
        if (!isMobile(shoujihao.getText().toString())) {
            b = false;
        }
        if (!isPwd(mima.getText().toString())) {
            b = false;
        }
        if (!mima.getText().toString().equals(querenmima.getText().toString())) {
            b = false;
        }
        return b;
    }

    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            huoquyanzhengma.setText("重新验证");
            huoquyanzhengma.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            huoquyanzhengma.setClickable(false);
            huoquyanzhengma.setText(millisUntilFinished / 1000 + "秒");
        }
    }
}
