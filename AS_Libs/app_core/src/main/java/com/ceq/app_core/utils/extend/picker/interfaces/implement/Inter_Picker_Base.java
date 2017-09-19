package com.ceq.app_core.utils.extend.picker.interfaces.implement;


import android.app.Activity;

import com.ceq.app_core.utils.extend.picker.interfaces.callback.Inter_Picker_Callback;

import java.util.Date;

/**
 * Created by Administrator on 2017/1/14.
 */

public interface Inter_Picker_Base {
    void useNumberPicker(Activity activity, String title, int rangeStart, int rangeEnd, int rangeStep, int defaultNumber, String suffix, Inter_Picker_Callback.OnNumberPickListener onNumberPickListener);

    void useCityPicker(Activity activity, String title, String defaultProvince, String defaultCity, String defaultCountry, boolean isHiddenProvince, boolean isHiddenCountry, final Inter_Picker_Callback.OnAddressPickListener onAddressPickListener);

    void useDateTimePicker(Activity activity, String title, int rangeStartYear, int rangeEndYear, Date defaultDatetime, final Inter_Picker_Callback.OnYearMonthDayTimePickListener onYearMonthDayTimePickListener);

    void useDatePicker(Activity activity, String title, int rangeStartYear, int rangeEndYear, Date defaultDatetime, final Inter_Picker_Callback.OnYearMonthDayPickListener onYearMonthDayPickListener);

    void   useOptionPicker(Activity activity, String title,int defaultIndex, String[] data, final Inter_Picker_Callback.OnOptionPickListener onOptionPickListener);
    String[] getValue();
}
