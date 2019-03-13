package com.example.lenovo.a0errordictionary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class shoucangActivity extends Activity {
    ListView mylV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoucang);
        mylV = (ListView)findViewById(R.id.lV);
        BaseAdapter ap = new BaseAdapter() {
            @Override
            public int getCount() {
                return getFilenum();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @SuppressLint("WrongConstant")
            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                LinearLayout line = new LinearLayout(shoucangActivity.this);
                line.setOrientation(0);
                ImageView image = new ImageView(shoucangActivity.this);
                image.setImageResource(R.drawable.ic_menu_send);
                TextView text = new TextView(shoucangActivity.this);
                text.setText(getFileString(i+1));//这里放文件读出的内容  函数，传入参数i  getFileString(inputstream,i);
                text.setTextSize(20);
                text.setTextColor(Color.BLUE);
                line.addView(image);
                line.addView(text);
                return line;
            }
        };
        mylV.setAdapter(ap);
        mylV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //第i项被单击 穿此参数到main2 进行查找显示
                Intent intent = new Intent();
                intent.setClass(shoucangActivity.this,Main2Activity.class);
                String str = String.valueOf(i);
                intent.putExtra("str",str);
                startActivity(intent);
            }
        });
    }
    public String getFileString(int i) {
        try{
            InputStreamReader inputStreamReader = null;
            FileInputStream fis = openFileInput("shoucang.txt");
            int j = 0;
            try {
                inputStreamReader = new InputStreamReader(fis, "utf-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        if(inputStreamReader != null){
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = null;
            boolean flag = false;
            StringBuffer sb = new StringBuffer("");
            try {
                while ((line = reader.readLine()) != null) {//按行读取放入line中
                        if(line.contains("11")){
                            j++;
                        }
                        if(i == j){
                            sb.append(line);
                            sb.append("\n");
                            flag = true;
                            break;
                        }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return flag?sb.toString():null;
        }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "文件流错误";
    }
    public int getFilenum() {
        int j = 0;
        try{
            InputStreamReader inputStreamReader = null;
            FileInputStream fis = openFileInput("shoucang.txt");
            try {
                inputStreamReader = new InputStreamReader(fis, "utf-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        if(inputStreamReader != null){
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = null;
            boolean flag = false;
            try {
                j = 0;
                while ((line = reader.readLine()) != null) {//按行读取放入line中
                    if(line.contains("11")){
                        j++;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }catch (Exception e){
        e.printStackTrace();
    }
        return j;
    }
}
