package com.baozi.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by User on 2015/7/30 030.
 */
public class MyRequest {

    public static void requestPost(RequestQueue mQueue, String Url, final String s, final Handler handler) {
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("tag-response", response);
//                        res = response;
                        Message msg = handler.obtainMessage();
                        msg.what = Constants.REQUEST_OK;
                        msg.obj = response;
                        handler.sendMessage(msg);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("tag-error.getMessage()", error.getMessage(), error);
                Message msg = handler.obtainMessage();
                msg.what = Constants.REQUEST_SORRY;
                handler.sendMessage(msg);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //������������Ҫpost�Ĳ���
                Map<String, String> params = new HashMap<>();
                params.put("str", s);
                return params;
            }
        };
        mQueue.add(stringRequest);
    }
}