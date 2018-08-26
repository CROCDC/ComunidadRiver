package com.river.comunidad.comunidadriver.View.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.Utils.Helper;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImagenDeLaNoticiaFragment extends android.support.v4.app.Fragment {

    public static final String CLAVE_URLIMAGEN = "url imagen";

    private ImageView imageViewDeLaNoticia;

    private String urlImagen;

    public ImagenDeLaNoticiaFragment() {
        // Required empty public constructor
    }

    public static ImagenDeLaNoticiaFragment fabricaDeFragmentsImagenDeLaNoticia(String url) {
        ImagenDeLaNoticiaFragment imagenDeLaNoticiaFragment = new ImagenDeLaNoticiaFragment();

        Bundle bundle = new Bundle();

        bundle.putString(CLAVE_URLIMAGEN, url);

        imagenDeLaNoticiaFragment.setArguments(bundle);

        return imagenDeLaNoticiaFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_imagen_de_la_noticia, container, false);

        Bundle bundle = getArguments();

        urlImagen = bundle.getString(CLAVE_URLIMAGEN);

        imageViewDeLaNoticia = view.findViewById(R.id.imageView_fragmentimagendelanoticia);

        Helper.cargarImagenes(imageViewDeLaNoticia, getContext(), urlImagen);


        return view;
    }

}
