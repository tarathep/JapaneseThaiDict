package com.tarathep.bokie.japanesethaidict;

/**
 * Created by bokee on 4/6/2018.
 */

public class GrammarList {
    private String id,grammar,mean,group,mark;

    public GrammarList(String id,String grammar,String mean,String group,String mark){
        this.id = id;
        this.grammar = grammar;
        this.mean = mean;
        this.group = group;
        this.mark = mark;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGrammar() {
        return grammar;
    }

    public void setGrammar(String grammar) {
        this.grammar = grammar;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
