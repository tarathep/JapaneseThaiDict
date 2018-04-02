package com.tarathep.bokie.japanesethaidict;

/**
 * Created by bokee on 11/4/2560.
 */

public class WordList {
    private String id,jp,kana,th,type,romanji,mark;

    public WordList(){

    }

    public WordList(String id,String jp, String kana, String th, String type, String romanji,String mark){
        this.id = id;
        this.jp = jp;
        this.kana = kana;
        this.th = th;
        this.type = type;
        this.romanji = romanji;
        this.mark = mark;
    }


    public String getId() {
        return id;
    }

    public String getJp() {
        return jp;
    }

    public String getKana() {
        return kana;
    }

    public String getTh() {
        return th;
    }

    public String getType() {
        return type;
    }

    public String getRomanji() {
        return romanji;
    }

    public String getMark() {
        return mark;
    }
}
