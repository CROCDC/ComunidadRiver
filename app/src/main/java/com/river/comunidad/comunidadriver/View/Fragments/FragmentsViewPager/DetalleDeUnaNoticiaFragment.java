package com.river.comunidad.comunidadriver.View.Fragments.FragmentsViewPager;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.river.comunidad.comunidadriver.Model.Model.Noticia;
import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.Utils.Helper;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleDeUnaNoticiaFragment extends Fragment {


    public static final String CLAVE_NOTICIA = "noticia fragment";

    private Noticia noticia;


    private ImageView imageViewDeLaNoticia;
    private TextView textViewTituloDeLaNoticia;
    private WebView webViewContenidoDeLaNoticia;

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
        webViewContenidoDeLaNoticia = view.findViewById(R.id.webViewContenidoDeLaNoticia_fragmentdetalledeunanoticia);

        Bundle bundle = getArguments();

        noticia = (Noticia) bundle.getSerializable(CLAVE_NOTICIA);

        try {
            Helper.cargarImagenes(imageViewDeLaNoticia, getContext(), noticia.getEmbedded().getListaDeImagenes().get(0).getMedia_details().getSizes().getMedium_Large().getSource_url());

        } catch (Exception e) {
            Toast.makeText(getContext(), "te debo la imagen", Toast.LENGTH_SHORT).show();
        }

        textViewTituloDeLaNoticia.setText(noticia.getTitle().getRendered());

        webViewContenidoDeLaNoticia.loadData(noticia.getContent().getRendered(), "text/html", "UTF-8")

        return view;
    }

}
