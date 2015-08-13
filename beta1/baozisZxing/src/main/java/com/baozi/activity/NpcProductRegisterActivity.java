package com.baozi.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baozi.adapter.MyAdapter;
import com.baozi.utils.Constants;
import com.baozi.utils.Encrypt.DES;
import com.baozi.utils.MyRequest;
import com.baozi.utils.NpcProductPopupWindow;
import com.baozi.utils.SharedParam;
import com.example.baoziszxing.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class NpcProductRegisterActivity extends Activity {
    private String Url = Constants.NPC_PRODUCT_URL;
    private RequestQueue mQueue;
    private StringBuffer json;

    private EditText shengchanchangjia;
    private EditText shengchanxukezhenghao;
    private EditText chanpinmingcheng;
    private EditText baozhiqi;
    private ImageView add;
    private ListView listView;
    private List<Map<String, String>> mData;
    private Map<String, String> map;
    private MyAdapter adapter;
    private Button zhuce;

    private MyHandler myHandler;

    private int i;//����ͨ�ű�־λ���û���Ϣ+����ͼƬ��
    private int flag;//ʧ�ܴ�����־λ
    private UUID uuid;
    private ProgressDialog progressDialog = null;

    //�Զ���ĵ�������  
    private NpcProductPopupWindow menuWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_npc_product);

        mQueue = Volley.newRequestQueue(getApplicationContext());

        init();

        adapter = new MyAdapter(this, mData, onClickListener);
        listView.setAdapter(adapter);
    }

    private void init() {
        uuid = UUID.randomUUID();
        myHandler = new MyHandler();
        i = 0;
        flag = 1;
        //ʵ����SelectPicPopupWindow  
        menuWindow = new NpcProductPopupWindow(NpcProductRegisterActivity.this, onClickListener);

        mData = new ArrayList<>();

        add = (ImageView) findViewById(R.id.add);
        add.setOnClickListener(onClickListener);
        listView = (ListView) findViewById(R.id.listview);

        shengchanchangjia = (EditText) findViewById(R.id.shengchanchangjia);
        shengchanxukezhenghao = (EditText) findViewById(R.id.shengchanxukezhenghao);
        chanpinmingcheng = (EditText) findViewById(R.id.chanpinmingcheng);
        baozhiqi = (EditText) findViewById(R.id.baozhiqi);
        zhuce = (Button) findViewById(R.id.zhuce);
        zhuce.setOnClickListener(onClickListener);
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
                                    String tmps1 = "npcproductclassapplyInsert?FirmID=" + SharedParam.getParam(getApplicationContext(), "id", "0") +//��Ҫ�޸�
                                            "&BusinessClassID=" + "123456" +//��Ҫ�޸�
                                            "&BusinessClass2ID=" + "123456" +//��Ҫ�޸�
                                            "&productClassName=" + chanpinmingcheng.getText().toString() +
                                            "&CompName=" + shengchanchangjia +
                                            "&productLifeCycle=" + baozhiqi +
                                            "&productionLicense=" + shengchanxukezhenghao +
                                            "&GUID=" + uuid +
                                            "&ApplyGUID=" + uuid +
                                            "&thisuserName=" + SharedParam.getParam(getApplicationContext(), "username", "�û�������");
                                    Log.i("tag--tmps", tmps1);
                                    String s1 = DES.getDes(tmps1);
                                    Log.i("tag--DES.getDes", s1);
                                    MyRequest.requestPost(mQueue, Url, s1, myHandler);
                                    break;
                                case 2:
                                    if (mData.size() > 0) {
                                        String tmps2 = "npcproductclassapplyextend?GUID=" + uuid +
                                                "&extendstr=" + "{\"extendstr\":[" + json + "]}" +
                                                "&thisusername=" + SharedParam.getParam(getApplicationContext(), "username", "�û�������");
                                        Log.i("tag--tmps", tmps2);
                                        String s2 = DES.getDes(tmps2);
                                        Log.i("tag--DES.getDes", s2);
                                        MyRequest.requestPost(mQueue, Url, s2, myHandler);
                                    }
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
                                        onClickListener.onClick(zhuce);
                                        break;
                                    case 2:
                                        String tmps2 = "npcproductclassapplyextend?GUID=" + uuid +
                                                "&extendstr=" + "{\"extendstr\":[" + json + "]}" +
                                                "&thisusername=" + SharedParam.getParam(getApplicationContext(), "username", "�û�������");
                                        Log.i("tag--tmps", tmps2);
                                        String s2 = DES.getDes(tmps2);
                                        Log.i("tag--DES.getDes", s2);
                                        MyRequest.requestPost(mQueue, Url, s2, myHandler);
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

    //���� 
    public void fanhui(View view) {
        finish();
    }

    //Ϊ��������ʵ�ּ�����  
    private OnClickListener onClickListener = new OnClickListener() {

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.add:
                    //��ʾ���� 
                    menuWindow.title.setText("");
                    menuWindow.info.setText("");
                    menuWindow.title.setTag(mData.size());
                    menuWindow.showAtLocation(NpcProductRegisterActivity.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //����layout��PopupWindow����ʾ��λ��
                    break;
                case R.id.positive://ȷ��
                    Log.i("tag--", (int) menuWindow.title.getTag() + "," + mData.size());
                    if (menuWindow.title.getText().toString().length() > 0) {//|| menuWindow.info.getText().toString().length() > 0
                        if ((int) menuWindow.title.getTag() == mData.size()) {//���
                            map = new HashMap<>();
                            map.put("title", menuWindow.title.getText().toString());
                            map.put("info", menuWindow.info.getText().toString());
                            mData.add(map);
                        } else {//�޸�
                            mData.get((int) menuWindow.title.getTag()).put("title", menuWindow.title.getText().toString());
                            mData.get((int) menuWindow.title.getTag()).put("info", menuWindow.info.getText().toString());
                        }
                    }
                    menuWindow.dismiss();
                    adapter.notifyDataSetChanged();
                    dataToString();
                    Log.i("tag--", (int) menuWindow.title.getTag() + json.toString() + "");
                    break;
                case R.id.edit_product://�༭
                    menuWindow.title.setText(mData.get((int) v.getTag()).get("title"));
                    menuWindow.info.setText(mData.get((int) v.getTag()).get("info"));
                    menuWindow.title.setTag(v.getTag());
                    //��ʾ����  
                    menuWindow.showAtLocation(NpcProductRegisterActivity.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //����layout��PopupWindow����ʾ��λ��
                    break;
                case R.id.delete_product:
                    mData.remove((int) v.getTag());
                    adapter.notifyDataSetChanged();
                    break;
                case R.id.zhuce:
                    if (flag == 1) {
                        progressDialog = ProgressDialog.show(NpcProductRegisterActivity.this, "���Ե�", "�ύ������...", true);
                    }

                    Message msg = myHandler.obtainMessage();
                    msg.what = Constants.REQUEST_OK;
                    msg.obj = "{state:Success}";
                    myHandler.sendMessage(msg);
                    break;
                default:
                    break;
            }


        }

    };

    private void dataToString() {
        json = new StringBuffer();
        for (int i = 0; i < mData.size(); i++) {
            json.append("{\"Name\":\"" + mData.get(i).get("title") +
                    "\",\"Value\":\"" + mData.get(i).get("info") + "\",\"IsDefalult\":\"" +
                    (mData.get(i).get("info").length() > 0 ? "1" : "0") +
                    "\",\"ViewIndex\":\"1\"}");
            json.append(i == mData.size() - 1 ? "" : ",");
        }
    }
}
