package com.baozi.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baozi.utils.Constants;
import com.baozi.utils.Encrypt.DES;
import com.baozi.utils.MyRequest;
import com.baozi.utils.SetImage;
import com.baozi.utils.SharedParam;
import com.baozi.utils.UploadFile;
import com.example.baoziszxing.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;


public class NpcBusinessRegisterActivity extends Activity implements View.OnClickListener {
    private String Url = Constants.NPC_BUSINESS;
    private RequestQueue mQueue;
    private EditText zuzhimingcheng;
    private EditText zuzhidizhi;
    private EditText zuzhiyoubian;
    private EditText zuzhidaima;
    private RadioGroup jigouxingzhi;
    private String xingzhi;
    private EditText jingyingfanwei;
    private EditText farenxingming;
    private EditText farenshenfenzhenghao;
    private EditText xingming;
    private EditText shoujihao;
    private EditText zhiwu;
    private EditText youxiang;
    private EditText chuanzhen;

    private Button zhuce;

    private TextView tianjiayingyezhizhao;
    private TextView tianjiashenfenzheng;
    private ImageView yingyezhizhao;
    private ImageView farenshenfenzhengzhao;
    private ImageView iv;
    //intent标志
    private static final int REQUEST_IMAGE = 2;
    private ArrayList<String> mSelectPath;
    private Intent intent;

    private MyHandler myHandler;

