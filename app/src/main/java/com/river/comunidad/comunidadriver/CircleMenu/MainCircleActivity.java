package com.river.comunidad.comunidadriver.CircleMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.View.Activitys.AccountActivity;
import com.river.comunidad.comunidadriver.View.Activitys.NoticiasActivity;


public class MainCircleActivity extends AppCompatActivity {

    private CircleMenuLayout mCircleMenuLayout;
    private Toolbar toolbar;

    private int[] mItemImgs = new int[]{
            R.drawable.eventosjpg, R.drawable.actividadesjpg,
            R.drawable.sabiasquejpg, R.drawable.historiasdevidajpg};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maincircle);

        toolbar = findViewById(R.id.toolbarPrincipal_toolbar);
        mCircleMenuLayout = findViewById(R.id.id_menulayout);

        setSupportActionBar(toolbar);


        mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs);

        mCircleMenuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {

            @Override
            public void itemClick(View view, int pos) {
                Toast.makeText(MainCircleActivity.this, mItemImgs[pos], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void itemCenterClick(View view) {
                startActivity(new Intent(MainCircleActivity.this, NoticiasActivity.class));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu_principal, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.opcionCuenta:
                startActivity(new Intent(MainCircleActivity.this, AccountActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}