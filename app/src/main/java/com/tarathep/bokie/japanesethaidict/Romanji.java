package com.tarathep.bokie.japanesethaidict;

/**
 * Created by bokee on 13/4/2560.
 */

public class Romanji {

    private String[]DicKana  = {"aa","あ","ii","い","uu","う","ee","え","oo","お"
            ,"か","が","き","ぎ","く","ぐ","け","げ","こ","ご"
            ,"さ","ざ","し","じ","す","ず","せ","ぜ","そ","ぞ"
            ,"た","だ","ち","じ","っ","つ","tsu","て","で","と","ど"
            ,"な","に","ぬ","ね","の"
            ,"は","ば","ぱ","ひ","び","ぴ","ふ","ぶ","ぷ","へ","べ","ぺ","ほ","ぼ","ぽ"
            ,"ま","み","む","め","も"
            ,"ゃ","や","ゅ","ゆ","ょ","よ"
            ,"ら","り","る","れ","ろ"
            ,"ゎ","わ" ,"を","ん"};


    private String[]DicRomanji = {"aa","a","ii","i","uu","u","ee","e","oo","o"
            ,"ka","ga","ki","gi","ku","gu","ke","ge","ko","go"
            ,"sa","za","shi","ji","su","zu","se","ze","so","zo"
            ,"ta","da","chi","ji","tsuu","tsu","tsu","te","de","to","do"
            ,"na","ni","nu","ne","no"
            ,"ha","ba","pa","hi","bi","pi","fu","bu","pu","he","be","pe","ho","bo","po"
            ,"ma","mi","mu","me","mo"
            ,"yaa","ya","yuu","yu","yoo","yo"
            ,"ra","ri","ru","re","ro"
            ,"waa","wa" ,"wo","nn"};


    public Romanji(){

    }
    public String toKana(String romanji){
        String output = "";
        for(int i=0;i<romanji.length();i++){
            try {
                output += DicKana[SearchIndex(""+romanji.charAt(i))];
            }catch (IndexOutOfBoundsException e){}

        }
        return output;
    }
    private int SearchIndex(String input){
        for(int i=0;i<DicRomanji.length;i++){
            if(input.equals(DicRomanji[i])){
                return i;
            }
        }
        return -1;
    }
    public String convert(String jp){
        try{
           return convertCheck(jp);
        }catch(Exception e){return "";}
    }
    private String convertCheck(String input){
        String output="",sub1="",sub2="",type="";
        boolean tsuu = false;
        for(int i =0;i<input.length();i++){
            if(isJP_KANA(input.charAt(i))){
                type = "kana";
            }else if(isJP_KATA(input.charAt(i))){
                type = "kata";
            }else{
                output+=input.charAt(i);
            }

            if(convertCHAR(type,input.charAt(i)).equals("yaa")||convertCHAR(type,input.charAt(i)).equals("yuu")||convertCHAR(type,input.charAt(i)).equals("yoo")){
                if(convertCHAR(type,input.charAt(i)).equals("yaa")){
                    sub1 = "ya";sub2="a";
                }else if(convertCHAR(type,input.charAt(i)).equals("yuu")){
                    sub1 = "yu";sub2="u";
                }else if(convertCHAR(type,input.charAt(i)).equals("yoo")){
                    sub1 = "yo";sub2="o";
                }
                String cut3="",cut2="";
                try{
                    cut3 = output.substring(output.length()-3, output.length());
                }catch(Exception e){}
                try{
                    cut2 = output.substring(output.length()-2, output.length());
                }catch(Exception e){}

                if(cut3.equals("shi")||cut3.equals("chi")||cut2.equals("ji"))
                    output = output.substring(0,output.length()-1)+sub2;
                else if(cut2.equals("ki")||cut2.equals("ni")||cut2.equals("hi")||cut2.equals("mi")||cut2.equals("ri")||cut2.equals("gi")||cut2.equals("bi")||cut2.equals("pi"))
                    output = output.substring(0,output.length()-1)+sub1;

            }else if(convertCHAR(type,input.charAt(i)).equals("nn")){
                output +="n";
            }else if(convertCHAR(type,input.charAt(i)).equals("tsuu")){
                tsuu = true;
            }else{
                if(tsuu){
                    output +=convertCHAR(type,input.charAt(i)).substring(0,convertCHAR(type,input.charAt(i)).length()-1)+convertCHAR(type,input.charAt(i));
                    if(output.substring(output.length()-3).equals("shi")){
                        output = output.substring(0, output.length()-4)+output.substring(output.length()-3);
                    }
                    tsuu =false;
                }else{
                    if(convertCHAR(type,input.charAt(i)).equals("-")){
                        output += output.substring(output.length()-1);
                    }else{
                        output +=convertCHAR(type,input.charAt(i));
                    }
                }

            }
        }
        return  output;
    }
    private String convertCHAR(String chartype, char character){
        String result="";
        int index;
        if(chartype.equals("kana"))
            index = 12353;
        else
            index = 12449;
        for(int i=0;i<81;i++){
            if(i==79){
                index = index+2;
            }
            if((int)character==index){
                //System.out.println(i);
                return DicRomanji[i];
            }else if(character=='ー'){
                return "-";
            }
            index++;
        }
        return result;
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
    private boolean isJP_KANA(char ch){
        if((int)ch>=12352&&(int)ch<=12447)
            return true;
        return false;
    }
    private boolean isJP_KATA(char ch){
        if((int)ch>=12448&&(int)ch<=12543)
            return true;
        return false;
    }
    private boolean isJP_KANJI(char ch){
        if((int)ch>=19968&&(int)ch<=40879)
            return true;
        return false;
    }
    private boolean isENG(char ch){
        if((int)ch>=32&&(int)ch<=127)
            return true;
        return false;
    }
    private String convertROMANJI(String input){
        String output="";
        int timeout = input.length();
        DicRomanji[80] = "n";
        while(input.length()>0){
            for(int i=0;i<DicRomanji.length;i++){
                if(input.startsWith(DicRomanji[i])){
                    if(i==79){
                        output +=(char)(12352+(i+1+2));
                    }else if(i==80){
                        output +=(char)(12352+(i+1+2));
                    }else{
                        output +=(char)(12352+(i+1));
                    }


                    System.out.println(DicRomanji[i]+" "+i);
                    input=input.substring(DicRomanji[i].length());
                    break;
                }else{
                    if(input.startsWith(" ")){
                        output+=" ";
                        input=input.substring(1);
                    }else if(input.startsWith("-")){
                        output+="-";
                        input=input.substring(1);
                    }else if(input.startsWith("[")){
                        output+="[";
                        input=input.substring(1);
                    }else if(input.startsWith("]")){
                        output+="]";
                        input=input.substring(1);
                    }else if(input.startsWith("+")){
                        output+="+";
                        input=input.substring(1);
                    }else if(input.startsWith("*")){
                        output+="*";
                        input=input.substring(1);
                    }else if(input.startsWith("=")){
                        output+="=";
                        input=input.substring(1);
                    }else if(input.startsWith("(")){
                        output+="(";
                        input=input.substring(1);
                    }else if(input.startsWith(")")){
                        output+=")";
                        input=input.substring(1);
                    }
                }
            }
            timeout--;
            if(timeout==-1){
                return "";
            }
        }
        return output;
    }



}
