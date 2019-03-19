package com.tarathep.bokie.japanesethaidict;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    Button btnAns1,btnAns2,btnAns3,btnAns4;
    TextView textQ;
    WordList word1,word2,word3,word4;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // init
        btnAns1 = (Button) findViewById(R.id.btn_ans1);
        btnAns2 = (Button) findViewById(R.id.btn_ans2);
        btnAns3 = (Button) findViewById(R.id.btn_ans3);
        btnAns4 = (Button) findViewById(R.id.btn_ans4);

        textQ = (TextView) findViewById(R.id.textViewQ);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Reset();
                /*
                Snackbar.make(view,  "Reset", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
    }

    private void Reset(){
        List<WordList> listGame = new DBManage(GameActivity.this).getWordListGame();

        String id;
        int r = new Random().nextInt(4);
        switch (r){
            case 0:
                word1 = listGame.get(0);
                word2 = listGame.get(1);
                word3 = listGame.get(2);
                word4 = listGame.get(3);
                id = word1.getId();
                break;
            case 1:
                word1 = listGame.get(1);
                word2 = listGame.get(0);
                word3 = listGame.get(2);
                word4 = listGame.get(3);
                id = word2.getId();
                break;
            case 2:
                word1 = listGame.get(2);
                word2 = listGame.get(1);
                word3 = listGame.get(0);
                word4 = listGame.get(3);
                id = word3.getId();
                break;
            case 3:
                word1 = listGame.get(3);
                word2 = listGame.get(1);
                word3 = listGame.get(2);
                word4 = listGame.get(0);
                id = word4.getId();
                break;
        }


        btnAns1.setText(word1.getTh().split(",")[0]);
        btnAns2.setText(word2.getTh().split(",")[0]);
        btnAns3.setText(word3.getTh().split(",")[0]);
        btnAns4.setText(word4.getTh().split(",")[0]);


        textQ.setText(word1.getJp());
        this.id = word1.getId();
    }


}
