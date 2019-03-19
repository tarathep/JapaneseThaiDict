package com.tarathep.bokie.japanesethaidict;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    TextView txt_jp,txt_kana,txt_th,txt_type,txt_romanji;
    Button btn_favorite,btn_speak,btn_copy,btn_bushu;
    EditText editText_furi,editText_jp,editText_th,editText_romanji;
    String[] W;


    private boolean state_copy = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        W = getIntent().getStringArrayExtra("message_key");
        //getSupportActionBar().setTitle("JapaneseThai Dict");


        txt_jp = (TextView) findViewById(R.id.txt_jp);
        txt_kana = (TextView) findViewById(R.id.txt_kana);
        txt_th = (TextView) findViewById(R.id.txt_th);
        txt_type = (TextView) findViewById(R.id.txt_type);
        txt_romanji = (TextView) findViewById(R.id.txt_romanji);

        btn_favorite = (Button) findViewById(R.id.button_favorite);
        btn_speak = (Button) findViewById(R.id.button_speak);
        btn_copy = (Button) findViewById(R.id.button_copy);
        btn_bushu = (Button) findViewById(R.id.button_bushu);


        editText_furi = (EditText) findViewById(R.id.editText_furi);
        editText_jp = (EditText) findViewById(R.id.editText_jp);
        editText_th = (EditText) findViewById(R.id.editText_th);
        editText_romanji = (EditText) findViewById(R.id.editText_romanji);

        editText_furi.setVisibility(View.GONE);
        editText_jp.setVisibility(View.GONE);
        editText_romanji.setVisibility(View.GONE);
        editText_th.setVisibility(View.GONE);

        //set history into word list
       // new DBManage(getApplicationContext()).clearHistory();


        new DBManage(getApplicationContext()).updateHistory(W[0]);

        //Toast.makeText(this,new DBManage(getApplicationContext()).getLastHistory()+"",Toast.LENGTH_SHORT).show();
        //Toast.makeText(this,"Clear!",Toast.LENGTH_SHORT).show();
        active();


        btn_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new DBManage(getApplicationContext()).queryPrimaryKey (W[0]).getFavorite() == null){
                    new DBManage(getApplicationContext()).Like(W[0]);
                }else {
                    new DBManage(getApplicationContext()).UnLike(W[0]);
                }
                active();
            }
        });

        txt_jp.setText(W[1]);

        if(furi(W[1])) txt_kana.setText(W[2]);
        else txt_kana.setText("");
        txt_th.setText(MutiLineSymbol(W[3]));
        txt_type.setText(W[4]);
        txt_romanji.setText(W[5]);

        btn_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state_copy){
                    state_copy = false;
                    if(furi(W[1])) editText_furi.setVisibility(View.VISIBLE);
                    editText_jp.setVisibility(View.VISIBLE);
                    editText_romanji.setVisibility(View.VISIBLE);
                    editText_th.setVisibility(View.VISIBLE);

                    editText_furi.setText(W[2]);
                    editText_jp.setText(W[1]);
                    editText_romanji.setText(W[5]);
                    editText_th.setText(MutiLineSymbol(W[3]));
                    btn_copy.setBackgroundTintList(DetailActivity.this.getResources().getColorStateList(R.color.colorBlue));
                }else {
                    state_copy = true;
                    editText_furi.setVisibility(View.GONE);
                    editText_jp.setVisibility(View.GONE);
                    editText_romanji.setVisibility(View.GONE);
                    editText_th.setVisibility(View.GONE);
                    btn_copy.setBackgroundTintList(DetailActivity.this.getResources().getColorStateList(R.color.colorBlue2));
                }


            }
        });

        final Handler handler = new Handler();
        btn_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TTS(DetailActivity.this).setLocale(Locale.JAPAN).speak(W[1]);

                btn_speak.setBackgroundTintList(DetailActivity.this.getResources().getColorStateList(R.color.colorGreen));
                txt_jp.setTextColor(DetailActivity.this.getResources().getColor(R.color.colorGreen));
               handler.postDelayed(new Runnable() {
                   @Override
                   public void run() {

                       if(new DBManage(getApplicationContext()).queryPrimaryKey(W[0]).getFavorite().equals("1")){
                           txt_jp.setTextColor(DetailActivity.this.getResources().getColor(R.color.colorRed));
                       }else {
                           txt_jp.setTextColor(DetailActivity.this.getResources().getColor(R.color.text));
                       }
                       btn_speak.setBackgroundTintList(DetailActivity.this.getResources().getColorStateList(R.color.colorGreen2));

                   }
               },1000);
            }
        });


        btn_bushu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),BushuActivity.class);
                intent.putExtra("msg_key",W);
                DetailActivity.this.startActivity(intent);
            }
        });
    }


    private void active(){
        if(new DBManage(getApplicationContext()).queryPrimaryKey (W[0]).getFavorite() != null){
            btn_favorite.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorRed));
            txt_jp.setTextColor(this.getResources().getColor(R.color.colorRed));
        }else {
            btn_favorite.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorRed2));
            txt_jp.setTextColor(this.getResources().getColor(R.color.text));
        }

    }

    private String MutiLineSymbol(String input){
        String output="";
        String[] tmp = input.split(",");
        for(int i=0;i<tmp.length;i++){
            output += tmp[i]+"\n";
        }
        return output;
    }
    private boolean furi(String input){
        for(int i=0;i<input.length();i++){
            if(new CheckLanguage().isJP_KANJI(input.charAt(i)))
                return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
