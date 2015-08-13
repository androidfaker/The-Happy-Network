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
        time = new TimeCount(60000, 1000);//����CountDownTimer����
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
                time.start();//��ʼ��ʱ
                //��̨��ӡ��֤��
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
                        //����EditText�ı�Ϊ�ɼ���
                        mima.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        mima.setSelection(mima.getText().length());
                        break;
                    case MotionEvent.ACTION_UP:
                        //����EditText�ı�Ϊ���ص�
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

        // ���������д�˷���,��������  
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
                        if ("Success".equals(state)) {//�ɹ�

                        } else if ("ExistsUser".equals(state)) {//�û��Ѵ���

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

    //ѡ����һ�� �����ж��Ƿ���ȷ
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {//editextʧȥ����ʱִ��
            switch (v.getId()) {
                case R.id.shoujihao:
                    if (isMobile(shoujihao.getText().toString()) || shoujihao.getText().toString().length() == 0) {
                        shoujihaotishi.setText(null);
                    } else {
                        shoujihaotishi.setText("�ֻ��Ÿ�ʽ����ȷ");
                    }
                    break;
                case R.id.yanzhengma:
                    if (!SharedParam.getParam(getApplicationContext(), "yanzhengma", "0").equals(yanzhengma.getText().toString()) && yanzhengma.getText().toString().length() != 0) {
                        yanzhengma.setText(null);
                        yanzhengma.setHint("��֤�����");
                    }
                    break;
                case R.id.mima:
                    if (isPwd(mima.getText().toString()) || mima.getText().toString().length() == 0) {
                        mimatishi.setText(null);
                    } else {
                        mimatishi.setText("�����ʽ����");
                    }
                    break;
                case R.id.querenmima:
                    if (mima.getText().toString().equals(querenmima.getText().toString()) || querenmima.getText().toString().length() == 0) {
                        querenmimatishi.setText(null);
                    } else {
                        querenmimatishi.setText("�������벻һ��");
                    }
                    break;
            }
        }
    }
    //��֤����

    /**
     * �ֻ�����֤
     *
     * @param str
     * @return ��֤ͨ������true
     */
    private boolean isMobile(String str) {
        Pattern p;
        Matcher m;
        boolean b;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // ��֤�ֻ���  
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * ������֤
     *
     * @param str
     * @return ��֤ͨ������true
     */
    private boolean isPwd(String str) {
        Pattern p;
        Matcher m;
        boolean b;
        p = Pattern.compile("^[^ ]+[\\s\\S]{4,14}[^ ]$"); // ��֤���� 6-16
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
            zhanghao.setHint("�ʺŲ���Ϊ��");
        }
        if (shoujihao.getText().toString().trim().length() == 0) {
            b = false;
            shoujihao.setText(null);
            shoujihao.setHint("�ֻ��Ų���Ϊ��");
        }
        if (yanzhengma.getText().toString().trim().length() == 0) {
            b = false;
            yanzhengma.setText(null);
            yanzhengma.setHint("��֤�����");
        }
        if (mima.getText().toString().trim().length() == 0) {
            b = false;
            mima.setText(null);
            mima.setHint("���벻��Ϊ��");
        }
        if (querenmima.getText().toString().trim().length() == 0) {
            b = false;
            querenmima.setText(null);
            querenmima.setHint("ȷ�����벻��Ϊ��");
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

    /* ����һ������ʱ���ڲ��� */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//��������Ϊ��ʱ��,�ͼ�ʱ��ʱ����
        }

        @Override
        public void onFinish() {//��ʱ���ʱ����
            huoquyanzhengma.setText("������֤");
            huoquyanzhengma.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//��ʱ������ʾ
            huoquyanzhengma.setClickable(false);
            huoquyanzhengma.setText(millisUntilFinished / 1000 + "��");
        }
    }
}
