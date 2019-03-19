package com.tarathep.bokie.japanesethaidict;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

public class TextHighlight extends CharacterStyle  implements UpdateAppearance {
    private Context context;

    TextHighlight(Context context){
       this.context = context;
    }
    @Override
    public void updateDrawState(TextPaint tp) {
        tp.setColor(context.getResources().getColor(R.color.colorYellow));
    }
}
