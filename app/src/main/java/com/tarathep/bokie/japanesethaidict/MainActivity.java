package com.tarathep.bokie.japanesethaidict;

import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WordListAdapter mAdapter;
    private  DBManage dbmanage;
    Handler handler;
    ImageView imgbg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter = new WordListAdapter(getApplicationContext(),query,dbmanage.queryWORD(query));
                recyclerView.setAdapter(mAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String query) {
               handler.removeCallbacksAndMessages(null);
               handler.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       if(query.length()==0){
                           mAdapter = new WordListAdapter(getApplicationContext(),query,dbmanage.queryFavorite(1));
                           active();
                       }else if(query.startsWith("%")){

                       } else{
                           imgbg.setVisibility(View.GONE);
                           mAdapter = new WordListAdapter(getApplicationContext(),query,dbmanage.queryWORD(query));
                       }

                       recyclerView.setAdapter(mAdapter);
                   }
               },1000);

                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void init(){
        imgbg = (ImageView) findViewById(R.id.img_bg);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        dbmanage = new DBManage(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new com.tarathep.bokie.japanesethaidict.DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        handler = new Handler();
        active();
    }


    void active(){
        mAdapter = new WordListAdapter(getApplicationContext(),"", dbmanage.queryFavorite(1));
        recyclerView.setAdapter(mAdapter);
        if(dbmanage.queryFavorite(1).size()==0){
            imgbg.setVisibility(View.VISIBLE);
        }else {
            imgbg.setVisibility(View.GONE);
        }
    }
}
