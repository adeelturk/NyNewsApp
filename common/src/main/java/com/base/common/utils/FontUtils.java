
package com.base.common.utils;

import android.content.Context;
import android.graphics.Typeface;

import androidx.core.content.res.ResourcesCompat;

import java.util.Hashtable;

public class FontUtils {

    public static FontUtils fontUtils;

    public static final String KEY_BOLD="fontBold";
    public static final String KEY_REGULAR="fontRegular";

    /*public static final String KEY_LIGHT="light";
    public static final String KEY_THIN="thin";
    public static final String KEY_OCR="ocr";*/

    private Hashtable<String, Typeface> fontCache ;

    private Context context;

    public static FontUtils getInstance(Context context){

        if(fontUtils==null){

            fontUtils=new FontUtils(context);
        }else{

            fontUtils.setContext(context);
        }

        return fontUtils;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private   FontUtils(Context context){
        this.context=context;

        fontCache= new Hashtable<>();
//        setTypeface(fontCache,KEY_REGULAR, R.font.dms_font,context);
//        setTypeface(fontCache,KEY_BOLD,R.font.dms_font_bold,context);

    }

    public  void setTypeface(Hashtable<String, Typeface> fontCache,String fontKey, int fontName, Context context) {
        Typeface tf = fontCache.get(fontName);
        if (tf == null) {
                try {
                tf = ResourcesCompat.getFont(context,fontName);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            fontCache.put(fontKey, tf);
        }

    }

    public Typeface getFont(String fontKey){

        if (fontCache.containsKey(fontKey)) {

            return fontCache.get(fontKey);
        } else
            return null;
    }

    public  void dispose(){
        fontCache=null;
    }
}