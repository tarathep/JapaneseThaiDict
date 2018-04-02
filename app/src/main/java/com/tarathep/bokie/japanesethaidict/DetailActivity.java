package com.tarathep.bokie.japanesethaidict;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    Button btn_favorite,btn_speak;
    String[] W;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        W = getIntent().getStringArrayExtra("message_key");
        getSupportActionBar().setTitle("JapaneseThai Dict");

        TextView txt_jp,txt_kana,txt_th,txt_type,txt_romanji;

        txt_jp = (TextView) findViewById(R.id.txt_jp);
        txt_kana = (TextView) findViewById(R.id.txt_kana);
        txt_th = (TextView) findViewById(R.id.txt_th);
        txt_type = (TextView) findViewById(R.id.txt_type);
        txt_romanji = (TextView) findViewById(R.id.txt_romanji);

        btn_favorite = (Button) findViewById(R.id.button_favorite);


        active();
        btn_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new DBManage(getApplicationContext()).queryPrimaryKey(W[0]).getMark().equals("1")){
                    new DBManage(getApplicationContext()).updateMask(W[0],"0");
                }else {
                    new DBManage(getApplicationContext()).updateMask(W[0],"1");
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

    }

    private void active(){
        if(new DBManage(getApplicationContext()).queryPrimaryKey(W[0]).getMark().equals("1")){
            btn_favorite.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorYellow));
        }else {
            btn_favorite.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorGray));
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
}
