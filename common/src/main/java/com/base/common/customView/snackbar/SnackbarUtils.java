package com.base.common.customView.snackbar;

import android.content.Context;
import android.graphics.Color;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.google.android.material.snackbar.Snackbar;
import com.base.common.R;


/**
 * @author Adeel Ur Rehman Turk
 */

/**
 * A Utility class to show snackbar of different types based on configurations in [SnackBarData]
 */

public class SnackbarUtils {


    /**
     * Use this method to show SnackBar
     *  @param v View object used in Snack Bar for anchoring
     *  @param snackBarData SnackbarData object having message and Snackbar Type
     *  @param context Context
     **/
    public static void showSnackbar(View v, SnackBarData snackBarData, Context context) {
        try {
            if (context == null || v == null || snackBarData == null) {
                return;
            }

            switch (snackBarData.getMessageType()) {


                case SnackBarData.ERROR:
                    showErrorSnackbarWithIcon(v, snackBarData.getMessage(), context, Snackbar.LENGTH_SHORT);
                    break;
                case SnackBarData.SUCCESS:
                case SnackBarData.SUCCESS_SMALL:
                    showSuccesSnackbarWithIcon(v, snackBarData.getMessage(), context, Snackbar.LENGTH_SHORT);
                    break;
                case SnackBarData.INFO:
                    showInfoSnackbarWithIcon(v, snackBarData.getMessage(), context, Snackbar.LENGTH_SHORT);
                    break;


            }
        }catch (Exception e){

            e.printStackTrace();
        }

    }

    private static void showInfoSnackbarWithIcon(View v, String snackbarText, Context context, int duration) {
        if (v == null || snackbarText == null) {
            return;
        }
        TSnackbar snackbar = TSnackbar.make(v, snackbarText, duration);
        View snackbarLayout = snackbar.getView();
        snackbarLayout.setBackgroundColor(context.getResources().getColor(R.color.arc_background));
        TextView textView = snackbarLayout.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        snackbar.setMaxWidth(3000);
       /* textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.info_icon, 0, 0, 0);
        textView.setCompoundDrawablePadding(context.getResources().getDimensionPixelOffset(R.dimen.icon_padding));*/
        snackbar.show();
    }

    private static void showSuccesSnackbarWithIcon(View v, String snackbarText, Context context, int duration) {
        if (v == null || snackbarText == null) {
            return;
        }
        TSnackbar snackbar = TSnackbar.make(v, snackbarText, duration);
        View snackbarLayout = snackbar.getView();
        snackbarLayout.setBackgroundColor(context.getResources().getColor(R.color.green));
        TextView textView = snackbarLayout.findViewById(R.id.snackbar_text);
        textView.setTextColor(context.getResources().getColor(R.color.white));
        textView.setGravity(Gravity.CENTER);
        snackbar.setMaxWidth(3000);
        snackbar.show();
    }


    private static void showErrorSnackbarWithIcon(View v, String snackbarText, Context context, int duration) {
        if (v == null || snackbarText == null) {
            return;
        }
        TSnackbar snackbar = TSnackbar.make(v, snackbarText, duration);
        View snackbarLayout = snackbar.getView();
        snackbarLayout.setBackgroundColor(context.getResources().getColor(R.color.snack_bar_error_color));
        TextView textView = snackbarLayout.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        snackbar.setMaxWidth(3000);
       /* textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_cardnumber, 0, 0, 0);
        textView.setCompoundDrawablePadding(context.getResources().getDimensionPixelOffset(R.dimen.icon_padding));*/
        snackbar.show();
    }

}
