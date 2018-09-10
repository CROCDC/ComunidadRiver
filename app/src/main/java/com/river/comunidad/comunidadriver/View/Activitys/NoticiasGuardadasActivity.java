package com.river.comunidad.comunidadriver.View.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.river.comunidad.comunidadriver.Controller.ControllerNoticiaFirebase;
import com.river.comunidad.comunidadriver.Model.Models.ListadoDeNoticias;
import com.river.comunidad.comunidadriver.Model.Models.Noticia;
import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.Utils.ResultListener;
import com.river.comunidad.comunidadriver.View.Adapters.ListaDeNoticiasEnVerticalAdapter;

import java.util.List;

public class NoticiasGuardadasActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private ShimmerRecyclerView shimmerRecyclerViewListaDeNoticiasGuardadas;
    private ListaDeNoticiasEnVerticalAdapter listaDeNoticiasEnVerticalAdapter;
    private CarouselLayoutManager layoutManager;
    private ControllerNoticiaFirebase controllerNoticiaFirebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias_guardadas);

        controllerNoticiaFirebase = new ControllerNoticiaFirebase(getApplicationContext());

        shimmerRecyclerViewListaDeNoticiasGuardadas = findViewById(R.id.recyclerViewLisDeNoticiasGuardadas_activitynoticiasguardadas);
        toolbar = findViewById(R.id.toolbarPrincipal_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listaDeNoticiasEnVerticalAdapter = new ListaDeNoticiasEnVerticalAdapter(new ListaDeNoticiasEnVerticalAdapter.NotificadorHaciaActivity() {
            @Override
            public void notificarAActivityClickItewView(ListadoDeNoticias listadoDeNoticias, Integer posicionActual) {
                cargarDetalleDeLaNoticia(listadoDeNoticias, posicionActual);
            }
        });

        controllerNoticiaFirebase.pedirListaDeNoticiasGuardadasDelUsuario( new ResultListener<List<Noticia>>() {
            @Override
            public void finish(List<Noticia> resultado) {
                listaDeNoticiasEnVerticalAdapter.setListaDeNoticias(resultado);
                shimmerRecyclerViewListaDeNoticiasGuardadas.hideShimmerAdapter();
            }
        });


        layoutManager = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        shimmerRecyclerViewListaDeNoticiasGuardadas.setHasFixedSize(true);
        shimmerRecyclerViewListaDeNoticiasGuardadas.setLayoutManager(layoutManager);
        shimmerRecyclerViewListaDeNoticiasGuardadas.addOnScrollListener(new CenterScrollListener());
        shimmerRecyclerViewListaDeNoticiasGuardadas.setAdapter(listaDeNoticiasEnVerticalAdapter);
        shimmerRecyclerViewListaDeNoticiasGuardadas.showShimmerAdapter();

    }

    public void cargarDetalleDeLaNoticia(ListadoDeNoticias listadoDeNoticias, Integer posicionActual) {
        Intent intent = new Intent(NoticiasGuardadasActivity.this, DetalleDeUnaNoticiaActivity.class);

        Bundle bundle = new Bundle();

        bundle.putSerializable(DetalleDeUnaNoticiaActivity.CLAVE_LISTADODENOTICIAS, listadoDeNoticias);
        bundle.putInt(DetalleDeUnaNoticiaActivity.CLAVE_POSICION, posicionActual);

        intent.putExtras(bundle);

        startActivity(intent);
    }
}
