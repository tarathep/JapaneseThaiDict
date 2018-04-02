package com.tarathep.bokie.japanesethaidict;

/**
 * Created by bokee on 13/4/2560.
 */

public class CheckLanguage {

    private boolean isENG(char ch){
        if((int)ch>=32&&(int)ch<=127)
            return true;
        return false;
    }
    private boolean isTH(char ch){
        if((int)ch>=3584&&(int)ch<=3711)
            return true;
        return false;
    }
    private boolean isJP(char ch){
        if(isJP_KANA(ch)){
            return true;
        }else if(isJP_KATA(ch)){
            return true;
        }else if(isJP_KANJI(ch))
            return true;
        return false;
    }
    private  boolean isJP_KANA(char ch){
        if((int)ch>=12352&&(int)ch<=12447)
            return true;
        return false;
    }
    private  boolean isJP_KATA(char ch){
        if((int)ch>=12448&&(int)ch<=12543)
            return true;
        return false;
    }
    public  boolean isJP_KANJI(char ch){
        if((int)ch>=19968&&(int)ch<=40879)
            return true;
        return false;
    }
    private  String checkSTR(String text){
        String result = "";
        String check = "";
        for(int i=0;i<text.length();i++){
            if(isENG(text.charAt(i))){
                if(!check.equals("en")){
                    result +="<en>";
                    check="en";
                }
                result +=text.charAt(i);
            }else if(isTH(text.charAt(i))){
                if(!check.equals("th")){
                    result +="<th>";
                    check="th";
                }
                result +=text.charAt(i);
            }else if(isJP(text.charAt(i))){
                if(!check.equals("jp")){
                    result +="<jp>";
                    check="jp";
                }
                result +=text.charAt(i);
            }
        }
        return result;
    }
    //special check extend jp lang kana kata
    private  String checkSTREX(String text){
        String result = "";
        String check = "";
        for(int i=0;i<text.length();i++){
            if(isENG(text.charAt(i))){
                if(!check.equals("en")){
                    result +="<en>";
                    check="en";
                }
                result +=text.charAt(i);
            }else if(isTH(text.charAt(i))){
                if(!check.equals("th")){
                    result +="<th>";
                    check="th";
                }
                result +=text.charAt(i);
            }else if(isJP_KANA(text.charAt(i))){
                if(!check.equals("jpkana")){
                    result +="<jpkana>";
                    check="jpkana";
                }
                result +=text.charAt(i);
            }else if(isJP_KATA(text.charAt(i))){
                if(!check.equals("jpkata")){
                    result +="<jpkata>";
                    check="jpkata";
                }
                result +=text.charAt(i);
            }else if(isJP_KANJI(text.charAt(i))){
                if(!check.equals("jpkanji")){
                    result +="<jpkanji>";
                    check="jpkanji";
                }
                result +=text.charAt(i);
            }
        }
        return result;
    }
    public  String check(String text){
        //<en>english<th>ไทย
        String cha[] = checkSTR(text).split("<"); //start index ->>1
        String check = "",tmp="";
        for(int i=1;i<cha.length;i++){
            if(cha[i].split(">")[0].equals("en")){
                check = "en";
                if(tmp.equals("")){
                    tmp = "en";
                }else if(!tmp.equals(check)){
                    return "??";
                }
            }else if(cha[i].split(">")[0].equals("th")){
                check = "th";
                if(tmp.equals("")){
                    tmp = "th";
                }else if(!tmp.equals(check)){
                    return "??";
                }
            }else if(cha[i].split(">")[0].equals("jp")){
                check ="jp";
                if(tmp.equals("")){
                    tmp = "jp";
                }else if(!tmp.equals(check)){
                    return "??";
                }
            }else{
                return "??";
            }
        }
        return check;
    }
    //extend kana kata
    public  String checkEX(String text){
        //<en>english<th>ไทย
        String cha[] = checkSTREX(text).split("<"); //start index ->>1
        String check = "",tmp="";
        for(int i=1;i<cha.length;i++){
            if(cha[i].split(">")[0].equals("en")){
                check = "en";
                if(tmp.equals("")){
                    tmp = "en";
                }else if(!tmp.equals(check)){
                    return "??";
                }
            }else if(cha[i].split(">")[0].equals("th")){
                check = "th";
                if(tmp.equals("")){
                    tmp = "th";
                }else if(!tmp.equals(check)){
                    return "??";
                }
            }else if(cha[i].split(">")[0].equals("jpkana")){
                check ="jpkana";
                if(tmp.equals("")){
                    tmp = "jpkana";
                }else if(!tmp.equals(check)){
                    if(tmp.equals("jpkata")||tmp.equals("jpkanji"))
                        return "jp";
                    return "??";
                }
            }else if(cha[i].split(">")[0].equals("jpkata")){
                check ="jpkata";
                if(tmp.equals("")){
                    tmp = "jpkata";
                }else if(!tmp.equals(check)){
                    if(tmp.equals("jpkana")||tmp.equals("jpkanji"))
                        return "jp";
                    return "??";
                }
            }else if(cha[i].split(">")[0].equals("jpkanji")){
                check ="jpkanji";
                if(tmp.equals("")){
                    tmp = "jpkanji";
                }else if(!tmp.equals(check)){
                    if(tmp.equals("jpkata")||tmp.equals("jpkana"))
                        return "jp";
                    return "??";
                }
            }else{
                return "??";
            }
        }
        return check;
    }
}
