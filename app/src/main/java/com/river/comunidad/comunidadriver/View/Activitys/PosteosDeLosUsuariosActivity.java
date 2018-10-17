package com.river.comunidad.comunidadriver.View.Activitys;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.river.comunidad.comunidadriver.Controller.ControllerPosteosFirebase;
import com.river.comunidad.comunidadriver.Model.Firebase.Posteo;
import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.Utils.ResultListener;
import com.river.comunidad.comunidadriver.View.Adapters.ListaDePosteosAdapter;

import java.util.List;

public class PosteosDeLosUsuariosActivity extends AppCompatActivity {

    private RecyclerView recyclerViewListaDePosteos;
    private ListaDePosteosAdapter listaDePosteosAdapter;
    private FloatingActionButton floatingActionButtonSubirPosteo;
    private Toolbar toolbar;

    public static final String CLAVE_POSTEO = "posteo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posteos_de_los_usuarios);

        pedirListaDePosteos();

        listaDePosteosAdapter = new ListaDePosteosAdapter();



        toolbar = findViewById(R.id.toolbarPrincipal_toolbar);
        recyclerViewListaDePosteos = findViewById(R.id.recyclerViewListDePosteosDeLosUsuarios_activityposteosdelosusuarios);
        floatingActionButtonSubirPosteo = findViewById(R.id.floatingButtonSubirPosteo);

        setSupportActionBar(toolbar);

        recyclerViewListaDePosteos.setAdapter(listaDePosteosAdapter);

        recyclerViewListaDePosteos.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));

        floatingActionButtonSubirPosteo.setOnClickListener(new TouchFloatingActionButtonSubirPosteo());

    }


    public void pedirListaDePosteos(){
        new ControllerPosteosFirebase().obtenerPosteosDeLosUsuarios(new ResultListener<List<Posteo>>() {
            @Override
            public void finish(List<Posteo> resultado) {
                listaDePosteosAdapter.setListaDePosteos(resultado);
            }
        });
    }

    public class TouchFloatingActionButtonSubirPosteo implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            startActivity(new Intent(PosteosDeLosUsuariosActivity.this,CrearPosteoActivity.class));

        }
    }

}
