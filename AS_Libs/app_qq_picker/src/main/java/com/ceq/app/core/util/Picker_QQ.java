package com.ceq.app.core.util;


import android.app.Activity;

import com.ceq.app_core.framework.Framework_App;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.extend.picker.abstracts.Util_Picker;
import com.ceq.app_core.utils.extend.picker.interfaces.callback.Inter_Picker_Callback;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DateTimePicker;
import cn.qqtheme.framework.picker.NumberPicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.util.ConvertUtils;

import static com.alibaba.fastjson.JSON.parseArray;

/**
 * Created by Administrator on 2017/1/11.
 */

public class Picker_QQ extends Util_Picker {
    private String[] value;

    @Override
    public void useNumberPicker(Activity activity, String title, int rangeStart, int rangeEnd, int rangeStep, int defaultNumber, String suffix, final Inter_Picker_Callback.OnNumberPickListener onNumberPickListener) {
        cn.qqtheme.framework.picker.NumberPicker picker = new cn.qqtheme.framework.picker.NumberPicker(activity);
        picker.setTitleText(title);
        picker.setLineVisible(false);
        picker.setOffset(2);//偏移量
        picker.setRange(rangeStart, rangeEnd, rangeStep);//数字范围
        picker.setSelectedItem(defaultNumber);
        picker.setLabel(suffix);
        picker.setOnNumberPickListener(new NumberPicker.OnNumberPickListener() {
            @Override
            public void onNumberPicked(int index, Number item) {
                onNumberPickListener.onNumberPicked(index, item);
            }
        });
        picker.show();
    }

    @Override
    public void useCityPicker(Activity activity, String title, String defaultProvince, String defaultCity, String defaultCountry, boolean isHiddenProvince, boolean isHiddenCountry, final Inter_Picker_Callback.OnAddressPickListener onAddressPickListener) {
        ArrayList<Province> provinceList = new ArrayList<Province>();
        String json = null;
        try {
            json = ConvertUtils.toString(Framework_App.getInstance().getAssets().open("city.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        provinceList.addAll(parseArray(json, Province.class));
        AddressPicker picker = new AddressPicker(activity, provinceList);
        picker.setTitleText(title);
        picker.setHideProvince(isHiddenProvince);
        picker.setHideCounty(isHiddenCountry);
        if (!Util_Empty.isEmpty(defaultProvince) && !Util_Empty.isEmpty(defaultCity) && !Util_Empty.isEmpty(defaultCountry))
            picker.setSelectedItem(defaultProvince, defaultCity, defaultCountry);
        picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
            @Override
            public void onAddressPicked(Province province, City city, County county) {
                onAddressPickListener.onAddressPicked(
                        province == null ? null : province.getAreaName(), city == null ? null : city.getAreaName(), county == null ? null : county.getAreaName());
            }
        });
        picker.show();
    }

    @Override
    public void useDateTimePicker(Activity activity, String title, int rangeStartYear, int rangeEndYear, Date defaultDatetime, final Inter_Picker_Callback.OnYearMonthDayTimePickListener onYearMonthDayTimePickListener) {
        DateTimePicker picker = new DateTimePicker(activity, DateTimePicker.HOUR_OF_DAY);
        picker.setRange(rangeStartYear, rangeEndYear);
        picker.setTitleText(title);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.format(defaultDatetime);
        Calendar calendar = simpleDateFormat.getCalendar();
        Util_Log.logTest(defaultDatetime, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        picker.setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                onYearMonthDayTimePickListener.onDateTimePicked(year, (String) Util_Empty.isEmptyToReplace(month, "0"), (String) Util_Empty.isEmptyToReplace(day, "0"), (String) Util_Empty.isEmptyToReplace(hour, "0"), (String) Util_Empty.isEmptyToReplace(minute, "0"));
            }
        });
        picker.show();
    }

    @Override
    public void useDatePicker(Activity activity, String title, int rangeStartYear, int rangeEndYear, Date defaultDatetime, final Inter_Picker_Callback.OnYearMonthDayPickListener onYearMonthDayPickListener) {
        DatePicker picker = new DatePicker(activity);
        picker.setTopPadding(2);
        picker.setTitleText(title);
        picker.setRangeStart(rangeStartYear, 1, 1);
        picker.setRangeEnd(rangeEndYear, 12, 31);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.format(defaultDatetime);
        Calendar calendar = simpleDateFormat.getCalendar();
        picker.setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                setValue(new String[]{year, month, day});
                onYearMonthDayPickListener.onDatePicked(year, (String) Util_Empty.isEmptyToReplace(month, "0"), (String) Util_Empty.isEmptyToReplace(day, "0"));
            }
        });
        picker.show();
    }

    @Override
    public void useOptionPicker(Activity activity, String title, int defaultIndex, String[] data, final Inter_Picker_Callback.OnOptionPickListener onOptionPickListener) {
        OptionPicker picker = new OptionPicker(activity, data);
        picker.setSelectedIndex(defaultIndex);
        picker.setTitleText(title);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                onOptionPickListener.onOptionPicked(index, item);
            }
        });
        picker.show();
    }

    @Override
    public String[] getValue() {
        return value;
    }

    public void setValue(String[] value) {
        this.value = value;
    }

    @Override
    public void init(Object... initParams) {

    }
}
