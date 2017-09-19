package com.ceq.app_core.utils.core;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/23.
 */
public class Util_View {
    public interface ViewHolder {

    }
    public static void viewOnClick(View.OnClickListener listener, View... view) {
        for (View v : view)
            v.setOnClickListener(listener);
    }

    public static void viewSetTextColor(int color, TextView... textViews) {
        for (TextView textView : textViews)
            textView.setTextColor(color);
    }

    public interface ListViewData<T> {
        List<T> initData(List<T> al);
    }

    public static class AbsListViewAdapter<T> extends BaseAdapter {
        List<T> List;
        Context c;
        InitAbsListView<T> initAbsListView;
        int layoutId;

        public AbsListViewAdapter(Context c, int layoutId,
                                  ListViewData listViewData,
                                  InitAbsListView<T> initAbsListView) {
            super();
            this.List = listViewData.initData(new ArrayList<T>());
            this.c = c;
            this.initAbsListView = initAbsListView;
            this.layoutId = layoutId;
        }

        @Override
        public int getCount() {
            return List.size();
        }

        @Override
        public Object getItem(int position) {
            return List.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public interface InitAbsListView<T> {

            ViewHolder initAbsListView(View convertView);

            void setAbsListView(ViewHolder vh, List<T> al, int position);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if (convertView == null) {
                convertView = LayoutInflater.from(c).inflate(layoutId, parent, false);
                vh = initAbsListView.initAbsListView(convertView);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            if (List != null)
                initAbsListView.setAbsListView(vh, List, position);
            return convertView;
        }

    }

    public static BaseAdapter absListViewBindAdapter(AbsListView absListview,
                                                     AbsListViewAdapter adapter) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            absListview.setAdapter(adapter);
        }
        return adapter;
    }


}
