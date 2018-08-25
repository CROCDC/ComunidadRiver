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
import com.river.comunidad.comunidadriver.Utils.Helper;
import com.river.comunidad.comunidadriver.View.Activitys.AccountActivity;
import com.river.comunidad.comunidadriver.View.Activitys.NoticiasActivity;
import com.river.comunidad.comunidadriver.View.Activitys.NoticiasPorCategoriaActivity;


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
            Intent intent = new Intent(MainCircleActivity.this, NoticiasPorCategoriaActivity.class);
            Bundle bundle = new Bundle();

            @Override
            public void itemClick(View view, int pos) {
                switch (mItemImgs[pos]){
                    case R.drawable.historiasdevidajpg:
                        bundle.putInt(NoticiasPorCategoriaActivity.CLAVE_CATEGORIA_RECIBIDA, Helper.CATEGORIA_DEPORTE_Y_SOCIEDAD);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;

                }
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