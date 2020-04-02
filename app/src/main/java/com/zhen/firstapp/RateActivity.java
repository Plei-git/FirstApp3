package com.zhen.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RateActivity extends AppCompatActivity {


    private final String TAG = "Rate";
    private float dollarRate = 0.1f;
    private float euroRate = 0.2f;
    private float wonRate = 0.3f;
    EditText rmb;
    TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        rmb = (EditText)findViewById(R.id.rmb);
        show = (TextView)findViewById(R.id.showOut);
    }

    public void onClick(View btn){
        //获取用户输入内容
        Log.i(TAG,"onClick:");
        String str = rmb.getText().toString();
        Log.i(TAG,"onClick:get str="+str);

        float r =0;
        if(str.length()>0){
            r = Float.parseFloat(str);

        }
        else{
            //提示用户输入内容
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
        }

        if(btn.getId()==R.id.btn_dollar){
            show.setText(String.format("%.2f",r*dollarRate));
        }

        else if(btn.getId()==R.id.btn_euro){
            show.setText(String.format("%.2f",r*euroRate));

        }
        else {
            show.setText(String.format("%.2f",r*wonRate));
        }

    }
    public void openOne(View btn) {
        //打开一个页面Activity

        openConfig();
    }

    private void openConfig() {
        Intent config = new Intent(this, ConfigActivity.class);

        config.putExtra("dollar_rate_key", dollarRate);
        config.putExtra("euro_rate_key", euroRate);
        config.putExtra("won_rate_key", wonRate);


        Log.i(TAG, "openOne:dollar_rate_key=" + dollarRate);
        Log.i(TAG, "openOne:euro_rate_key=" + euroRate);
        Log.i(TAG, "openOne:won_rate_key=" + wonRate);


        startActivityForResult(config, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rate,menu);
    return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_set){
            openConfig();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode , int resultCode , Intent data){
         //requestCode 区分是谁返回的数据；resultCode区分返回的数据通过什么格式去区分
         if(requestCode ==1 && resultCode == 2){
             /*
             bdl.putFloat("key_dollar",newDollar);
        bdl.putFloat("key_euro",newEuro);
        bdl.putFloat("key_won",newWon);
              */
             Bundle bundle = data.getExtras();
             dollarRate = bundle.getFloat("key_dollar",0.1f);
             euroRate = bundle.getFloat("key_euro",0.1f);
             wonRate = bundle.getFloat("key_won",0.1f);

             Log.i(TAG,"onActivityResult:dollarRate=" +dollarRate);
             Log.i(TAG,"onActivityResult:euroRate=" +euroRate);
             Log.i(TAG,"onActivityResult:wonRate=" +wonRate);


         }
         super.onActivityResult(requestCode,resultCode,data);
        }


}
