package com.base.common.customView.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.common.customView.textview.AutoResizeTextView;
import com.base.common.R;

import java.util.Objects;

/**
 * Created by Turk
 * on 3/13/2018.
 */

/**
 *
 * @author Adeel Ur Rehman Turk
 *
 * DialogSheet is a utility which follow s builder pattern i.e every method would return DialogSheet object so making
 * it easy to use. DialogSheet class can be used to show Bottom Dialog Sheet whcih helps user perform actions or get notified about important messages.
 * use [getInstance] method to get its instance and show the picker. DatePickerFragment will return the selected date in given format
 * Constructor parameter
 *
 */

@SuppressWarnings("UnusedReturnValue,unused")
public class DialogSheet {
    private Context context;
    private MyDialog dialog;
    private int buttonColor = -1;
    private int backgroundColor = -1;
    private boolean showButtons = false;
    private TextView titleTextView;
    private TextView messageTextView;
    private ImageView iconImageView;
   // private ImageView closeBtn;
    private AutoResizeTextView positiveButton;
    private AutoResizeTextView negativeButton;
    private RelativeLayout textContainer;
    private LinearLayout messageContainer;
    public FrameLayout mainDialogContainer;
    private View inflatedView;

    /*
    *
    * Constructor
    * @param context Context to use in intialising or getting resource values
    * */

    public DialogSheet(Context context) {
        this.context = context;
        this.init(context);
    }

    public MyDialog getDialog() {
        return dialog;
    }

    public DialogSheet setTitle(CharSequence title) {
        this.titleTextView.setVisibility(View.VISIBLE);
        this.titleTextView.setText(title);
        return this;
    }

    /*
     * Method to check if dialog sheet is visible
     * @return Boolean value would be true if dialog sheet is visible and vice versa
     **/

    public boolean isShowing(){

        if(dialog!=null) {
           return dialog.isShowing();
        }else{

            return false;
        }
    }

    /**
     *  Method to set title for  dialog sheet from resources i.e. stings.xml
     *  @param titleRes Pass id of your string resource value
     *
     */
    public DialogSheet setTitle(@StringRes int titleRes) {
        this.setTitle(this.context.getResources().getString(titleRes));
        return this;
    }

    /**
     *  Method to set message for  dialog sheet
     *  @param message Pass String value to show message
     *
     */
    public DialogSheet setMessage(CharSequence message) {
        this.messageTextView.setText(message);
       // this.closeBtn.setVisibility(View.GONE);
        this.messageTextView.setVisibility(View.VISIBLE);

        return this;
    }

    /**
     *  Method to set message for you dialog sheet
     *  @param messageRes Pass id of your string resource value
     *
     */
    public DialogSheet setMessage(@StringRes int messageRes) {
        this.setMessage(this.context.getResources().getString(messageRes));
        return this;
    }
    /**
     *  Method to set Drawable  icon on left of title for  dialog sheet
     *  @param icon Pass drawable icon
     *
     */

    public DialogSheet setIcon(Drawable icon) {
        this.showIcon();
        this.iconImageView.setImageDrawable(icon);
        return this;
    }

    /**
     *  Method to set Bitmap icon on left of title for  dialog sheet
     *  @param icon Pass Bitmap icon
     *
     */
    public DialogSheet setIcon(Bitmap icon) {
        this.showIcon();
        this.iconImageView.setImageBitmap(icon);
        return this;
    }

    /**
     *  Method to set icon  on left of title for dialog sheet
     *  @param iconRes Pass resource id of icon
     *
     */
    public DialogSheet setIcon(@DrawableRes int iconRes) {
        this.showIcon();
        this.iconImageView.setImageResource(iconRes);
        return this;
    }

    /**
     *  Method to set positive button text and click listener
     *  Dialog will dismiss once user click
     *  @param text positive button title
     *  @param onPositiveClickListener OnPositiveClickListener call back for positive click
     *
     */

    private DialogSheet setPositiveButton(CharSequence text, final OnPositiveClickListener onPositiveClickListener) {
        this.positiveButton.setVisibility(View.VISIBLE);
        this.positiveButton.setText(text);
        this.positiveButton.setOnClickListener(view -> {

            DialogSheet.this.dialog.dismiss();
            if (onPositiveClickListener != null) {
                onPositiveClickListener.onClick(view);
            }

        });
        this.showButtons = true;
        return this;
    }

