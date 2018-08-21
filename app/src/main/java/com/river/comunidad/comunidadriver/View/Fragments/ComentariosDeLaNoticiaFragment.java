package com.river.comunidad.comunidadriver.View.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.river.comunidad.comunidadriver.Controler.ControllerNoticiaFirebase;
import com.river.comunidad.comunidadriver.Model.Models.Comentario;
import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.Utils.ResultListener;
import com.river.comunidad.comunidadriver.View.Adapters.ListaDeComentariosAdapter;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComentariosDeLaNoticiaFragment extends android.support.v4.app.Fragment {


    public static final String CLAVE_IDNOTICIA = "clave noticia";

    private Integer idNoticia;
    private EditText editTextComentarioDelUsuario;
    private CardView cardViewButtonPublicar;
    private TextView textViewCantidadDeComentarios;
    private RecyclerView recyclerViewListaDeComentarios;
    private ListaDeComentariosAdapter listaDeComentariosAdapter;

    private Comentario comentario;

    public ComentariosDeLaNoticiaFragment() {
        // Required empty public constructor
    }

    public static ComentariosDeLaNoticiaFragment fabricaDeFragmentsComentariosDeLaNoticia(Integer idNoticia) {
        ComentariosDeLaNoticiaFragment comentariosDeLaNoticiaFragment = new ComentariosDeLaNoticiaFragment();

        Bundle bundle = new Bundle();

        bundle.putInt(CLAVE_IDNOTICIA, idNoticia);

        comentariosDeLaNoticiaFragment.setArguments(bundle);

        return comentariosDeLaNoticiaFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        idNoticia = bundle.getInt(CLAVE_IDNOTICIA);

        listaDeComentariosAdapter = new ListaDeComentariosAdapter();
        pedirComentarios();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comentarios_de_la_noticia, container, false);


        editTextComentarioDelUsuario = view.findViewById(R.id.editTextComentarioDelUsuario_fragmentcomentariosdelanoticia);
        cardViewButtonPublicar = view.findViewById(R.id.cardViewButtonPublicarComentario_fragmentcomentariosdelanoticia);
        textViewCantidadDeComentarios = view.findViewById(R.id.textViewCantidadDeComentarios_fragmentcomentariosdelanoticia);
        recyclerViewListaDeComentarios = view.findViewById(R.id.recyclerViewListaDeComentariosDeLaNoticia_fragmentcomentariosdelanoticia);


        recyclerViewListaDeComentarios.setAdapter(listaDeComentariosAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,true) {
            //UTILIZO ESTO PARA DESHABILITAR LA POSIBILIDAD DE SCROLLEAR EN EL RECYCLERVIEW
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };


        recyclerViewListaDeComentarios.setLayoutManager(linearLayoutManager);


        touchCardViewButtonPublicar();

        return view;
    }


    public void touchCardViewButtonPublicar() {
        cardViewButtonPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTextComentarioDelUsuario.getText().toString().equals("")) {
                    try {
                        comentario = new Comentario(FirebaseAuth.getInstance().getCurrentUser().getUid(), idNoticia, FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString(), 0,0, editTextComentarioDelUsuario.getText().toString(), "hace una hora");
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                    new ControllerNoticiaFirebase(getContext()).publicarComentario(idNoticia, comentario, new ResultListener<Boolean>() {
                        @Override
                        public void finish(Boolean resultado) {
                            if (resultado) {
                                FancyToast.makeText(getContext(), "Comentario Publicado", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                                pedirComentarios();
                                editTextComentarioDelUsuario.setText("");
                            } else {
                                FancyToast.makeText(getContext(), "a ocurrido un error por favor intente nuevamente", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();

                            }
                        }
                    });
                } else {
                    FancyToast.makeText(getContext(), "Por favor escriba un comentario antes de publicarlo", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }
            }
        });
    }

    public void pedirComentarios() {
        new ControllerNoticiaFirebase(getContext()).pedirComentariosDeUnaNoticia(idNoticia, new ResultListener<List<Comentario>>() {
            @Override
            public void finish(List<Comentario> resultado) {
                listaDeComentariosAdapter.setListaDeComentarios(resultado);
            }
        });
    }
}
