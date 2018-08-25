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
import com.river.comunidad.comunidadriver.Controler.ControlerNoticiaRetrofit;
import com.river.comunidad.comunidadriver.Model.Models.ListadoDeNoticias;
import com.river.comunidad.comunidadriver.Model.Models.Noticia;
import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.Utils.ResultListener;
import com.river.comunidad.comunidadriver.View.Adapters.ListaDeNoticiasEnVerticalAdapter;

import java.util.List;

public class NoticiasPorCategoriaActivity extends AppCompatActivity {

    public static final String CLAVE_CATEGORIA_RECIBIDA = " categoria";

    private Integer categoriaRecibida;

    private ListaDeNoticiasEnVerticalAdapter listaDeNoticiasEnVerticalAdapter;
    private ShimmerRecyclerView shimmerRecyclerViewListaDeNoticias;
    private CarouselLayoutManager layoutManager;

    private ControlerNoticiaRetrofit controlerNoticiaRetrofit;

    private Toolbar toolbar;
    private ProgressBar progressBar;

    private List<Noticia> listaDeNoticias;

    private Boolean estaCargando = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias_por_categoria);

        toolbar = findViewById(R.id.toolbarPrincipal_toolbar);
        progressBar = findViewById(R.id.progressBar_activitynoticiasporcategoria);
        shimmerRecyclerViewListaDeNoticias = findViewById(R.id.recyclerViewListaNoticias_activitynoticiasporcategoria);

        Intent intentRecicibo = getIntent();
        Bundle bundle = intentRecicibo.getExtras();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        controlerNoticiaRetrofit = new ControlerNoticiaRetrofit(getApplicationContext());

        categoriaRecibida = bundle.getInt(CLAVE_CATEGORIA_RECIBIDA);

        listaDeNoticiasEnVerticalAdapter = new ListaDeNoticiasEnVerticalAdapter(new ListaDeNoticiasEnVerticalAdapter.NotificadorHaciaActivity() {
            @Override
            public void notificarAActivityClickItewView(ListadoDeNoticias listadoDeNoticias, Integer posicionActual) {
                cargarDetalleDeLaNoticia(listadoDeNoticias, posicionActual);
            }
        });
        controlerNoticiaRetrofit.pedirListaDeNoticiasPorCategoria(new ResultListener<List<Noticia>>() {
            @Override
            public void finish(List<Noticia> resultado) {
                listaDeNoticiasEnVerticalAdapter.setListaDeNoticias(resultado);
                shimmerRecyclerViewListaDeNoticias.hideShimmerAdapter();
                listaDeNoticias = resultado;
                estaCargando = false;

            }
        }, categoriaRecibida);


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
                        controlerNoticiaRetrofit.pedirListaDeNoticiasPorCategoria(new ResultListener<List<Noticia>>() {
                            @Override
                            public void finish(List<Noticia> resultado) {
                                progressBar.setVisibility(View.GONE);
                                estaCargando = false;
                                listaDeNoticiasEnVerticalAdapter.agregarNoticiasALaLista(resultado);
                            }
                        }, categoriaRecibida);
                    }
                }

            }
        });
    }

    public void cargarDetalleDeLaNoticia(ListadoDeNoticias listadoDeNoticias, Integer posicionActual) {
        Intent intent = new Intent(NoticiasPorCategoriaActivity.this, DetalleDeUnaNoticiaActivity.class);

        Bundle bundle = new Bundle();

        bundle.putSerializable(DetalleDeUnaNoticiaActivity.CLAVE_LISTADENOTICIAS, listadoDeNoticias);
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
                Intent intent = new Intent(NoticiasPorCategoriaActivity.this,AccountActivity.class);
                startActivity(intent);
                break;
            case 16908332:
                NoticiasPorCategoriaActivity.this.onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);

    }
}
