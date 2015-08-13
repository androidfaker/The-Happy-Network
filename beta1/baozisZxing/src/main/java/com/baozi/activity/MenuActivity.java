package com.baozi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.example.baoziszxing.R;


public class MenuActivity extends ActionBarActivity implements View.OnClickListener {

    Button dingdan;
    Button denglu;
    Button zhuce;
    Button zhucegeren;
    Button npcshangjia;
    Button npcchanpin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        dingdan = (Button) findViewById(R.id.dingdan);
        dingdan.setOnClickListener(this);
        denglu = (Button) findViewById(R.id.denglu);
        denglu.setOnClickListener(this);
        zhuce = (Button) findViewById(R.id.register);
        zhuce.setOnClickListener(this);
        zhucegeren = (Button) findViewById(R.id.npcperson);
        zhucegeren.setOnClickListener(this);
        npcshangjia = (Button) findViewById(R.id.npcbusiness);
        npcshangjia.setOnClickListener(this);
        npcchanpin = (Button) findViewById(R.id.npcproduct);
        npcchanpin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dingdan:
                startActivity(new Intent(getApplicationContext(), OrderActivity.class));
                break;
            case R.id.denglu:
                Intent denglu = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(denglu);
                break;
            case R.id.register:
                Intent dingdan = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(dingdan);
                break;
            case R.id.npcperson:
                Intent zhuce = new Intent(getApplicationContext(), NpcPersonRegisterActivity.class);
                startActivity(zhuce);
                break;
            case R.id.npcbusiness:
                Intent npczhuce = new Intent(getApplicationContext(), NpcBusinessRegisterActivity.class);
                startActivity(npczhuce);
                break;
            case R.id.npcproduct:
                startActivity(new Intent(getApplicationContext(), NpcProductRegisterActivity.class));
                break;
        }
    }
}
