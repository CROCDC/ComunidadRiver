package com.river.comunidad.comunidadriver.View.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.river.comunidad.comunidadriver.Model.Models.ListadoDeNoticias;
import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.View.Adapters.ListaDeNoticiasEnVerticalAdapter;

public class NoticiasGuardadasActivity extends AppCompatActivity {

    private ShimmerRecyclerView shimmerRecyclerViewListaDeNoticiasGuardadas;
    private ListaDeNoticiasEnVerticalAdapter listaDeNoticiasEnVerticalAdapter;
    private CarouselLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias_guardadas);

        shimmerRecyclerViewListaDeNoticiasGuardadas = findViewById(R.id.recyclerViewLisDeNoticiasGuardadas_activitynoticiasguardadas);

        listaDeNoticiasEnVerticalAdapter = new ListaDeNoticiasEnVerticalAdapter(new ListaDeNoticiasEnVerticalAdapter.NotificadorHaciaActivity() {
            @Override
            public void notificarAActivityClickItewView(ListadoDeNoticias listadoDeNoticias, Integer posicionActual) {

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
}
