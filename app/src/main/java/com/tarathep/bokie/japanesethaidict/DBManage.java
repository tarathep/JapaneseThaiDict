package com.tarathep.bokie.japanesethaidict;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bokee on 11/4/2560.
 */

public class DBManage {
    Context context;
    WordList wordList;
    GrammarList grammarList;

    DBManage(Context context){
        this.context = context;
    }


    public List getQueryDict(String expression){
        DBHelper dbHelper =new DBHelper(context);
        try {
            dbHelper.createDataBase();
            dbHelper.openDataBase();
        }
        catch (Exception e) {e.printStackTrace();}

        List list = new ArrayList();
        dbHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor;


        if(new CheckLanguage().checkEX(expression).equals("en")){
            cursor = sqLiteDatabase.rawQuery("SELECT word_id,jp,kana,th,type,romanji,history,favorite FROM table_words WHERE romanji LIKE '%"+expression+"%' order by (case when romanji ='"+expression+"' then 1 when romanji like '"+expression+"%' then 2 else 3 end);",null);

        }else if(new CheckLanguage().checkEX(expression).equals("th")) {
            cursor = sqLiteDatabase.rawQuery("SELECT word_id,jp,kana,th,type,romanji,history,favorite FROM table_words WHERE th LIKE '%"+expression+"%' order by (case when th ='"+expression+"' then 1 when th like '"+expression+"%' then 2 else 3 end);",null);

        }else if(new CheckLanguage().checkEX(expression).equals("jpkana")||new CheckLanguage().checkEX(expression).equals("jpkata")) {
            cursor = sqLiteDatabase.rawQuery("SELECT word_id,jp,kana,th,type,romanji,history,favorite FROM table_words WHERE kana LIKE '%"+expression+"%' order by (case when kana ='"+expression+"' then 1 when kana like '"+expression+"%' then 2 else 3 end);",null);

        }else {
            cursor = sqLiteDatabase.rawQuery("SELECT word_id,jp,kana,th,type,romanji,history,favorite FROM table_words WHERE jp LIKE '%"+expression+"%' order by (case when jp ='"+expression+"' then 1 when jp like '"+expression+"%' then 2 else 3 end);",null);
        }
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            wordList = new WordList(cursor.getString(cursor.getColumnIndex("word_id")),cursor.getString(cursor.getColumnIndex("jp")),cursor.getString(cursor.getColumnIndex("kana")),cursor.getString(cursor.getColumnIndex("th")),cursor.getString(cursor.getColumnIndex("type")),cursor.getString(cursor.getColumnIndex("romanji")),cursor.getString(cursor.getColumnIndex("history")),cursor.getString(cursor.getColumnIndex("favorite")));
            list.add(wordList);
            cursor.moveToNext();
        }
        dbHelper.close();
        sqLiteDatabase.close();
        cursor.close();
        return list;
    }


    public WordList queryPrimaryKey(String PK){
        DBHelper dbHelper =new DBHelper(context);
        try {
            dbHelper.createDataBase();
            dbHelper.openDataBase();
        }
        catch (Exception e) {e.printStackTrace();}
        dbHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM table_words WHERE word_id="+PK,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            wordList = new WordList(cursor.getString(cursor.getColumnIndex("word_id")),cursor.getString(cursor.getColumnIndex("jp")),cursor.getString(cursor.getColumnIndex("kana")),cursor.getString(cursor.getColumnIndex("th")),cursor.getString(cursor.getColumnIndex("type")),cursor.getString(cursor.getColumnIndex("romanji")),cursor.getString(cursor.getColumnIndex("history")),cursor.getString(cursor.getColumnIndex("favorite")));
            cursor.moveToNext();
        }
        dbHelper.close();
        sqLiteDatabase.close();
        cursor.close();
        return wordList;
    }


