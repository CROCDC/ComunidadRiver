package com.river.comunidad.comunidadriver.View.Fragments;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.river.comunidad.comunidadriver.Model.API_REST.ListadoDeNoticias;
import com.river.comunidad.comunidadriver.Model.API_REST.Noticia;
import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.View.Adapters.ListaDeNoticiasEnHorizontalAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UltimasNoticiasFragment extends Fragment {


    public static final String CLAVE_LISTA_NOTICIAS ="lista";

    public UltimasNoticiasFragment() {
        // Required empty public constructor
    }

    private ListadoDeNoticias listadoDeNoticias;

    private RecyclerView recyclerViewListaDeUltimasNoticias;
    private ListaDeNoticiasEnHorizontalAdapter listaDeNoticiasEnHorizontalAdapter;

    public static UltimasNoticiasFragment frabricaFragmentsUltimasNoticias(List<Noticia> listaDeNoticias){
        UltimasNoticiasFragment ultimasNoticiasFragment = new UltimasNoticiasFragment();

        Bundle bundle = new Bundle();

        bundle.putSerializable(CLAVE_LISTA_NOTICIAS,new ListadoDeNoticias(listaDeNoticias));

        ultimasNoticiasFragment.setArguments(bundle);

        return ultimasNoticiasFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ultimas_noticias, container, false);

        recyclerViewListaDeUltimasNoticias = view.findViewById(R.id.recyclerViewListaDeUltimasNoticias_fragmentultimasnoticias);

        Bundle bundle = getArguments();

        listadoDeNoticias = (ListadoDeNoticias) bundle.getSerializable(CLAVE_LISTA_NOTICIAS);

        listaDeNoticiasEnHorizontalAdapter = new ListaDeNoticiasEnHorizontalAdapter();

        listaDeNoticiasEnHorizontalAdapter.setListaDeNoticias(listadoDeNoticias.getArray());

        recyclerViewListaDeUltimasNoticias.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        recyclerViewListaDeUltimasNoticias.setAdapter(listaDeNoticiasEnHorizontalAdapter);



        return view;
    }

}
