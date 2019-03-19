package com.tarathep.bokie.japanesethaidict;

import android.content.Context;
import android.support.v4.widget.TextViewCompat;
import android.widget.TextView;

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {


    public CustomTextView(Context context,String text) {
        super(context);
        setText(text);
        setTextSize(35);
        //setId(0);

    }
}
