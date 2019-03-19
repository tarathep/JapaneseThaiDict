package com.tarathep.bokie.japanesethaidict;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import javax.crypto.Cipher;

/**
 * Created by bokee on 11/4/2560.
 */

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.MyViewHolder> {

    Context context;
    List<WordList> wordLists;
    String search;

    public WordListAdapter(Context context,String search,List<WordList> wordList){
        this.context =context;
        this.search = search;
        this.wordLists = wordList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView w,wd;
        public RelativeLayout layout;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            w = (TextView) view.findViewById(R.id.w);
            wd = (TextView) view.findViewById(R.id.wd);
            layout = (RelativeLayout) view.findViewById(R.id.layout);
            cardView = (CardView) view.findViewById(R.id.card);
        }

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final String[] w_col = new String[7];
        w_col[0] = wordLists.get(position).getId();
        w_col[1] = wordLists.get(position).getJp();w_col[2] = wordLists.get(position).getKana();
        w_col[3] = wordLists.get(position).getTh();w_col[4] = wordLists.get(position).getType();
        w_col[5] = wordLists.get(position).getRomanji();w_col[6] = wordLists.get(position).getRomanji();

        final String W = w_col[1]+" 「"+w_col[5]+"」";
        final String WD = w_col[3]+" "+w_col[4];
        final String h = search;

        try {
            int start = W.indexOf(h);
            int end = start + h.length();
            SpannableString word = new SpannableString(W);
            word.setSpan(new TextHighlight(context),start,end,0);
            holder.w.setText(word);
            holder.w.setMovementMethod(new LinkMovementMethod());

        }catch (IndexOutOfBoundsException e){
            holder.w.setText(W);
        }

        try {
            int start2 = WD.indexOf(h);
            int end2 = start2 + h.length();
            SpannableString word2 = new SpannableString(WD);
            word2.setSpan(new TextHighlight(context),start2,end2,0);
            holder.wd.setText(word2);
            holder.wd.setMovementMethod(new LinkMovementMethod());
        }catch (IndexOutOfBoundsException e){
            holder.wd.setText(WD);
        }

        holder.w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentDetailActivity(w_col);
            }
        });

        holder.wd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentDetailActivity(w_col);
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               intentDetailActivity(w_col);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wordLists.size();
    }

    private void intentDetailActivity(String[] w_col){
        Intent intent = new Intent(context,DetailActivity.class);
        intent.putExtra("message_key",w_col);
        context.startActivity(intent);
    }
}
