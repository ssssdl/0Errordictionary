package com.example.lenovo.a0errordictionary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class Main2Activity extends AppCompatActivity {
    TextView tV2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//这里获取shoucang传来的内容 并且查找 显示查找结果
        tV2 = (TextView)findViewById(R.id.textView2);
        Intent intent = this.getIntent();
        String str = intent.getStringExtra("str");
        int i = Integer.valueOf(str);
        tV2.setText(getFileString(i+1));
    }
    public String getFileString(int i){
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
                            if(i == j){
                                sb.append(line);
                                sb.append("\n");
                                line = reader.readLine();
                                while (!line.contains("11")){
                                    sb.append(line);
                                    sb.append("\n");
                                    line = reader.readLine();
                                    if(line == null)
                                        break;
                                }
                                flag = true;
                                break;
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return flag?sb.toString():"未查找到此错误";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "文件流错误";
    }
}