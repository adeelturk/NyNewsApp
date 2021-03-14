package com.base.common.customView.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.base.common.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Adeel Ur Rehman Turk
 *
 * */

/**
 * This class is a utility Dialog which helps to show a Material styled date picker,
 * use [getInstance] method to get its instance and show the picker. DatePickerFragment will return the selected date in given format
 * through [onActivityResult] method of targeted Fragment
 *  Following are the properties which you can pass in [getInstance]
 *  [DATE_FORMAT] pass the date format which you will use to pass in and get date out of DatePickerFragment for example dd/mm/yyyy
 *  [CURRENT_SELECTED_DATE] pass the date already selected to show it as selected on calendar
 *  [SEPARATOR] Define the icon which separates the date int to day month and year for example a backslash '/' because your date format is dd/mm/yyyy
 *
 */

@SuppressWarnings("unused")
public class DatePickerFragment extends BaseDialogFragment {

    private static final String CURRENT_SELECTED_DATE = "selectedDate";
    private static final String DATE_FORMAT = "dateFormat";
    private static final String SEPARATOR = "separator";
    private static final String DEFAULT_DATE_LIMIT = "default_date_limit";

    private String format;

    /**
     *
     *  @param selectedDate pass the date already selected to show it as selected on calendar
     *  @param requestCode pass the date already selected to show it as selected on calendar
     *  @param targetedFragment Set the target fragment to pass on the selected date.
     *  @param format pass the date format which will be used to pass in and get date out of DatePickerFragment for example dd/mm/yyyy
     *  @param separator Pass the character which separates the date int to day month and year for example a backslash '/' because your date format is dd/mm/yyyy
     *  @param dateYearLimit pass 0 or -1 for now
     * @return DatePickerFragment  instance with constraints to show up in UI.
     */

    public static DatePickerFragment getInstance(@Nullable String selectedDate,
                                                 int requestCode,
                                                 Fragment targetedFragment, String format,
                                                 String separator,
                                                 int dateYearLimit) {

        DatePickerFragment datePickerFragment = new DatePickerFragment();
        Bundle b = new Bundle();
        b.putString(DatePickerFragment.CURRENT_SELECTED_DATE,
                selectedDate);
        b.putString(DatePickerFragment.DATE_FORMAT,
                format);

        b.putString(DatePickerFragment.SEPARATOR,
                separator);
        if (dateYearLimit <= 0) {
            b.putInt(DatePickerFragment.DEFAULT_DATE_LIMIT,
                    0);
        } else {

            b.putInt(DatePickerFragment.DEFAULT_DATE_LIMIT,
                    dateYearLimit);
        }

        datePickerFragment.setArguments(b);
        datePickerFragment.setTargetFragment(targetedFragment, requestCode);
        return datePickerFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.empty_black, container, false);

        String selectedDate = requireArguments().getString(CURRENT_SELECTED_DATE);
        format = getArguments().getString(DATE_FORMAT);
        String separator = getArguments().getString(SEPARATOR);

        Calendar calendar = Calendar.getInstance();
        int dateYearLimit = getArguments().getInt(DEFAULT_DATE_LIMIT, 0);
        int year = calendar.get(Calendar.YEAR) - dateYearLimit;

        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        if (selectedDate==null||selectedDate.isEmpty()) {
            selectedDate = getFormattedDate(day , month , year );
        }

        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            Date date = simpleDateFormat.parse(selectedDate);
            calendar.setTime(Objects.requireNonNull(date));
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);


        } catch (Exception e) {

            e.printStackTrace();
            Toast.makeText(getActivity(), getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
        }


        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select");
        builder.setSelection(calendar.getTimeInMillis());

        // builder.setCalendarConstraints(CalendarConstraints.)
        final MaterialDatePicker materialDatePicker = builder.build();

        materialDatePicker.addOnPositiveButtonClickListener(selection -> {

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(((Long) selection).longValue());
            onDateSet(cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH));

            System.out.println("");


        });

        materialDatePicker.addOnDismissListener(dialogInterface -> {


            DatePickerFragment.this.dismiss();

        });

                materialDatePicker.show(requireFragmentManager(), "datepicekr");
        return v;
    }


    /**
     *  pass date String in a preset format to get Date Object
     *  @param dateStr selected date year.
     *
     */
    private Date getDateObject(String dateStr) throws Exception {
        Calendar cal = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = simpleDateFormat.parse(dateStr);
        cal.setTime(Objects.requireNonNull(date));

        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    /*private static long getMinDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.MONTH, -11);
        return cal.getTime().getTime();
    }*/

    /**
     *  pass year , month and day to set result for targeted fragment.
     *  @param year selected date year.
     *  @param monthOfYear selected date Month.
     *  @param  dayOfMonth selected date day.
     */

    private void onDateSet(int year, int monthOfYear, int dayOfMonth) {
        Intent intent = new Intent();
        //yyyy-MM-dd
        //monthOfYear = monthOfYear + 1;
        String day = String.valueOf(dayOfMonth);
        String month = String.valueOf(monthOfYear);
        if (day.length() == 1) {

            day = "0" + dayOfMonth;
        }
        if (month.length() == 1) {

            month = "0" + monthOfYear;
        }
        String dateRes = getFormattedDate(Integer.parseInt(day), Integer.parseInt(month), year);
        intent.putExtra("date", dateRes);
        Objects.requireNonNull(getTargetFragment()).onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        dismiss();

    }

    /**
     *  pass year , month and day to get date in a preset format.
     *  @param year selected date year.
     *  @param month selected date Month.
     *  @param  year selected date day.
     *
     */

    private String getFormattedDate(int day, int month, int year) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat(format);
        return df.format(cal.getTime());
    }

    /**
     *  Get current time stamp in a give date format
     *  @param format date format.
     *
     */

    private static String getCurrentTimeStamp(String format) {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }

}
