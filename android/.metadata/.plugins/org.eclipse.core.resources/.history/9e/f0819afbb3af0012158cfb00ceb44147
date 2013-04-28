package com.aware.aware;

import java.util.ArrayList;

import com.aware.aware.R;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

public class ListView extends ListActivity {
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> reports = new ArrayList<String>();

    //DEFINING STRING ADAPTER WHICH WILL HANDLE DATA OF LISTVIEW
    ArrayAdapter<String> adapter;

    //RECORDING HOW MUCH TIMES BUTTON WAS CLICKED
    int clickCounter=0;

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.list_layout);
        adapter=new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1,
            reports);
        setListAdapter(adapter);
    }

    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    public void addItems(View v) {
        reports.add("Clicked : "+clickCounter++);
        adapter.notifyDataSetChanged();
    }
}