package com.tarathep.bokie.japanesethaidict;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BushuActivity extends AppCompatActivity {

    String[] W;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bushu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //sender data from detail
        W = getIntent().getStringArrayExtra("msg_key");

        String output="";
        LinearLayout layout = (LinearLayout) findViewById(R.id.lin_lay);
        for(int i=0;i<W[1].length();i++){
            //W[1].charAt(i);
            //layout.addView(new CustomButton(this, W[1].charAt(i)+""),150,150);


            String setText = "";
            setText = W[1].charAt(i)+"";
            for(int j=0;j<i+2;j++){
                setText+="\n.";
            }


            layout.addView(new CustomTextView(this, setText+""),120,LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        //new DBManage(this).getSingleWord( W[1].charAt(i)+"").getTh()

        Button myButton = new Button(this);
        myButton.setText("Push Me");
/*
         ConstraintLayout ll = (ConstraintLayout) findViewById(R.id.con_layout);
       // ConstraintLayout.LayoutParams lp =;
        ll.addView(new Button(this),  new ConstraintLayout.LayoutParams(100,100));
        ll.addView(new Button(this),  new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.LEFT, ConstraintLayout.LayoutParams.WRAP_CONTENT));

  */




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, W[1], Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
