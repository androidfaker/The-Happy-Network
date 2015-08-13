package com.baozi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.baoziszxing.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ErWeiMaResultAcitivty extends Activity {
    private TextView textView_result;
    private String sendrequesturl = "http://192.168.1.250:8002/Services/NPCProductService.asmx/getProductjson?";
    private final int UPDATE_UI = 1;
    private String Uid;
    private String Code;
    private String jsonresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_result);
        textView_result = (TextView) findViewById(R.id.text_showresult);
        Intent intent = getIntent();

        Uid = intent.getStringExtra("result");
        Log.i("UID = ", Uid);
        Code = intent.getStringExtra("result");

        NameValuePair pair1 = new BasicNameValuePair("uid", null);
        NameValuePair pair2 = new BasicNameValuePair("Code", Code);
        NameValuePair pair3 = new BasicNameValuePair("thisusername",null);

        final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
        pairList.add(pair1);
        pairList.add(pair2);
        pairList.add(pair3);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("HttpPost", "POST request");
                // 先获取用户名和年龄
                try
                {
                    HttpEntity requestHttpEntity = new UrlEncodedFormEntity(
                            pairList);
                    // URL使用基本URL即可，其中不需要加参数
                    HttpPost httpPost = new HttpPost(sendrequesturl);
                    // 将请求体内容加入请求中
                    httpPost.setEntity(requestHttpEntity);
                    // 需要客户端对象来发送请求
                    HttpClient httpClient = new DefaultHttpClient();
                    // 发送请求
                    HttpResponse response = httpClient.execute(httpPost);
                    // 显示响应
                    showResponseResult(response);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }

        }).start();


    }
    /**
     * 显示响应结果到命令行和TextView
     * @param response
     */
    String result;
    private void showResponseResult(final HttpResponse response)
    {
        if (null == response)
        {
            return;
        }

        HttpEntity httpEntity = response.getEntity();
        try
        {
            InputStream inputStream = httpEntity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream));
            result = "";
            String line = "";
            while (null != (line = reader.readLine()))
            {
                result += line;

            }
            Log.i("result = ", result);
            System.out.println(result);
            try {
                JSONTokener jsondata = new JSONTokener(result);
                JSONObject jsobj = (JSONObject) jsondata.nextValue();
                String state = jsobj.optString("state");
                if ("Failed".equals(state)) {
                    Log.i("State",state);
                    textView_result.setText("该产品尚未录入国家防伪代码中心库，请谨慎...");
                } else {
                    JSONArray jsarray = jsobj.optJSONArray("data");
                    for (int a = 0; a < jsobj.length(); a++) {
                        String name = "产品名称 : " + ((JSONObject) jsarray.get(a)).getString("productName");
                        String compname = "生产厂家 : " + ((JSONObject) jsarray.get(a)).getString("CompName");
                        String buildDate = "生产日期 : " + ((JSONObject) jsarray.get(a)).getString("buildDate");
                        String producState = "产品状态 : " + ((JSONObject) jsarray.get(a)).getString("producState");
                        String createBy = "信息管理者 : " + ((JSONObject) jsarray.get(a)).getString("createBy");
                        jsonresult = name + "\n" + compname + "\n" + buildDate + "\n" + producState + "\n" + createBy;
                        Log.i("JSON", jsonresult);
                        textView_result.setText("北京鑫通运科信息技术有限公司：\n" + jsonresult);
                        }
//                    textView_result.setText("北京鑫通运科信息技术有限公司：\n" + jsonresult);
                }

                }catch(JSONException e){
                    e.printStackTrace();
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}

