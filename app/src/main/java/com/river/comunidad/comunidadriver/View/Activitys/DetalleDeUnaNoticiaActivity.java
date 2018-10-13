package com.river.comunidad.comunidadriver.View.Activitys;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.river.comunidad.comunidadriver.Controller.ControllerNoticiaFirebase;
import com.river.comunidad.comunidadriver.Model.API_REST.ListadoDeNoticias;
import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.Utils.CubeTransformer;
import com.river.comunidad.comunidadriver.Utils.ResultListener;
import com.river.comunidad.comunidadriver.View.Adapters.ViewPagerAdaperDetalleNoticia;
import com.river.comunidad.comunidadriver.View.Fragments.ComentariosDeLaNoticiaFragment;
import com.river.comunidad.comunidadriver.View.Fragments.FragmentsViewPager.DetalleDeUnaNoticiaFragment;
import com.river.comunidad.comunidadriver.View.Fragments.ImagenDeLaNoticiaFragment;
import com.shashank.sony.fancytoastlib.FancyToast;

public class DetalleDeUnaNoticiaActivity extends AppCompatActivity implements ComentariosDeLaNoticiaFragment.NotificadorHaciaDetalleDeUnaNoticiaActivity, DetalleDeUnaNoticiaFragment.NotificadorHaciaDetalleDeUnaNoticiaActivity {

    public static final String CLAVE_LISTADODENOTICIAS = "lista de noticias";
    public static final String CLAVE_POSICION = "posicion noticia actual";

    private ListadoDeNoticias listadoDeNoticias;
    private Integer posicionActual;

    private Toolbar toolbar;

    private FrameLayout frameLayoutContenedor;
    private ViewPager viewPagerListaDeNoticias;
    private ViewPagerAdaperDetalleNoticia viewPagerAdaperDetalleNoticia;

    ImagenDeLaNoticiaFragment imagenDeLaNoticiaFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_de_una_noticia);

        frameLayoutContenedor = findViewById(R.id.frameLayoutContenedor);
        toolbar = findViewById(R.id.toolbarPrincipal_toolbar);
        viewPagerListaDeNoticias = findViewById(R.id.viewPagerListaDeNoticias_activitydetalledeunanoticia);


        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("");

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        listadoDeNoticias = (ListadoDeNoticias) bundle.getSerializable(CLAVE_LISTADODENOTICIAS);

        posicionActual = bundle.getInt(CLAVE_POSICION);
        viewPagerAdaperDetalleNoticia = new ViewPagerAdaperDetalleNoticia(getSupportFragmentManager(), listadoDeNoticias.getArray());

        viewPagerListaDeNoticias.setAdapter(viewPagerAdaperDetalleNoticia);

        viewPagerListaDeNoticias.setCurrentItem(posicionActual);

        viewPagerListaDeNoticias.setPageTransformer(true,new CubeTransformer());

        informacionScrollViewPager();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu_noticiadetalle, menu);

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        imagenDeLaNoticiaFragment = null;
        viewPagerListaDeNoticias.setVisibility(View.VISIBLE);
        frameLayoutContenedor.setBackgroundColor(Color.TRANSPARENT);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 16908332:
                DetalleDeUnaNoticiaActivity.this.onBackPressed();
                break;
            case R.id.opcionCompartir:
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_SUBJECT, "Compartir");
                share.putExtra(Intent.EXTRA_TEXT, "Mira esta noticia me gusta mucho  " + listadoDeNoticias.getArray().get(viewPagerListaDeNoticias.getCurrentItem()).getLink());
                startActivity(Intent.createChooser(share, "Compartir en"));
                break;

            case R.id.opcionLike:

                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    final ControllerNoticiaFirebase controllerNoticiaFirebase = new ControllerNoticiaFirebase(getBaseContext());

                    controllerNoticiaFirebase.verficiarSiLaNoticiaEstaEnFirebase(listadoDeNoticias.getArray().get(viewPagerListaDeNoticias.getCurrentItem()), new ResultListener<Boolean>() {
                        @Override
                        public void finish(Boolean resultado) {
                            if (resultado) {

                                controllerNoticiaFirebase.agregarLaNoticiaAGuardado(listadoDeNoticias.getArray().get(viewPagerListaDeNoticias.getCurrentItem()), new ResultListener<Boolean>() {
                                    @Override
                                    public void finish(Boolean resultado) {
                                        if (resultado) {
                                            FancyToast.makeText(getApplicationContext(), " la Noticia a sido Guardada", Toast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                        } else {
                                            FancyToast.makeText(getApplicationContext(), "Ocurrio un error por favor reintente mas tarde", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                                        }
                                    }
                                });

                            } else {
                                FancyToast.makeText(getApplicationContext(), "La Noticia ya esta guardada ", Toast.LENGTH_LONG, FancyToast.ERROR, false).show();

                            }
                        }
                    });
                } else {
                    FancyToast.makeText(getApplicationContext(), "Debes estas logueado para acceder a esta funcion ", Toast.LENGTH_LONG, FancyToast.ERROR, false).show();

                }
                break;

            case R.id.opcionComentarios:
                ComentariosDeLaNoticiaFragment comentariosDeLaNoticiaFragment = ComentariosDeLaNoticiaFragment.fabricaDeFragmentsComentariosDeLaNoticia(listadoDeNoticias.getArray().get(posicionActual).getId());
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutContenedor, comentariosDeLaNoticiaFragment).addToBackStack("Camilo");
                fragmentTransaction.commit();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    public void informacionScrollViewPager() {

        viewPagerListaDeNoticias.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                posicionActual = position;

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void notificarTouchLikeButton(final Integer idComentario) {

    }


    @Override
    public void notificarTouchImageView(String url) {

        if (imagenDeLaNoticiaFragment == null){
            imagenDeLaNoticiaFragment = ImagenDeLaNoticiaFragment.fabricaDeFragmentsImagenDeLaNoticia(url);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.desde_abajo_hacia_arriba,0,0, R.anim.desde_arriba_hacia_abajo);
            fragmentTransaction.replace(R.id.frameLayoutContenedor, imagenDeLaNoticiaFragment).addToBackStack("Camilo");
            fragmentTransaction.commit();

            viewPagerListaDeNoticias.setVisibility(View.GONE);
            frameLayoutContenedor.setBackgroundColor(Color.BLACK);
        }


    }
}




