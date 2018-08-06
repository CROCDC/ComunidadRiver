package com.river.comunidad.comunidadriver.CircleMenu;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.river.comunidad.comunidadriver.R;


public class CircleMenuActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getListView().setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{
        }));

    }



    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = null;
        if (position == 0) {
            //intent = new Intent(this, CircleActivity.class);
        } else {
            intent = new Intent(this, MainCircleActivity.class);
        }
        startActivity(intent);
    }


}