    UUID uuid;
    private int i;//三次通信标志位（用户信息+两张图片）
    private int flag;//失败次数标志位
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_npc_business_register);
        init();
    }

    private void init() {
        mQueue = Volley.newRequestQueue(getApplicationContext());
        uuid = UUID.randomUUID();
        myHandler = new MyHandler();
        i = 0;
        flag = 1;

        zuzhimingcheng = (EditText) findViewById(R.id.zuzhimingcheng);
        zuzhidizhi = (EditText) findViewById(R.id.zuzhidizhi);
        zuzhiyoubian = (EditText) findViewById(R.id.zuzhiyoubian);
        zuzhidaima = (EditText) findViewById(R.id.zuzhidaima);
        jigouxingzhi = (RadioGroup) findViewById(R.id.jigouxingzhi);
        jigouxingzhi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //获取变更后的选中项的ID
                int radioButtonId = group.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton) NpcBusinessRegisterActivity.this.findViewById(radioButtonId);
                xingzhi = rb.getText().toString();
            }
        });

        xingzhi = "公司";

        jingyingfanwei = (EditText) findViewById(R.id.jingyingfanwei);
        farenxingming = (EditText) findViewById(R.id.farenxingming);
        farenshenfenzhenghao = (EditText) findViewById(R.id.farenshenfenzhenghao);
        xingming = (EditText) findViewById(R.id.xingming);
        shoujihao = (EditText) findViewById(R.id.shoujihao);
        zhiwu = (EditText) findViewById(R.id.zhiwu);
        youxiang = (EditText) findViewById(R.id.youxiang);
        chuanzhen = (EditText) findViewById(R.id.chuanzhen);

        intent = new Intent(getApplicationContext(), MultiImageSelectorActivity.class);
        // 是否显示照相机
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 图片单选
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, 0);
        tianjiayingyezhizhao = (TextView) findViewById(R.id.tianjiayingyezhizhao);
        tianjiayingyezhizhao.setOnClickListener(this);
        yingyezhizhao = (ImageView) findViewById(R.id.yingyezhizhao);
        tianjiashenfenzheng = (TextView) findViewById(R.id.tianjiashenfenzheng);
        tianjiashenfenzheng.setOnClickListener(this);
        farenshenfenzhengzhao = (ImageView) findViewById(R.id.farenshenfenzhengzhao);

        zhuce = (Button) findViewById(R.id.zhuce);
        zhuce.setOnClickListener(this);
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
                            i++;
                            switch (i) {
                                case 1:
                                    String tmps = "EntitynpcfirmapplyInsert?firmName=" + zuzhimingcheng.getText().toString() +
                                            "&firmNumber=" + zuzhidaima.getText().toString() +
                                            "&legalName=" + farenxingming.getText().toString() +
                                            "&legalIDCard=" + farenshenfenzhenghao.getText().toString() +
                                            "&contactName=" + xingming.getText().toString() +
                                            "&contactPhone=" + shoujihao.getText().toString() +
                                            "&contactTitle=" + zhiwu.getText().toString() +
                                            "&contactFax=" + chuanzhen.getText().toString() +
                                            "&contactEmail=" + youxiang.getText().toString() +
                                            "&firmAddress=" + zuzhidizhi.getText().toString() +
                                            "&firmpostCode=" + zuzhiyoubian.getText().toString() +
                                            "&firmInstitutions=" + xingzhi +
                                            "&firmBusiness=" + jingyingfanwei.getText().toString() +
                                            "&UID=" + "123456" +//需要修改
                                            "&GUID=" + uuid +
                                            "&ApplyGUID=" + uuid +
                                            "&thisuserName=" + SharedParam.getParam(getApplicationContext(), "username", "用户异常");
                                    Log.i("tag--tmps", tmps);
                                    String s = DES.getDes(tmps);
                                    Log.i("tag--DES.getDes", s);
//                                    RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
                                    MyRequest.requestPost(mQueue, Url, s, myHandler);
                                    break;
                                case 2:
                                    UploadImage(yingyezhizhao, "firmLicenseImg");//!!!!!!!!!!!!!!!!!!!!!!!!
                                    break;
                                case 3:
                                    UploadImage(farenshenfenzhengzhao, "legalIDCardImg");
                                    break;
                                case 4:
                                    progressDialog.dismiss();
                                    break;
                            }
                        } else if ("Failed".equals(state)) {//错误
                            if (flag < 5) {//5次内发送请求
                                flag++;
                                switch (i) {
                                    case 1:
                                        onClick(zhuce);
                                        break;
                                    case 2:
                                        UploadImage(yingyezhizhao, "IDCardImg");//!!!!!!!!!!!!!!!!!!!!!!!!
                                        break;
                                    case 3:
                                        UploadImage(farenshenfenzhengzhao, "IDCardImg");
                                        break;
                                }
                            } else {//5次后退出
                                flag = 1;
                                i = i - 1;
                                progressDialog.dismiss();
                            }
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

    public void fanhui(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tianjiayingyezhizhao:
                iv = yingyezhizhao;
                // 默认选择
                if (mSelectPath != null && mSelectPath.size() > 0) {
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
                }
                startActivityForResult(intent, REQUEST_IMAGE);
                break;
            case R.id.tianjiashenfenzheng:
                iv = farenshenfenzhengzhao;
                // 默认选择
                if (mSelectPath != null && mSelectPath.size() > 0) {
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
                }
                startActivityForResult(intent, REQUEST_IMAGE);
                break;
            case R.id.zhuce:
                if (flag == 1 && yingyezhizhao.getDrawable() != null && farenshenfenzhengzhao.getDrawable() != null) {
                    progressDialog = ProgressDialog.show(NpcBusinessRegisterActivity.this, "请稍等", "提交数据中...", true);
                    Message msg = myHandler.obtainMessage();
                    msg.what = Constants.REQUEST_OK;
                    msg.obj = "{state:Success}";
                    myHandler.sendMessage(msg);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                StringBuilder sb = new StringBuilder();
                for (String p : mSelectPath) {
                    sb.append(p);
//                    sb.append("\n");
                }
                Log.i("tag", "sb.toString()" + sb.toString());
                SetImage.setImage(sb.toString(), iv);
                mSelectPath = null;
            }
        }
    }

    private void UploadImage(ImageView iv, String type) {
        Log.i("tag--文件转16进制开始", new Date() + "");
        String ur = UploadFile.sendPhoto(iv);
        Log.i("tag--文件转16进制结束", new Date() + "");

        String tmps = "ImgUpload?imgStream=" + ur + "&strGuid=" + uuid + "&imgFormat=jpg&imgType=" + type + "&thisusername=" + SharedParam.getParam(getApplicationContext(), "usermane", "用户异常");
        Log.i("tag--加密开始", new Date() + "");
        String s = DES.getDes(tmps);
        Log.i("tag---DES.getDes", s);
        Log.i("tag--加密结束", new Date() + "");
//        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        MyRequest.requestPost(mQueue, Url, s, myHandler);
    }
}