// - - - - HISTORY - - - - -

    public List<WordList> getHistory(){
        DBHelper dbHelper =new DBHelper(context);
        try {
            dbHelper.createDataBase();
            dbHelper.openDataBase();
        }
        catch (Exception e) {e.printStackTrace();}

        List list = new ArrayList();
        dbHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqLiteDatabase.rawQuery("select * from table_words where history is not null order by history desc;",null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            wordList = new WordList(cursor.getString(cursor.getColumnIndex("word_id")),cursor.getString(cursor.getColumnIndex("jp")),cursor.getString(cursor.getColumnIndex("kana")),cursor.getString(cursor.getColumnIndex("th")),cursor.getString(cursor.getColumnIndex("type")),cursor.getString(cursor.getColumnIndex("romanji")),cursor.getString(cursor.getColumnIndex("history")),cursor.getString(cursor.getColumnIndex("favorite")));
            list.add(wordList);
            cursor.moveToNext();
        }
        dbHelper.close();
        sqLiteDatabase.close();
        cursor.close();
        return list;
    }

    public int getLastHistory(){

        DBHelper dbHelper =new DBHelper(context);
        try {
            dbHelper.createDataBase();
            dbHelper.openDataBase();
        }
        catch (Exception e) {e.printStackTrace();}

        int vlast = 0;
        dbHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqLiteDatabase.rawQuery("select * from table_words where history is not null order by history desc limit 1",null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            vlast = cursor.getInt(cursor.getColumnIndex("history"));
            cursor.moveToNext();
        }
        dbHelper.close();
        sqLiteDatabase.close();
        cursor.close();

        return vlast;

    }

    public void clearHistory(){
        DBHelper dbHelper =new DBHelper(context);
        try {
            dbHelper.createDataBase();
            dbHelper.openDataBase();
        }
        catch (Exception e) {e.printStackTrace();}
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("update table_words set history = null");
        dbHelper.close();
        sqLiteDatabase.close();
    }

    public void updateHistory(String pk){
        int historyId = getLastHistory()+1;

        DBHelper dbHelper =new DBHelper(context);
        try {
            dbHelper.createDataBase();
            dbHelper.openDataBase();
        }
        catch (Exception e) {e.printStackTrace();}
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("update table_words set history = "+historyId+" where word_id ="+pk);
        dbHelper.close();
        sqLiteDatabase.close();
    }

