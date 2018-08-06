package com.river.comunidad.comunidadriver.View.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.river.comunidad.comunidadriver.Model.Model.Noticia;
import com.river.comunidad.comunidadriver.R;

public class DetalleDeUnaNoticiaActivity extends AppCompatActivity {

    public static final String CLAVE_NOTICIA = "noticia";

    private Noticia noticia;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_de_una_noticia);

        toolbar = findViewById(R.id.toolbarPrincipal_toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        noticia = (Noticia) bundle.getSerializable(CLAVE_NOTICIA);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu_principal,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.opcionCuenta:
                Toast toast = Toast.makeText(this, "account activity", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                toast.show();
                break;
            case 16908332:
                DetalleDeUnaNoticiaActivity.this.onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);

    }
}
