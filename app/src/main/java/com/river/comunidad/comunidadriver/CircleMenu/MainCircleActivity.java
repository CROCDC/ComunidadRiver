package com.river.comunidad.comunidadriver.CircleMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.river.comunidad.comunidadriver.Controller.ContrellerApiPushNotification;
import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.Utils.Helper;
import com.river.comunidad.comunidadriver.View.Activitys.AccountActivity;
import com.river.comunidad.comunidadriver.View.Activitys.NoticiasActivity;
import com.river.comunidad.comunidadriver.View.Activitys.NoticiasPorCategoriaActivity;


public class MainCircleActivity extends AppCompatActivity {

    private CircleMenuLayout mCircleMenuLayout;
    private Toolbar toolbar;

    private Intent intent;
    private Bundle bundle;

    private int[] mItemImgs = new int[]{
            R.drawable.eventosjpg, R.drawable.actividadesjpg,
            R.drawable.sabiasquejpg, R.drawable.historiasdevidajpg};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maincircle);

        try {
            new ContrellerApiPushNotification().insertarToken();
        }catch (Exception e){
            Log.d(e.getMessage(),"notificaciones");
        }

        toolbar = findViewById(R.id.toolbarPrincipal_toolbar);
        mCircleMenuLayout = findViewById(R.id.id_menulayout);

        setSupportActionBar(toolbar);

        mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs);

        intent = new Intent(MainCircleActivity.this, NoticiasPorCategoriaActivity.class);
        bundle = new Bundle();

        mCircleMenuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {


            @Override
            public void itemClick(View view, int pos) {
                switch (mItemImgs[pos]) {
                    case R.drawable.historiasdevidajpg:
                        cargarCategoria(Helper.CATEGORIA_DEPORTE_Y_SOCIEDAD);
                        break;
                    case R.drawable.sabiasquejpg:
                        cargarCategoria(Helper.CATEGORIA_EFEMÉRIDES);
                        break;
                    case R.drawable.eventosjpg:
                        cargarCategoria(Helper.CATEGORIA_NUESTRO_CLUB);
                        break;
                    case R.drawable.actividadesjpg:
                        cargarCategoria(Helper.CATEGORIA_POR_LOS_QUINCHOS);
                        break;
                }
            }

            @Override
            public void itemCenterClick(View view) {
                cargarCategoria(Helper.CATEGORIA_NOTICIAS);
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

    public void cargarCategoria(Integer categoria) {
        bundle.putInt(NoticiasPorCategoriaActivity.CLAVE_CATEGORIA_RECIBIDA, categoria);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}