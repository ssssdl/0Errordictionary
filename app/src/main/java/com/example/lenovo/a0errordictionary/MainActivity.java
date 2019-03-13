package com.example.lenovo.a0errordictionary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //搜索功能控件定义
    private EditText eT1;
    private TextView tV1;
    private InputStream inputstream;//定义获取字典文件对象
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //搜索功能控件连接
        eT1=(EditText)findViewById(R.id.editText1);
        tV1=(TextView)findViewById(R.id.textView1);
        //其他框架控件
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //框架
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //写入字典文件
                String ss = tV1.getText().toString();
                if(ss != ""){
                    if(ss != "未查找到此错误"&&ss != "文件流错误") {
                        //写文件
                        if(write(ss))//写入收藏文件
                            Snackbar.make(view, "收藏成功", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        else
                            Snackbar.make(view, "收藏失败", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else
                        Snackbar.make(view, "收藏失败", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                }
                else
                    Snackbar.make(view, "收藏失败", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        eT1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {//可以添加按钮，点击按钮开始搜索   这里其实可以直接用搜索框SearchView  刚发现  有空再改
                if(i== EditorInfo.IME_ACTION_SEARCH||(keyEvent!=null&&keyEvent.getKeyCode()== KeyEvent.KEYCODE_ENTER)){//EditorInfo.IME_ACTION_SEARCH、EditorInfo.IME_ACTION_SEND等分别对应EditText的imeOptions属性
                    //TODO回车键按下时要执行的操作
                    String Edm=eT1.getText().toString();//获取文本框输入的内容
                    String str1 = Edm.replaceAll("[^A-Z]", "");
                    String str2 = Edm.replaceAll("[^0-9]", "");
                    inputstream = getResources().openRawResource(R.raw.d8016d9999);
                    if(str1.compareTo("D") == 0)
                        inputstream = getResources().openRawResource(R.raw.d8016d9999);//选择字典文件
                    else if(str1.compareTo("M") == 0)
                        inputstream = getResources().openRawResource(R.raw.m6101m6025);//选择字典文件
                    else if(str1.compareTo("PG") == 0)
                        inputstream = getResources().openRawResource(R.raw.pg0165pg1087);//选择字典文件
                    else if(str1.compareTo("R") == 0)
                        inputstream = getResources().openRawResource(R.raw.r6002r6035);//选择字典文件
                    else if(str1.compareTo("C") == 0){
                        int x=Integer.parseInt(str2);
                        if(x>998&&x<1906)
                            inputstream = getResources().openRawResource(R.raw.c999c1905);//选择字典文件
                        else if(x>3999&&x<6001)
                            inputstream = getResources().openRawResource(R.raw.c4000c6000);//选择字典文件
                        else if(x>2499&&x<3924)
                            inputstream = getResources().openRawResource(R.raw.c2500c3923);//选择字典文件
                        else if(x>1999&&x<2500)
                            inputstream = getResources().openRawResource(R.raw.c2000c2499);//选择字典文件
                    }
                    Edm = getFileString(inputstream,Edm);//这一行执行查找函数
                    tV1.setText(Edm);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //收藏夹
            Intent intent = new Intent(MainActivity.this,shoucangActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            //搜索 有机会的话把id改一下
        } else if (id == R.id.nav_gallery) {
            //收藏夹
            Intent intent = new Intent(MainActivity.this,shoucangActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String getFileString(InputStream inputStream,String errorName) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
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
                if(line.contains(errorName)){
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag?sb.toString():"未查找到此错误";
    }
        return "文件流错误";
    }
    boolean write(String s){
        try{
            FileOutputStream fos = openFileOutput("shoucang.txt",MODE_APPEND);
            PrintStream ps = new PrintStream(fos);
            ps.println(s);
            ps.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }
}