// - - - - -FAVORITE - - - - --

    public List<WordList> getFavorite(){
        DBHelper dbHelper =new DBHelper(context);
        try {
            dbHelper.createDataBase();
            dbHelper.openDataBase();
        }
        catch (Exception e) {e.printStackTrace();}

        List list = new ArrayList();
        dbHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqLiteDatabase.rawQuery("select * from table_words where favorite is not null order by favorite desc;",null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            wordList = new WordList(cursor.getString(cursor.getColumnIndex("word_id")),cursor.getString(cursor.getColumnIndex("jp")),cursor.getString(cursor.getColumnIndex("kana")),cursor.getString(cursor.getColumnIndex("th")),cursor.getString(cursor.getColumnIndex("type")),cursor.getString(cursor.getColumnIndex("romanji")),cursor.getString(cursor.getColumnIndex("history")),cursor.getString(cursor.getColumnIndex("favorite")));
            list.add(wordList);
            cursor.moveToNext();
        }
        dbHelper.close();
        sqLiteDatabase.close();
        cursor.close();
        return list;
    }

    public int getLastFavorite(){

        DBHelper dbHelper =new DBHelper(context);
        try {
            dbHelper.createDataBase();
            dbHelper.openDataBase();
        }
        catch (Exception e) {e.printStackTrace();}

        int vlast = 0;
        dbHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqLiteDatabase.rawQuery("select * from table_words where favorite is not null order by favorite desc limit 1",null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            vlast = cursor.getInt(cursor.getColumnIndex("favorite"));
            cursor.moveToNext();
        }
        dbHelper.close();
        sqLiteDatabase.close();
        cursor.close();

        return vlast;

    }

    public void clearFavorite(){
        DBHelper dbHelper =new DBHelper(context);
        try {
            dbHelper.createDataBase();
            dbHelper.openDataBase();
        }
        catch (Exception e) {e.printStackTrace();}
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("update table_words set favorite = null");
        dbHelper.close();
        sqLiteDatabase.close();
    }

    public void Like(String pk){
        int favoriteId = getLastFavorite()+1;

        DBHelper dbHelper =new DBHelper(context);
        try {
            dbHelper.createDataBase();
            dbHelper.openDataBase();
        }
        catch (Exception e) {e.printStackTrace();}
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("update table_words set favorite = "+favoriteId+" where word_id ="+pk);
        dbHelper.close();
        sqLiteDatabase.close();
    }

    public void UnLike(String pk){

        DBHelper dbHelper =new DBHelper(context);
        try {
            dbHelper.createDataBase();
            dbHelper.openDataBase();
        }
        catch (Exception e) {e.printStackTrace();}
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("update table_words set favorite = null where word_id ="+pk);
        dbHelper.close();
        sqLiteDatabase.close();
    }


    // --- Find ONE
    public WordList getSingleWord(String expr){
        DBHelper dbHelper =new DBHelper(context);
        try {
            dbHelper.createDataBase();
            dbHelper.openDataBase();
        }
        catch (Exception e) {e.printStackTrace();}

        List list = new ArrayList();
        dbHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqLiteDatabase.rawQuery("select * from table_words where jp=='"+expr+"' order by history desc limit 1",null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            wordList = new WordList(cursor.getString(cursor.getColumnIndex("word_id")),cursor.getString(cursor.getColumnIndex("jp")),cursor.getString(cursor.getColumnIndex("kana")),cursor.getString(cursor.getColumnIndex("th")),cursor.getString(cursor.getColumnIndex("type")),cursor.getString(cursor.getColumnIndex("romanji")),cursor.getString(cursor.getColumnIndex("history")),cursor.getString(cursor.getColumnIndex("favorite")));
            //list.add(wordList);
            cursor.moveToNext();
        }
        dbHelper.close();
        sqLiteDatabase.close();
        cursor.close();
        return wordList;
    }

    // --- Game
    public List<WordList> getWordListGame(){
        DBHelper dbHelper =new DBHelper(context);
        try {
            dbHelper.createDataBase();
            dbHelper.openDataBase();
        }
        catch (Exception e) {e.printStackTrace();}

        List list = new ArrayList();
        dbHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select jp,word_id,kana,th,type,romanji,history,favorite,game from table_words where favorite is not null and game is null group by (word_id) order by random() limit 4;",null);

        /*
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] select = {"jp","word_id","kana","th","type","romanji","history","favorite","game"};
        String table = "table_words";

        qb.setTables(table);
        qb.setDistinct(true);
        qb.appendWhere("favorite is not null");
       cursor = qb.query(sqLiteDatabase,select,null,null,null,null,"random() limit 4");
       */
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            wordList = new WordList(cursor.getString(cursor.getColumnIndex("word_id")),cursor.getString(cursor.getColumnIndex("jp")),cursor.getString(cursor.getColumnIndex("kana")),cursor.getString(cursor.getColumnIndex("th")),cursor.getString(cursor.getColumnIndex("type")),cursor.getString(cursor.getColumnIndex("romanji")),cursor.getString(cursor.getColumnIndex("history")),cursor.getString(cursor.getColumnIndex("favorite")));
            wordList.setGame(cursor.getString(cursor.getColumnIndex("game")));

            list.add(wordList);
            cursor.moveToNext();
        }
        dbHelper.close();
        sqLiteDatabase.close();
        cursor.close();
        return list;
    }



    // GRAMMAR

    public List queryGrammar(){
        DBHelper dbHelper =new DBHelper(context);
        try {
            dbHelper.createDataBase();
            dbHelper.openDataBase();
        }
        catch (Exception e) {e.printStackTrace();}

        List list = new ArrayList();
        dbHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqLiteDatabase.rawQuery("SELECT id,grammar,mean,_group,mark FROM grammar_lists order by id asc",null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            grammarList = new GrammarList(cursor.getString(cursor.getColumnIndex("id")),cursor.getString(cursor.getColumnIndex("grammar")),cursor.getString(cursor.getColumnIndex("mean")),cursor.getString(cursor.getColumnIndex("_group")),cursor.getString(cursor.getColumnIndex("mark")));
            list.add(grammarList);
            cursor.moveToNext();
        }
        dbHelper.close();
        sqLiteDatabase.close();
        cursor.close();
        return list;
    }


    public List queryGrammar(String expression){
        DBHelper dbHelper =new DBHelper(context);
        try {
            dbHelper.createDataBase();
            dbHelper.openDataBase();
        }
        catch (Exception e) {e.printStackTrace();}

        List list = new ArrayList();
        dbHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqLiteDatabase.rawQuery("SELECT id,grammar,mean,_group,mark FROM grammar_lists WHERE grammar LIKE '%"+expression+"%' OR mean LIKE '%"+expression+"%' order by grammar asc",null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            grammarList = new GrammarList(cursor.getString(cursor.getColumnIndex("id")),cursor.getString(cursor.getColumnIndex("grammar")),cursor.getString(cursor.getColumnIndex("mean")),cursor.getString(cursor.getColumnIndex("_group")),cursor.getString(cursor.getColumnIndex("mark")));
            list.add(grammarList);
            cursor.moveToNext();
        }
        dbHelper.close();
        sqLiteDatabase.close();
        cursor.close();
        return list;
    }


}
