package com.ceq.app_core.utils.extend.picker.interfaces.callback;

/**
 * Created by Administrator on 2017/1/14.
 */

public interface  Inter_Picker_Callback {
     interface OnNumberPickListener {
        void onNumberPicked(int index, Number item);

    }
     interface  OnAddressPickListener {
           void onAddressPicked(String province, String city, String county);

    }
     interface OnYearMonthDayTimePickListener  {
        void onDateTimePicked(String year, String month, String day, String hour, String minute);

    }
     interface OnYearMonthDayPickListener  {
         void onDatePicked(String year, String month, String day);

    }
     interface OnOptionPickListener  {
         void onOptionPicked(int index, String item);
    }
}