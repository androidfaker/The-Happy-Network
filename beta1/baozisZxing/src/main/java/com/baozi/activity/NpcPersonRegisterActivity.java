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


public class NpcPersonRegisterActivity extends Activity implements View.OnClickListener {
    private String Url = Constants.NPC_PERSON;
    private RequestQueue mQueue;
    private EditText zhenshixingming;
    private EditText shenfenzhenghao;

    private TextView tianjiazhaopian;
    private ImageView shouchishenfenzheng;

    private Button zhuce;
    //intent��־
    private static final int REQUEST_IMAGE = 2;
    private ArrayList<String> mSelectPath;

    private MyHandler myHandler;

    private int i;//����ͨ�ű�־λ���û���Ϣ+����ͼƬ��
    private int flag;//ʧ�ܴ�����־λ
    private UUID uuid;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_npc_person_register);
        init();
    }

    private void init() {
        mQueue = Volley.newRequestQueue(getApplicationContext());
        uuid = UUID.randomUUID();
        myHandler = new MyHandler();
        i = 0;
        flag = 1;

        zhenshixingming = (EditText) findViewById(R.id.zhenshixingming);
        shenfenzhenghao = (EditText) findViewById(R.id.shenfenzhenghao);

        tianjiazhaopian = (TextView) findViewById(R.id.tianjiazhaopian);
        tianjiazhaopian.setOnClickListener(this);
        shouchishenfenzheng = (ImageView) findViewById(R.id.shouchishenfenzheng);
        zhuce = (Button) findViewById(R.id.zhuce);
        zhuce.setOnClickListener(this);
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
                            i++;
                            switch (i) {
                                case 1:
                                    String tmps = "EntitynpcpersonalapplyInsert?UID=" + SharedParam.getParam(getApplicationContext(), "id", "0") +//��Ҫ�޸�
                                            "&personalPhone=" + "123456" +//��Ҫ�޸�
                                            "&personalName=" + zhenshixingming.getText().toString() +
                                            "&personalIDCard=" + shenfenzhenghao.getText().toString() +
                                            "&GUID=" + uuid +
                                            "&ApplyGUID=" + uuid +
                                            "&thisuserName=" + SharedParam.getParam(getApplicationContext(), "username", "�û�������");//��Ҫ�޸�
                                    Log.i("tag--tmps", tmps);
                                    String s = DES.getDes(tmps);
                                    Log.i("tag--DES.getDes", s);
//                                    RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
                                    MyRequest.requestPost(mQueue, Url, s, myHandler);
                                    break;
                                case 2:
                                    UploadImage(shouchishenfenzheng, "personalIDCardImg");
                                    break;
                                case 3:
                                    progressDialog.dismiss();
                                    break;
                            }
                        } else if ("Failed".equals(state)) {//����
                            if (flag < 5) {//5���ڷ�������
                                flag++;
                                switch (i) {
                                    case 1:
                                        onClick(zhuce);
                                        break;
                                    case 2:
                                        UploadImage(shouchishenfenzheng, "personalIDCardImg");
                                        break;
                                }
                            } else {//5�κ��˳�
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
            case R.id.tianjiazhaopian:
                Intent intent = new Intent(NpcPersonRegisterActivity.this, MultiImageSelectorActivity.class);
                // �Ƿ���ʾ�����
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                // ͼƬ��ѡ
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, 0);
                // Ĭ��ѡ��
                if (mSelectPath != null && mSelectPath.size() > 0) {
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
                }
                startActivityForResult(intent, REQUEST_IMAGE);
                break;
            case R.id.zhuce:
                if (flag == 1 && shouchishenfenzheng.getDrawable() != null) {
                    progressDialog = ProgressDialog.show(NpcPersonRegisterActivity.this, "���Ե�", "�ύ������...", true);
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
                SetImage.setImage(sb.toString(), shouchishenfenzheng);
                mSelectPath = null;

            }
        }
    }

    private void UploadImage(ImageView iv, String type) {
        Log.i("tag--�ļ�ת16���ƿ�ʼ", new Date() + "");
        String ur = UploadFile.sendPhoto(iv);
        Log.i("tag--�ļ�ת16���ƽ���", new Date() + "");

        String tmps = "ImgUpload?imgStream=" + ur + "&strGuid=" + uuid + "&imgFormat=jpg&imgType=" + type + "&thisusername=" + SharedParam.getParam(getApplicationContext(), "username", "�û�������");
        Log.i("tag--���ܿ�ʼ", new Date() + "");
        String s = DES.getDes(tmps);
        Log.i("tag---DES.getDes", s);
        Log.i("tag--���ܽ���", new Date() + "");
//        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        MyRequest.requestPost(mQueue, Url, s, myHandler);
    }
}
