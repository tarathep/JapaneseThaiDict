package com.tarathep.bokie.japanesethaidict;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bokee on 4/6/2018.
 */

public class GrammarListAdapter extends RecyclerView.Adapter<GrammarListAdapter.MyViewHolder> {

    Context context;
    List<GrammarList> grammarLists;
    String search;

    public GrammarListAdapter(Context context, String search, List<GrammarList> grammarLists){
        this.context =context;
        this.search = search;
        this.grammarLists = grammarLists;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView w,w2,wd;
        public RelativeLayout layout;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            w = (TextView) view.findViewById(R.id.w);
            w2 = (TextView) view.findViewById(R.id.w2);
            wd = (TextView) view.findViewById(R.id.wd);
            layout = (RelativeLayout) view.findViewById(R.id.layout);
            cardView = (CardView) view.findViewById(R.id.card);
        }

    }
    @Override
    public GrammarListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_list_row, parent, false);
        return new GrammarListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final GrammarListAdapter.MyViewHolder holder, final int position) {

        final String h = search;

        final String W = grammarLists.get(position).getGrammar();
        final String WD = grammarLists.get(position).getMean();
        try {
            int start = W.indexOf(h);
            int end = start + h.length();
            SpannableString word = new SpannableString(W);
            word.setSpan(new StrikethroughSpan(),start,end,0);
            holder.w.setText(word);
            //holder.w2.setPaintFlags(holder.w2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.w.setMovementMethod(new LinkMovementMethod());

        }catch (IndexOutOfBoundsException e){
            holder.w.setText(W);
        }

        try {
            int start2 = WD.indexOf(h);
            int end2 = start2 + h.length();
            SpannableString word2 = new SpannableString(WD);
            word2.setSpan(new CallToast(),start2,end2,0);
            holder.wd.setText(word2);
            holder.wd.setMovementMethod(new LinkMovementMethod());
        }catch (IndexOutOfBoundsException e){
            holder.wd.setText(WD);
        }

    }

    @Override
    public int getItemCount() {
        return grammarLists.size();
    }
}
