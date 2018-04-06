package com.tarathep.bokie.japanesethaidict;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private WordListAdapter mAdapter;
    private GrammarListAdapter grammarListAdapter;
    private  DBManage dbmanage;
    Handler handler;
    ImageView imgbg;
    SearchView searchView;
    int page= R.id.menu_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //init
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);




        MenuItem item = menu.findItem(R.id.menuSearch);
        searchView = (SearchView)item.getActionView();
        searchView.setQueryHint("ค้นหา");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                //mAdapter = new WordListAdapter(getApplicationContext(),query,dbmanage.queryWORD(query));
                //recyclerView.setAdapter(mAdapter);

                grammarListAdapter = new GrammarListAdapter(getApplicationContext(),query,dbmanage.queryGrammar(query));
                recyclerView.setAdapter(grammarListAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String query) {
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(query.length()==0){
                            active();
                        } else if(query.startsWith("%")){

                        } else{
                            switch (page){
                                case R.id.menu_home:
                                    imgbg.setVisibility(View.GONE);
                                    mAdapter = new WordListAdapter(getApplicationContext(),query,dbmanage.queryWORD(query));
                                    recyclerView.setAdapter(mAdapter);
                                    break;
                                case R.id.menu_grammar:
                                    final String q;

                                    if(new CheckLanguage().checkEX(query).equals("en")){
                                        q= new Romanji().toKana(query);
                                        Toast.makeText(getApplicationContext(),q,Toast.LENGTH_SHORT).show();
                                    }else {
                                        q = query;
                                    }

                                    grammarListAdapter = new GrammarListAdapter(getApplicationContext(),q,dbmanage.queryGrammar(q));
                                    recyclerView.setAdapter(grammarListAdapter);
                                    break;
                            }

                        }

                    }
                },1000);

                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        page = id;
        switch (id){
            case R.id.menu_grammar:
                id = R.id.menu_grammar;
                break;
            case R.id.menu_home:
                id = R.id.menu_home;
                break;
            case R.id.menu_favorite:
                id = R.id.menu_favorite;
                break;
            case R.id.menu_test:
                id = R.id.menu_test;
                break;
            case R.id.menu_ch:
                id = R.id.menu_ch;
                break;
        }

        /*
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        active();
        return true;
    }

    void active(){
        switch (page){
            case R.id.menu_home:
                mAdapter = new WordListAdapter(getApplicationContext(),"", dbmanage.queryFavorite(1));
                recyclerView.setAdapter(mAdapter);
                break;
            case R.id.menu_grammar:
                grammarListAdapter = new GrammarListAdapter(getApplicationContext(),"", dbmanage.queryGrammar());
                recyclerView.setAdapter(grammarListAdapter);
                break;

        }

        if(dbmanage.queryFavorite(1).size()==0){
            imgbg.setVisibility(View.VISIBLE);
        }else {
            imgbg.setVisibility(View.GONE);
        }

    }
}
