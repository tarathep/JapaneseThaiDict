package com.tarathep.bokie.japanesethaidict;

import android.content.Context;
import android.widget.Button;

public class CustomButton extends android.support.v7.widget.AppCompatButton {
    public CustomButton(Context context,String text) {
        super(context);
        setText(text);
        setTextSize(25);

    }



}
