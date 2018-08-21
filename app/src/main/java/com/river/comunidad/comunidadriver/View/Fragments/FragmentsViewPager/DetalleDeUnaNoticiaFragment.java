package com.river.comunidad.comunidadriver.View.Fragments.FragmentsViewPager;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.river.comunidad.comunidadriver.Model.Models.Noticia;
import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.Utils.Helper;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleDeUnaNoticiaFragment extends Fragment {


    public static final String CLAVE_NOTICIA = "noticia fragment";
    public static final String CLAVE_LISTA_NOTICIAS = "lista de noticias";

    private Noticia noticia;


    private ImageView imageViewDeLaNoticia;
    private TextView textViewTituloDeLaNoticia;
    private TextView textViewContenidoDeLaNoticia;

    public static DetalleDeUnaNoticiaFragment fabricaDeFragmentsDetalleDeUnaNoticia(Noticia noticia) {
        DetalleDeUnaNoticiaFragment detalleDeUnaNoticiaFragment = new DetalleDeUnaNoticiaFragment();

        Bundle bundle = new Bundle();

        bundle.putSerializable(CLAVE_NOTICIA, noticia);

        detalleDeUnaNoticiaFragment.setArguments(bundle);

        return detalleDeUnaNoticiaFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle_de_una_noticia, container, false);

        imageViewDeLaNoticia = view.findViewById(R.id.imageViewDeLaNoticia_fragmentdetalledeunanoticia);
        textViewTituloDeLaNoticia = view.findViewById(R.id.textViewTituloDeLaNoticia_fragmentdetalledeunanoticia);
        textViewContenidoDeLaNoticia = view.findViewById(R.id.webViewContenidoDeLaNoticia_fragmentdetalledeunanoticia);


        Bundle bundle = getArguments();

        noticia = (Noticia) bundle.getSerializable(CLAVE_NOTICIA);

        try {
            Helper.cargarImagenes(imageViewDeLaNoticia, getContext(), noticia.getEmbedded().getListaDeImagenes().get(0).getMedia_details().getSizes().getMedium_Large().getSource_url());

        } catch (Exception e) {
            Helper.cargarImagenes(imageViewDeLaNoticia, getContext(), noticia.getEmbedded().getListaDeImagenes().get(0).getsource_url());
        }

        textViewTituloDeLaNoticia.setText(noticia.getTitle().getRendered());

        textViewContenidoDeLaNoticia.setText(Html.fromHtml(noticia.getContent().getRendered()));


        return view;
    }

}
