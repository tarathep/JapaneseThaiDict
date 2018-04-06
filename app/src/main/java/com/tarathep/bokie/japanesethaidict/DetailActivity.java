package com.tarathep.bokie.japanesethaidict;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView txt_jp,txt_kana,txt_th,txt_type,txt_romanji;
    Button btn_favorite,btn_speak,btn_copy;
    EditText editText_furi,editText_jp,editText_th,editText_romanji;
    String[] W;

    boolean state_copy = true;
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

        editText_furi = (EditText) findViewById(R.id.editText_furi);
        editText_jp = (EditText) findViewById(R.id.editText_jp);
        editText_th = (EditText) findViewById(R.id.editText_th);
        editText_romanji = (EditText) findViewById(R.id.editText_romanji);

        editText_furi.setVisibility(View.GONE);
        editText_jp.setVisibility(View.GONE);
        editText_romanji.setVisibility(View.GONE);
        editText_th.setVisibility(View.GONE);

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

    }

    private void active(){
        if(new DBManage(getApplicationContext()).queryPrimaryKey(W[0]).getMark().equals("1")){
            btn_favorite.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorRed));
            //btn_favorite.setText("");
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
}
