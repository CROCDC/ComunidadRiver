package com.river.comunidad.comunidadriver.View.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.river.comunidad.comunidadriver.Model.Model.Noticia;
import com.river.comunidad.comunidadriver.View.Fragments.FragmentsViewPager.VPNoticiaFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapterNoticiaFragment extends FragmentStatePagerAdapter {
    private List<Fragment> listaDeFragments;


    public ViewPagerAdapterNoticiaFragment(FragmentManager fm, List<Noticia> listaDeNoticias) {
        super(fm);
        listaDeFragments = new ArrayList<>();
        for (Noticia noticia : listaDeNoticias) {
            VPNoticiaFragment vpNoticiaFragment = VPNoticiaFragment.frabricaDeFragmentsVPNoticiaFragment(noticia);

            listaDeFragments.add(vpNoticiaFragment);
        }

    }

    @Override
    public Fragment getItem(int position) {
        return listaDeFragments.get(position);
    }

    @Override
    public int getCount() {
        return listaDeFragments.size();
    }
}
