package com.zhen.firstapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RateListActivity extends ListActivity implements  Runnable{

    String data[]={"wait..."};
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_rate_list);

        final List<String> list1= new ArrayList<String>();
        for(int i = 1;i<100;i++){
            list1.add("item"+i);

        }

        ListAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        setListAdapter(adapter);

        Thread t =  new Thread(this);
        t.start();

        handler = new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);

                if(msg.what ==7){
                   List<String> list2 =  (List<String>) msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(RateListActivity.this,android.R.layout.simple_list_item_1,list2);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
    }

    public void run(){
        //获取网络数据,放入list带回到主线程中
        Log.i("thread","run......");
        List<String>  retList = new ArrayList<String>();

        Document doc = null;
        try{

            Thread.sleep(3000);
            doc = Jsoup.connect("https://www.usd-cny.com/bankofchina.htm").get();

            Log.i("td","run:"+doc.title());

            Elements tables =doc.getElementsByTag("table");
//                for(Element table :tables){
//                    Log.i(TAG,"run:table=" + table);
//                }
            Element table1 = tables.get(0);
            Log.i("td","run:table1="+table1);
            Elements tds = table1.getElementsByTag("td");
            for (int i =0;i<tds.size();i+=6){
                Element td1=tds.get(i);
                Element td2=tds.get(i+5);
                String str1 = td1.text();
                String val = td2.text();

                Log.i("td","run:"+str1+"==>"+val);
                retList.add(str1 + "==>"+val);

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        Message msg = handler.obtainMessage(7);
        //msg.what = 5;
        // msg.obj = "Hello from run()";
        msg.obj=retList;
        handler.sendMessage(msg);
    }
}
