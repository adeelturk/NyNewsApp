package com.base.common.utils;

import android.util.DisplayMetrics;

import com.base.common.base.BaseActivity;


public class CardSizeUtil {

    public static final int CARD_WIDTH_LARGE_DEVICE_PERCENTAGE=50;
    public static final int CARD_WIDTH_PERCENTAGE=45;
    public static final int CARD_HEITGHT_PERCENTAGE=70;
    public static final int CARD_OFFSET_PERCENTAGE=3;

    public static CardSize getCardSize(BaseActivity activity) {

        float cardWidth = 0;
        float cardHeight = 0;
        float spaceOffset = 0;
        float screenWidth = 0;
        float screenHeight = 0;
        float density;


        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        density = displayMetrics.density;
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        screenHeight = height;
        screenWidth = width;
        float widthDp = pxToDp(width, density);

        if (widthDp <= 320) {
            cardWidth = width;

        } else if(widthDp>500) {

            cardWidth = (width / 100) * CARD_WIDTH_LARGE_DEVICE_PERCENTAGE;


        }else{

            cardWidth = (width / 100) * CARD_WIDTH_PERCENTAGE;
        }

        cardHeight = (cardWidth / 100) * CARD_HEITGHT_PERCENTAGE;

        spaceOffset = (width / 100) * CARD_OFFSET_PERCENTAGE;


        return new CardSize(cardWidth, cardHeight, spaceOffset, screenWidth, screenHeight, density);

    }


    public static CardSize getCardSizeForGrid(BaseActivity activity) {

        float cardWidth = 0;
        float cardHeight = 0;
        float spaceOffset = 0;
        float screenWidth = 0;
        float screenHeight = 0;
        float density;


        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        density = displayMetrics.density;
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        screenHeight = height;
        screenWidth = width;


        cardWidth = (width / 100) * CARD_WIDTH_PERCENTAGE;
        cardHeight = (cardWidth / 100) * CARD_HEITGHT_PERCENTAGE;

        spaceOffset = (width / 100) * CARD_OFFSET_PERCENTAGE;


        return new CardSize(cardWidth, cardHeight, spaceOffset, screenWidth, screenHeight, density);

    }

    public static float pxToDp(int somePxValue, float density) {

        return somePxValue / density;
    }
}
