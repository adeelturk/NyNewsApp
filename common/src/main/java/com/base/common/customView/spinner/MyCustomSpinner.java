package com.base.common.customView.spinner;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.base.common.R;
import com.base.common.utils.FontUtils;


/**
 * @author Adeel Ur Rehman Turk
 */

/**
 * A Customised Spinner with drop down icon , round background ,custom font and property to show error in Spinner
 */
public class MyCustomSpinner extends LinearLayout {


    private Spinner spinner;
    private TextView titleTextView;
    /*private Animation shake ;*/
    public MyCustomSpinner(Context context) {
        super(context);
    }

    public MyCustomSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, context);
    }

    public MyCustomSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, context);
    }

    private void init(AttributeSet attrs, Context context) {


        TypedArray typedArray = getContext().obtainStyledAttributes(
                attrs,
                R.styleable.MyCustomSpinner);


        final int arrayResourceId = typedArray.getResourceId(R.styleable.MyCustomSpinner_entries, 0);
        final int spinner_style = typedArray.getResourceId(R.styleable.MyCustomSpinner_spinner_style, 0);
         final boolean haveFullWidth = typedArray.getBoolean(R.styleable.MyCustomSpinner_have_full_width, true);
        final ColorStateList titleTextColor = typedArray.getColorStateList(R.styleable.MyCustomSpinner_title_color);
        final String title = typedArray.getString(R.styleable.MyCustomSpinner_title);


        if (spinner_style != 0) {
            spinner = new Spinner(context, null, spinner_style);
        } else {
            spinner = new Spinner(context);

        }
        titleTextView=new TextView(context);
        titleTextView.setTextColor(titleTextColor);
        titleTextView.setText(title);
        titleTextView.setTypeface(FontUtils.getInstance(context).getFont(FontUtils.KEY_BOLD));

        LayoutParams titleTextViewLayoutParams ;
        LayoutParams layoutParams ;

        if (haveFullWidth) {
            layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        }else{

            layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }
        titleTextViewLayoutParams=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        titleTextView.setPadding(20,20,20,5);
        titleTextView.setLayoutParams(titleTextViewLayoutParams);
        spinner.setLayoutParams(layoutParams);
        spinner.setBackgroundColor(ContextCompat.getColor(context,R.color.transperent_color));
        spinner.setPadding(20,5,5,20);
        spinner.setId(R.id.spinner_dd);

       // LinearLayout.LayoutParams llLayoutParams =new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);;
        this.setOrientation(LinearLayout.VERTICAL);
        this.setBackgroundResource(R.drawable.round_corner_onlyborder_button_grey);
        this.addView(titleTextView);
        this.addView(spinner);

        typedArray.recycle();

        if(arrayResourceId!=0){

            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, android.R.id.text1);
            spinnerAdapter.addAll(context.getResources().getStringArray(arrayResourceId));
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinnerAdapter);

        }
    }

    /**
     * Use this method to set Spinner Adapter
     *  @param adapter object of SpinnerAdapter
     *
     **/

    public void setAdapter(SpinnerAdapter adapter) {

        spinner.setAdapter(adapter);
    }

    /**
     * Use this method to get call back for item selected
     *  @param onItemSelectedListener Spinner drop down item selected listener
     *
     **/

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener) {

        spinner.setOnItemSelectedListener(onItemSelectedListener);
    }


    /**
     * Use this method to set error
     *  @param error error message to show in spinner
     *
     **/

    public void setError(String error) {

        try {
            if (error!=null || !error.trim().isEmpty()) {

                /*spinner.startAnimation(shake);*/
                ((TextView) spinner.getSelectedView()).setError(error);
                spinner.performClick();
            } else {
                /*spinner.clearAnimation();*/
                ((TextView) spinner.getSelectedView()).setError(null);

            }
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    public void setError(String error, int textViewResourceId) {

        if (error!=null || !error.trim().isEmpty()) {

            // spinner.startAnimation(shake);
            ((TextView) spinner.getSelectedView().findViewById(textViewResourceId)).setError(error);
            spinner.performClick();
        } else {
            // spinner.clearAnimation();
            ((TextView) spinner.getSelectedView().findViewById(textViewResourceId)).setError(null);

        }

    }

    public void setSelectedItem(int index){

        if(index>0)
        spinner.setSelection(index);
    }


    public int getSelectedPosition(){

       return spinner.getSelectedItemPosition();
    }
    public void resetToDefault(){

            spinner.setSelection(0);
    }

    @Override
    public boolean performClick() {
        spinner.performClick();
        return super.performClick();
    }
}
