package com.river.comunidad.comunidadriver.View.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.river.comunidad.comunidadriver.Controler.ControllerNoticiaFirebase;
import com.river.comunidad.comunidadriver.Model.Models.Comentario;
import com.river.comunidad.comunidadriver.Model.Models.DisLike;
import com.river.comunidad.comunidadriver.Model.Models.Like;
import com.river.comunidad.comunidadriver.Model.Models.Respuesta;
import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.Utils.ResultListener;
import com.river.comunidad.comunidadriver.View.Adapters.ListaDeComentariosAdapter;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComentariosDeLaNoticiaFragment extends android.support.v4.app.Fragment {

    private NotificadorHaciaDetalleDeUnaNoticiaActivity notificador;

    public static final String CLAVE_IDNOTICIA = "clave noticia";

    private Integer idNoticia;
    private EditText editTextComentarioDelUsuario;
    private CardView cardViewButtonPublicar;
    private TextView textViewCantidadDeComentarios;
    private RecyclerView recyclerViewListaDeComentarios;
    private ListaDeComentariosAdapter listaDeComentariosAdapter;
    private ControllerNoticiaFirebase controllerNoticiaFirebase;

    private FirebaseUser user;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        notificador = (NotificadorHaciaDetalleDeUnaNoticiaActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            user = FirebaseAuth.getInstance().getCurrentUser();
        }


        idNoticia = bundle.getInt(CLAVE_IDNOTICIA);
        controllerNoticiaFirebase = new ControllerNoticiaFirebase(getContext());
        listaDeComentariosAdapter = new ListaDeComentariosAdapter(new ListaDeComentariosAdapter.NotificadorHaciaImplementadorDeComentariosAdapter() {
            @Override
            public void notificar() {
            }

            @Override
            public void noticicarTouchLikeButton(final Integer idComentario) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    final String usuarioUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    controllerNoticiaFirebase.verfificarSiElUsuarioYaLikeoElComentario(idNoticia, idComentario, usuarioUID, new ResultListener<Boolean>() {
                        @Override
                        public void finish(Boolean resultado) {
                            if (resultado) {
                                FancyToast.makeText(getContext(), "solo puede opinar una vez", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                            } else {
                                controllerNoticiaFirebase.verfificarSiElUsuarioYaDisLikeoElComentario(idNoticia, idComentario, usuarioUID, new ResultListener<Boolean>() {
                                    @Override
                                    public void finish(Boolean resultado) {
                                        if (resultado) {
                                            controllerNoticiaFirebase.descontarDisLikeAUnComentario(idNoticia, idComentario, usuarioUID, new ResultListener<Boolean>() {
                                                @Override
                                                public void finish(Boolean resultado) {
                                                    controllerNoticiaFirebase.darLikeAUnComentario(idNoticia, idComentario, new ResultListener<Boolean>() {
                                                        @Override
                                                        public void finish(Boolean resultado) {
                                                            pedirComentarios();
                                                        }
                                                    });
                                                }
                                            });
                                        } else {
                                            controllerNoticiaFirebase.darLikeAUnComentario(idNoticia, idComentario, new ResultListener<Boolean>() {
                                                @Override
                                                public void finish(Boolean resultado) {
                                                    pedirComentarios();
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    });

                } else {
                    FancyToast.makeText(getContext(), "debe estar logueado para acceder a esta funcion", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

                }
            }

            @Override
            public void notificarTouchDisLikeButton(final Integer idComentario) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    final String usuarioUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    controllerNoticiaFirebase.verfificarSiElUsuarioYaDisLikeoElComentario(idNoticia, idComentario, usuarioUID, new ResultListener<Boolean>() {
                        @Override
                        public void finish(Boolean resultado) {
                            if (resultado) {
                                FancyToast.makeText(getContext(), "solo puede opinar una vez", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

                            } else {
                                controllerNoticiaFirebase.verfificarSiElUsuarioYaLikeoElComentario(idNoticia, idComentario, usuarioUID, new ResultListener<Boolean>() {
                                    @Override
                                    public void finish(Boolean resultado) {
                                        if (resultado) {
                                            controllerNoticiaFirebase.descontarLikeAUnComentario(idNoticia, idComentario, usuarioUID, new ResultListener<Boolean>() {
                                                @Override
                                                public void finish(Boolean resultado) {
                                                    controllerNoticiaFirebase.darDisLikeAUnComentario(idNoticia, idComentario, new ResultListener<Boolean>() {
                                                        @Override
                                                        public void finish(Boolean resultado) {
                                                            pedirComentarios();
                                                        }
                                                    });
                                                }
                                            });
                                        } else {
                                            controllerNoticiaFirebase.darDisLikeAUnComentario(idNoticia, idComentario, new ResultListener<Boolean>() {
                                                @Override
                                                public void finish(Boolean resultado) {
                                                    pedirComentarios();
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    });


                } else {
                    FancyToast.makeText(getContext(), "debe estar logueado para acceder a esta funcion", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();


                }
            }

            @Override
            public void notificarTouchPublicarButton(String texto, Integer idComentario) {
                List<String> usuarios = new ArrayList<>();

                Like like = new Like(0, usuarios);
                DisLike disLike = new DisLike(0, usuarios);


                Respuesta respuesta = new Respuesta(idNoticia, idComentario, user.getDisplayName(), user.getUid(), user.getPhotoUrl().toString(), like, disLike, texto, System.currentTimeMillis());

                controllerNoticiaFirebase.publicarRespuestas(idNoticia, idComentario, respuesta, new ResultListener<Boolean>() {
                    @Override
                    public void finish(Boolean resultado) {
                        if (resultado) {
                            pedirComentarios();
                        } else {
                            FancyToast.makeText(getContext(), "a ocurrido un error por favor intente nuevamente", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();

                        }
                    }
                });

            }

        });

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true) {
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

                if (FirebaseAuth.getInstance().getCurrentUser() != null) {

                    if (!editTextComentarioDelUsuario.getText().toString().equals("")) {
                        List<Respuesta> listaDeRespuestas = new ArrayList<>();

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        List<String> usuarios = new ArrayList<>();

                        Like like = new Like(0, usuarios);
                        DisLike disLike = new DisLike(0, usuarios);


                        comentario = new Comentario(idNoticia, user.getUid(), user.getDisplayName(), user.getPhotoUrl().toString(), like, disLike, editTextComentarioDelUsuario.getText().toString(), System.currentTimeMillis(), listaDeRespuestas);

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
                } else {
                    FancyToast.makeText(getContext(), "debe estar logueado para acceder a esta funcion", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

                }

            }

        });
    }

    public void pedirComentarios() {
        new ControllerNoticiaFirebase(getContext()).pedirListaDeComentariosDeUnaNoticia(idNoticia, new ResultListener<List<Comentario>>() {
            @Override
            public void finish(List<Comentario> resultado) {
                listaDeComentariosAdapter.setListaDeComentarios(resultado);
                textViewCantidadDeComentarios.setText("Todos los comentarios (" + resultado.size() + ")");

            }
        });
    }

    public interface NotificadorHaciaDetalleDeUnaNoticiaActivity {
        public void notificarTouchLikeButton(Integer idComentario);
    }

    public interface NotificadorHaciaComentariosAdapter {
        public void notificarSumarLike();
    }
}
