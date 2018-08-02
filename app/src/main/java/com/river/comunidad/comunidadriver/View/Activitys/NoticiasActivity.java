package com.river.comunidad.comunidadriver.View.Activitys;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.river.comunidad.comunidadriver.Controler.ControlerNoticias;
import com.river.comunidad.comunidadriver.Model.Model.Noticia;

import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.Utils.ResultListener;

import com.river.comunidad.comunidadriver.View.Adapters.ViewPagerAdapterNoticiaFragment;

import java.util.List;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;

public class NoticiasActivity extends AppCompatActivity {

    private VerticalViewPager verticalViewPagerListaDeNoticias;
    private ViewPagerAdapterNoticiaFragment viewPagerAdapterNoticiaFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);

        verticalViewPagerListaDeNoticias = findViewById(R.id.verticalViewPagerListaNoticias_activitynoticias);

        new ControlerNoticias().pedirListaDeNoticias(new ResultListener<List<Noticia>>() {
            @Override
            public void finish(List<Noticia> resultado) {
                viewPagerAdapterNoticiaFragment = new ViewPagerAdapterNoticiaFragment(getSupportFragmentManager(),resultado);

                verticalViewPagerListaDeNoticias.setAdapter(viewPagerAdapterNoticiaFragment);
                verticalViewPagerListaDeNoticias.setClipToPadding(false);
                verticalViewPagerListaDeNoticias.setPageMargin(1);




                informacionScrolViewPager();



            }
        });

    }

    public void informacionScrolViewPager(){
        verticalViewPagerListaDeNoticias.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                viewPagerAdapterNoticiaFragment.getItem(verticalViewPagerListaDeNoticias.getCurrentItem()).getView().setPadding(0,0,0,0);
                viewPagerAdapterNoticiaFragment.getItem(verticalViewPagerListaDeNoticias.getCurrentItem() + 1).getView().setPadding(40,0,40,0);


            }
        });
    }
}
