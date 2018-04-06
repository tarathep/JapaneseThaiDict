package com.tarathep.bokie.japanesethaidict;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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


    public List queryFavorite(int expr){
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
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM table_words WHERE mark="+expr,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            wordList = new WordList(cursor.getString(cursor.getColumnIndex("word_id")),cursor.getString(cursor.getColumnIndex("jp")),cursor.getString(cursor.getColumnIndex("kana")),cursor.getString(cursor.getColumnIndex("th")),cursor.getString(cursor.getColumnIndex("type")),cursor.getString(cursor.getColumnIndex("romanji")),cursor.getString(cursor.getColumnIndex("mark")));
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
            wordList = new WordList(cursor.getString(cursor.getColumnIndex("word_id")),cursor.getString(cursor.getColumnIndex("jp")),cursor.getString(cursor.getColumnIndex("kana")),cursor.getString(cursor.getColumnIndex("th")),cursor.getString(cursor.getColumnIndex("type")),cursor.getString(cursor.getColumnIndex("romanji")),cursor.getString(cursor.getColumnIndex("mark")));
            cursor.moveToNext();
        }
        dbHelper.close();
        sqLiteDatabase.close();
        cursor.close();
        return wordList;
    }

    //use -------------------
    public List queryWORD(String expression){
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
            cursor = sqLiteDatabase.rawQuery("SELECT word_id,jp,kana,th,type,romanji,mark FROM table_words WHERE romanji LIKE '%"+expression+"%' order by (case when romanji ='"+expression+"' then 1 when romanji like '"+expression+"%' then 2 else 3 end);",null);

        }else if(new CheckLanguage().checkEX(expression).equals("th")) {
            cursor = sqLiteDatabase.rawQuery("SELECT word_id,jp,kana,th,type,romanji,mark FROM table_words WHERE th LIKE '%"+expression+"%' order by (case when th ='"+expression+"' then 1 when th like '"+expression+"%' then 2 else 3 end);",null);

        }else if(new CheckLanguage().checkEX(expression).equals("jpkana")||new CheckLanguage().checkEX(expression).equals("jpkata")) {
            cursor = sqLiteDatabase.rawQuery("SELECT word_id,jp,kana,th,type,romanji,mark FROM table_words WHERE kana LIKE '%"+expression+"%' order by (case when kana ='"+expression+"' then 1 when kana like '"+expression+"%' then 2 else 3 end);",null);

        }else {
            cursor = sqLiteDatabase.rawQuery("SELECT word_id,jp,kana,th,type,romanji,mark FROM table_words WHERE jp LIKE '%"+expression+"%' OR kana LIKE '%"+expression+"%' OR th LIKE '%"+expression+"%' OR romanji LIKE '%"+expression+"%' order by jp asc",null);
        }
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            wordList = new WordList(cursor.getString(cursor.getColumnIndex("word_id")),cursor.getString(cursor.getColumnIndex("jp")),cursor.getString(cursor.getColumnIndex("kana")),cursor.getString(cursor.getColumnIndex("th")),cursor.getString(cursor.getColumnIndex("type")),cursor.getString(cursor.getColumnIndex("romanji")),cursor.getString(cursor.getColumnIndex("mark")));
            list.add(wordList);
            cursor.moveToNext();
        }
        dbHelper.close();
        sqLiteDatabase.close();
        cursor.close();
        return list;
    }

    public void updateMask(String pk,String mask){
        DBHelper dbHelper =new DBHelper(context);
        try {
            dbHelper.createDataBase();
            dbHelper.openDataBase();
        }
        catch (Exception e) {e.printStackTrace();}
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("UPDATE table_words SET mark='"+mask+"' WHERE word_id="+pk+"");
        dbHelper.close();
        sqLiteDatabase.close();
    }
    //- - - - -
    public GrammarList queryGrammarPrimaryKey(String PK){
        DBHelper dbHelper =new DBHelper(context);
        try {
            dbHelper.createDataBase();
            dbHelper.openDataBase();
        }
        catch (Exception e) {e.printStackTrace();}
        dbHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM grammar_lists WHERE id="+PK,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            grammarList = new GrammarList(cursor.getString(cursor.getColumnIndex("id")),cursor.getString(cursor.getColumnIndex("grammar")),cursor.getString(cursor.getColumnIndex("mean")),cursor.getString(cursor.getColumnIndex("_group")),cursor.getString(cursor.getColumnIndex("mark")));
            cursor.moveToNext();
        }
        dbHelper.close();
        sqLiteDatabase.close();
        cursor.close();
        return grammarList;
    }
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
