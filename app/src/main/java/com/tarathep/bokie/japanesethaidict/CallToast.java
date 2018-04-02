package com.tarathep.bokie.japanesethaidict;

import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

/**
 * Created by bokee on 3/21/2018.
 */

public class CallToast extends ClickableSpan {
    @Override
    public void onClick(View widget) {
        //Toast.makeText(widget.getContext().getApplicationContext(), "Yeah!", Toast.LENGTH_SHORT).show();
    }
}
