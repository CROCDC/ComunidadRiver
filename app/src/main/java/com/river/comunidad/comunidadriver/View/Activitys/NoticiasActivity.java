package com.river.comunidad.comunidadriver.View.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.river.comunidad.comunidadriver.Controller.ControllerNoticiaRetrofit;
import com.river.comunidad.comunidadriver.Model.Models.ListadoDeNoticias;
import com.river.comunidad.comunidadriver.Model.Models.Noticia;

import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.Utils.ResultListener;

import com.river.comunidad.comunidadriver.View.Adapters.ListaDeNoticiasEnVerticalAdapter;

import java.util.List;


public class NoticiasActivity extends AppCompatActivity {


    private ListaDeNoticiasEnVerticalAdapter listaDeNoticiasEnVerticalAdapter;
    private ShimmerRecyclerView shimmerRecyclerViewListaDeNoticias;
    private CarouselLayoutManager layoutManager;

    private Boolean estaCargando = false;
    private ControllerNoticiaRetrofit controllerNoticiaRetrofit;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private ListadoDeNoticias listadoDeNoticias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);

        progressBar = findViewById(R.id.progressBar_activitynoticias);
        toolbar = findViewById(R.id.toolbarPrincipal_toolbar);
        shimmerRecyclerViewListaDeNoticias = findViewById(R.id.verticalViewPagerListaNoticias_activitynoticias);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        listaDeNoticiasEnVerticalAdapter = new ListaDeNoticiasEnVerticalAdapter(new ListaDeNoticiasEnVerticalAdapter.NotificadorHaciaActivity() {
            @Override
            public void notificarAActivityClickItewView(ListadoDeNoticias listadoDeNoticias, Integer posicionActual) {
                cargarDetalleDeLaNoticia(listadoDeNoticias, posicionActual);
            }
        });
        controllerNoticiaRetrofit = new ControllerNoticiaRetrofit(this);
        controllerNoticiaRetrofit.pedirListaDeNoticias(5, new ResultListener<List<Noticia>>() {
            @Override
            public void finish(List<Noticia> resultado) {
                listaDeNoticiasEnVerticalAdapter.setListaDeNoticias(resultado);
                listadoDeNoticias = new ListadoDeNoticias(resultado);
                shimmerRecyclerViewListaDeNoticias.hideShimmerAdapter();
                estaCargando = false;
            }
        });


        layoutManager = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        shimmerRecyclerViewListaDeNoticias.setHasFixedSize(true);
        shimmerRecyclerViewListaDeNoticias.setLayoutManager(layoutManager);
        shimmerRecyclerViewListaDeNoticias.addOnScrollListener(new CenterScrollListener());
        shimmerRecyclerViewListaDeNoticias.setAdapter(listaDeNoticiasEnVerticalAdapter);
        shimmerRecyclerViewListaDeNoticias.showShimmerAdapter();

        paginacion();
    }


    public void paginacion() {

        shimmerRecyclerViewListaDeNoticias.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (estaCargando) {
                    return;
                }

                Integer posicionActual = layoutManager.getCenterItemPosition();
                Integer ultimaCelda = layoutManager.getItemCount();

                if (posicionActual != -1) {
                    if (posicionActual >= (ultimaCelda - 2)) {
                        estaCargando = true;
                        progressBar.setVisibility(View.VISIBLE);
                        controllerNoticiaRetrofit.pedirListaDeNoticias(5, new ResultListener<List<Noticia>>() {
                            @Override
                            public void finish(final List<Noticia> resultado) {
                                progressBar.setVisibility(View.GONE);
                                estaCargando = false;
                                listaDeNoticiasEnVerticalAdapter.agregarNoticiasALaLista(resultado);

                            }


                        });
                    }
                }

            }
        });
    }

    public void cargarDetalleDeLaNoticia(ListadoDeNoticias listadoDeNoticias, Integer posicionActual) {
        Intent intent = new Intent(NoticiasActivity.this, DetalleDeUnaNoticiaActivity.class);

        Bundle bundle = new Bundle();

        bundle.putSerializable(DetalleDeUnaNoticiaActivity.CLAVE_LISTADODENOTICIAS, listadoDeNoticias);
        bundle.putInt(DetalleDeUnaNoticiaActivity.CLAVE_POSICION, posicionActual);

        intent.putExtras(bundle);

        startActivity(intent);
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
                Intent intent = new Intent(NoticiasActivity.this,AccountActivity.class);
                startActivity(intent);
                break;
            case 16908332:
                NoticiasActivity.this.onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);

    }

}