    /**
     *  Method to set positive button text and click listener
     *  Dialog will not dismiss on user click
     *  @param text positive button title
     *  @param onPositiveClickListener OnPositiveClickListenerNotDissmisable call back for positive click
     *
     */

    private DialogSheet setPositiveButton(CharSequence text, final OnPositiveClickListenerNotDissmisable onPositiveClickListener) {
        this.positiveButton.setVisibility(View.VISIBLE);
        this.positiveButton.setText(text);
        this.positiveButton.setOnClickListener(view -> {


            if (onPositiveClickListener != null) {
                onPositiveClickListener.onClick(view);
            }


        });
        this.showButtons = true;
        return this;
    }

    /**
     *  Method to set positive dialog Cancel callback
     *
     *  @param listener MyOnCancelListener call back te get notified when dialog is canceled
     *
     */
    public DialogSheet setCancelListener(MyOnCancelListener listener) {

        dialog.setOnCancelListener(listener);
        return this;
    }

    /**
     *  Method to set negative button text and click listener
     *  Dialog will dismiss once user click
     *  @param text negative button title
     *  @param onNegativeClickListener OnNegativeClickListener call back for positive click
     *
     */
    private DialogSheet setNegativeButton(CharSequence text, final OnNegativeClickListener onNegativeClickListener) {
        this.negativeButton.setVisibility(View.VISIBLE);
        this.negativeButton.setText(text);
        this.negativeButton.setOnClickListener(view -> {


            if (onNegativeClickListener != null) {
                onNegativeClickListener.onClick(view);
            }
            DialogSheet.this.dialog.dismiss();

        });
        this.showButtons = true;
        return this;
    }

    /**
     *  Method to set positive button text and click listener
     *  Dialog will not dismiss on user click
     *  @param textRes Pass id of your string resource value
     *  @param onPositiveClickListener OnPositiveClickListenerNotDissmisable call back for positive click
     *
     */
    public DialogSheet setPositiveButtonNonAutoDismissable(@StringRes int textRes, OnPositiveClickListenerNotDissmisable onPositiveClickListener) {
        this.setPositiveButton(this.context.getResources().getString(textRes), onPositiveClickListener);
        return this;
    }

    /**
     *  Method to set positive button text and click listener
     *  Dialog will dismiss once user click
     *  @param textRes positive button title
     *  @param onPositiveClickListener OnPositiveClickListener call back for positive click
     *
     */
    public DialogSheet setPositiveButton(@StringRes int textRes, OnPositiveClickListener onPositiveClickListener) {
        this.setPositiveButton(this.context.getResources().getString(textRes), onPositiveClickListener);
        return this;
    }

    /**
     *  Method to set negative button text and click listener
     *  Dialog will dismiss once user click
     *  @param textRes positive button title
     *  @param onNegativeClickListener OnNegativeClickListener call back for positive click
     *
     */

    public DialogSheet setNegativeButton(@StringRes int textRes, OnNegativeClickListener onNegativeClickListener) {
        this.setNegativeButton(this.context.getResources().getString(textRes), onNegativeClickListener);
        return this;
    }

    /**
     *  Method to set Buttons Color
     *  @param buttonsColor pass color value
     */

    @SuppressWarnings("unused")
    public DialogSheet setButtonsColor(@ColorInt int buttonsColor) {
        this.buttonColor = buttonsColor;
        return this;
    }

    /**
     *  Method to set Buttons Color
     *  @param buttonsColorRes pass color id from res/color.xml
     *
     */
    public DialogSheet setButtonsColorRes(@ColorRes int buttonsColorRes) {
        this.buttonColor = ContextCompat.getColor(this.context, buttonsColorRes);
        return this;
    }


