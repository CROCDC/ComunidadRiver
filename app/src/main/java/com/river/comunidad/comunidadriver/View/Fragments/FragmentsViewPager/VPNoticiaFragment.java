package com.river.comunidad.comunidadriver.View.Fragments.FragmentsViewPager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.river.comunidad.comunidadriver.Model.API_REST.Noticia;
import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.Utils.Helper;

/**
 * A simple {@link Fragment} subclass.
 */
public class VPNoticiaFragment extends Fragment {

    public static final String CLAVE_OBJETO_NOTICIA = "objeto";

    private Noticia noticia;

    private ImageView imageViewDeLaNoticia;
    private TextView textViewTituloDeLaNoticia;
    private TextView textViewCopeteDeLaNoticia;

    public static VPNoticiaFragment frabricaDeFragmentsVPNoticiaFragment(Noticia noticia) {
        VPNoticiaFragment vpNoticiaFragment = new VPNoticiaFragment();

        Bundle bundle = new Bundle();

        bundle.putSerializable(CLAVE_OBJETO_NOTICIA, noticia);

        vpNoticiaFragment.setArguments(bundle);

        return vpNoticiaFragment;

    }

    public VPNoticiaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vpnoticia, container, false);

        imageViewDeLaNoticia = view.findViewById(R.id.imageViewDeLaNoticia_fragmentvpnoticia);
        textViewTituloDeLaNoticia = view.findViewById(R.id.textViewTituloDeLaNoticia_fragmentvpnoticia);
        textViewCopeteDeLaNoticia = view.findViewById(R.id.textViewCopeteDeLaNoticia_fragmentvpnoticia);

        Bundle bundle = getArguments();

        noticia = (Noticia) bundle.getSerializable(CLAVE_OBJETO_NOTICIA);

        Helper.cargarImagenes(imageViewDeLaNoticia, getContext(), noticia.getEmbedded().getListaDeImagenes().get(0).getMedia_details().getSizes().getMedium().getSource_url());

        textViewTituloDeLaNoticia.setText(noticia.getTitle().getRendered());

        textViewCopeteDeLaNoticia.setText(noticia.getExcerpt().getRendered());



        return view;
    }

}
