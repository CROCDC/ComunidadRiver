package com.river.comunidad.comunidadriver.View.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.river.comunidad.comunidadriver.Model.API_REST.Noticia;
import com.river.comunidad.comunidadriver.View.Fragments.FragmentsViewPager.DetalleDeUnaNoticiaFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdaperDetalleNoticia extends FragmentStatePagerAdapter {
    private List<Fragment> listaDeFragments;

    public ViewPagerAdaperDetalleNoticia(FragmentManager fm, List<Noticia> listaDeNoticias) {
        super(fm);
        listaDeFragments = new ArrayList<>();

        for (Noticia noticia : listaDeNoticias) {
            DetalleDeUnaNoticiaFragment detalleDeUnaNoticiaFragment = DetalleDeUnaNoticiaFragment.fabricaDeFragmentsDetalleDeUnaNoticia(noticia);
            listaDeFragments.add(detalleDeUnaNoticiaFragment);
        }
    }

    public void agregarNoticiasALaLista(List<Noticia> listaDeNoticias) {
        listaDeNoticias.addAll(listaDeNoticias);


    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int i) {
        return listaDeFragments.get(i);
    }


    @Override
    public int getCount() {
        return listaDeFragments.size();
    }
}