    /**
     *  Method to set Background Color
     *  @param backgroundColor pass color value
     */
    public DialogSheet setBackgroundColor(@ColorInt int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    /**
     *  Method to set Background Color
     *  @param backgroundColorRes pass color id from res/color.xml
     */

    @SuppressWarnings("unused")
    public DialogSheet setBackgroundColorRes(@ColorRes int backgroundColorRes) {
        this.backgroundColor = ContextCompat.getColor(this.context, backgroundColorRes);
        return this;
    }

    /**
     *  Method to enable or disable cancel able feature of dialog sheet
     *  @param cancelable pass true/false
     */

    public DialogSheet setCancelable(boolean cancelable) {
        this.dialog.setCancelable(cancelable);
        return this;
    }


    /**
     *  Method to set custom View of you dialog sheet
     *  @param view pass an in initialised View object to display in the dialog sheet
     */
    public DialogSheet setView(View view) {

        this.messageContainer.addView(view);
        if (this.inflatedView == null) {
            this.inflatedView = view;
        }

        return this;
    }

    public void removePaddingAroundView() {


        mainDialogContainer.setPadding(0, 0, 0, 0);
    }


    /**
     *  Method to set custom View of dialog sheet
     *  @param layoutRes pass resource layout id of View
     */
    public DialogSheet setView(@LayoutRes int layoutRes) {
        this.inflatedView = View.inflate(this.context, layoutRes, null);
        this.setView(this.inflatedView);
        return this;
    }

    @SuppressWarnings("unused")
    public View getInflatedView() {
        return this.inflatedView;
    }

    /**
     *  Method show  dialog sheet
     *
     */
    public void show() {
        if (this.backgroundColor == -1) {
            this.backgroundColor = DialogUtils.getThemeBgColor(this.context);
        }

        if (this.backgroundColor != -1) {
           // mainDialogContainer.setBackgroundColor(this.backgroundColor);
            this.titleTextView.setTextColor(DialogUtils.getTextColor(this.backgroundColor));
            //this.messageTextView.setTextColor(DialogUtils.getTextColorSec(this.backgroundColor));
        }

        if (!this.showButtons) {
            this.textContainer.setPadding(0, 0, 0, 0);
        } else {
            int color;
            if (this.buttonColor != -1) {
                color = this.buttonColor;
            } else {
                color = DialogUtils.getThemeAccentColor(this.context);
            }

           // this.negativeButton.setTextColor(color);
           // DialogUtils.setButton(this.backgroundColor, color, this.positiveButton, true);
           // DialogUtils.setButton(this.backgroundColor, color, this.negativeButton, false);
           // this.positiveButton.setTextColor(DialogUtils.buttonTextColor(color));
        }

        this.dialog.show();
    }

    @SuppressWarnings("unused")
    public void dismiss() {
        if(this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
    }


    private void init(Context context) {
        this.dialog = new MyDialog(context, R.style.BottomDialogTheme);
        this.dialog.requestWindowFeature(1);
        this.dialog.setContentView(R.layout.layout_bottomdialog);
        Objects.requireNonNull(this.dialog.getWindow()).setLayout(-1, -2);
        this.dialog.getWindow().setGravity(80);
        this.titleTextView = this.dialog.findViewById(R.id.dialogTitle);
        this.messageTextView = this.dialog.findViewById(R.id.dialogMessage);
        this.iconImageView = this.dialog.findViewById(R.id.dialogIcon);
        this.positiveButton = this.dialog.findViewById(R.id.buttonPositive);
        this.negativeButton = this.dialog.findViewById(R.id.buttonNegative);
        this.textContainer = this.dialog.findViewById(R.id.textContainer);
        this.messageContainer = this.dialog.findViewById(R.id.messageContainer);
        this.mainDialogContainer = this.dialog.findViewById(R.id.mainDialogContainer);
        //this.closeBtn=this.dialog.findViewById(R.id.closeBtn);
        //this.closeBtn.setOnClickListener(view -> dismiss());

    }

    private void showIcon() {
        this.iconImageView.setVisibility(View.VISIBLE);
    }

    public interface OnNegativeClickListener {
        void onClick(View var1);
    }


    public interface OnPositiveClickListenerNotDissmisable {
        void onClick(View var1);
    }

    public interface OnPositiveClickListener {
        void onClick(View var1);
    }


    public class MyDialog extends Dialog {

        private MyOnCancelListener myOnCancelListener;

        public MyDialog(@NonNull Context context) {
            super(context);
        }

        MyDialog(@NonNull Context context, int themeResId) {
            super(context, themeResId);
        }

        protected MyDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }


        void setOnCancelListener(@Nullable MyOnCancelListener listener) {
            super.setOnCancelListener(dialogInterface -> {

            });
            this.myOnCancelListener = listener;
        }

        @Override
        public void cancel() {
            if (myOnCancelListener != null) {
                myOnCancelListener.onCancel(this);
            }

            myOnCancelListener = null;
            super.cancel();
        }
    }


   public interface MyOnCancelListener extends DialogInterface.OnCancelListener {


    }
}
