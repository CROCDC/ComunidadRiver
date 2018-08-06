package com.river.comunidad.comunidadriver.View.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.river.comunidad.comunidadriver.Controler.ControlerNoticias;
import com.river.comunidad.comunidadriver.Model.Model.Noticia;

import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.Utils.ResultListener;

import com.river.comunidad.comunidadriver.View.Adapters.ListaDeNoticiasAdapter;

import java.util.List;



public class NoticiasActivity extends AppCompatActivity {


    private ListaDeNoticiasAdapter listaDeNoticiasAdapter;
    private ShimmerRecyclerView shimmerRecyclerViewListaDeNotiicas;
    private CarouselLayoutManager layoutManager;

    private Boolean estaCargando = false;
    private ControlerNoticias controlerNoticias;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);

        toolbar = findViewById(R.id.toolbarPrincipal_toolbar);
        shimmerRecyclerViewListaDeNotiicas = findViewById(R.id.verticalViewPagerListaNoticias_activitynoticias);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listaDeNoticiasAdapter = new ListaDeNoticiasAdapter(new ListaDeNoticiasAdapter.NotificadorHaciaNoticiasActivity() {
            @Override
            public void notificarANoticiasActivity(Noticia noticia) {
                cargarDetalleDeLaNoticia(noticia);
            }
        });
        controlerNoticias = new ControlerNoticias();
        controlerNoticias.pedirListaDeNoticias(5,new ResultListener<List<Noticia>>() {
            @Override
            public void finish(List<Noticia> resultado) {
                listaDeNoticiasAdapter.setListaDeNoticias(resultado);
                shimmerRecyclerViewListaDeNotiicas.hideShimmerAdapter();
                estaCargando = false;
            }
        });


        layoutManager = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        shimmerRecyclerViewListaDeNotiicas.setHasFixedSize(true);
        shimmerRecyclerViewListaDeNotiicas.setLayoutManager(layoutManager);
        shimmerRecyclerViewListaDeNotiicas.addOnScrollListener(new CenterScrollListener());
        shimmerRecyclerViewListaDeNotiicas.setAdapter(listaDeNoticiasAdapter);
        shimmerRecyclerViewListaDeNotiicas.showShimmerAdapter();

        paginacion();
    }



    public void paginacion(){

        shimmerRecyclerViewListaDeNotiicas.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (estaCargando) {
                    return;
                }

                Integer posicionActual = layoutManager.getCenterItemPosition();
                Integer ultimaCelda = layoutManager.getItemCount();

                if (posicionActual >= (ultimaCelda - 3)) {
                    estaCargando = true;
                    controlerNoticias.pedirListaDeNoticias(5,new ResultListener<List<Noticia>>() {
                        @Override
                        public void finish(final List<Noticia> resultado) {

                            estaCargando = false;
                            listaDeNoticiasAdapter.agregarNoticiasALaLista(resultado);

                        }


                    });
                }
            }
        });
    }

    public void cargarDetalleDeLaNoticia(Noticia noticia){
        Intent intent = new Intent(NoticiasActivity.this,DetalleDeUnaNoticiaActivity.class);

        Bundle bundle = new Bundle();

        bundle.putSerializable(DetalleDeUnaNoticiaActivity.CLAVE_NOTICIA,noticia);

        intent.putExtras(bundle);

        startActivity(intent);
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
                NoticiasActivity.this.onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);

    }

}
