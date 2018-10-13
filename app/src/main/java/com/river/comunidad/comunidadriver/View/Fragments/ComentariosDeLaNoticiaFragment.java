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
import com.river.comunidad.comunidadriver.Controller.ControllerNoticiaFirebase;
import com.river.comunidad.comunidadriver.Model.Firebase.Comentario;
import com.river.comunidad.comunidadriver.Model.Firebase.DisLike;
import com.river.comunidad.comunidadriver.Model.Firebase.Like;
import com.river.comunidad.comunidadriver.Model.Firebase.Respuesta;
import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.Utils.Helper;
import com.river.comunidad.comunidadriver.Utils.ResultListener;
import com.river.comunidad.comunidadriver.View.Adapters.ListaDeComentariosAdapter;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    private List<Respuesta> listaDeRespuestas;

    private String urlImagenDePerfil;
    private String nombreDeUsuario;

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
            public void notificarTouchDisLikeButtonRespuesta(final Integer idRespuesta, final String idComentario, final Integer idNoticia) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    controllerNoticiaFirebase.verificarSiElUsuarioYaReaccionoALaRespuesta("disLike", idNoticia, idComentario, idRespuesta, new ResultListener<Boolean>() {
                        @Override
                        public void finish(Boolean resultado) {
                            if (resultado) {
                                FancyToast.makeText(getContext(), "solo puede opinar una vez", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

                            } else {
                                controllerNoticiaFirebase.verificarSiElUsuarioYaReaccionoALaRespuesta("like", idNoticia, idComentario, idRespuesta, new ResultListener<Boolean>() {
                                    @Override
                                    public void finish(Boolean resultado) {
                                        if (resultado) {
                                            controllerNoticiaFirebase.descontarReaccionAUnaRespueta("like", idNoticia, idComentario, idRespuesta, new ResultListener<Boolean>() {
                                                @Override
                                                public void finish(Boolean resultado) {
                                                    controllerNoticiaFirebase.darReaccionAUnaRespuesta("disLike", idNoticia, idComentario, idRespuesta, new ResultListener<Boolean>() {
                                                        @Override
                                                        public void finish(Boolean resultado) {
                                                            pedirComentarios();
                                                        }
                                                    });
                                                }
                                            });


                                        } else {
                                            controllerNoticiaFirebase.darReaccionAUnaRespuesta("disLike", idNoticia, idComentario, idRespuesta, new ResultListener<Boolean>() {
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
                    Helper.avisarNoEstasLogueado(getContext());

                }
            }

            @Override
            public void notificarTouchLikeButtonRespuesta(final Integer idRespuesta, final String idComentario, final Integer idNoticia) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    controllerNoticiaFirebase.verificarSiElUsuarioYaReaccionoALaRespuesta("like", idNoticia, idComentario, idRespuesta, new ResultListener<Boolean>() {
                        @Override
                        public void finish(Boolean resultado) {
                            if (resultado) {
                                FancyToast.makeText(getContext(), "solo puede opinar una vez", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                            } else {
                                controllerNoticiaFirebase.verificarSiElUsuarioYaReaccionoALaRespuesta("disLike", idNoticia, idComentario, idRespuesta, new ResultListener<Boolean>() {
                                    @Override
                                    public void finish(Boolean resultado) {
                                        if (resultado) {
                                            controllerNoticiaFirebase.descontarReaccionAUnaRespueta("disLike", idNoticia, idComentario, idRespuesta, new ResultListener<Boolean>() {
                                                @Override
                                                public void finish(Boolean resultado) {
                                                    controllerNoticiaFirebase.darReaccionAUnaRespuesta("like", idNoticia, idComentario, idRespuesta, new ResultListener<Boolean>() {
                                                        @Override
                                                        public void finish(Boolean resultado) {
                                                            pedirComentarios();
                                                        }
                                                    });
                                                }
                                            });


                                        } else {
                                            controllerNoticiaFirebase.darReaccionAUnaRespuesta("like", idNoticia, idComentario, idRespuesta, new ResultListener<Boolean>() {
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
                    Helper.avisarNoEstasLogueado(getContext());

                }

            }

            @Override
            public void notificarTouchLikeButton(final String idComentario, final Integer idNoticia) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    controllerNoticiaFirebase.verfificarSiElUsuarioYaReaccionoAlComentario("like", idNoticia, idComentario, new ResultListener<Boolean>() {
                        @Override
                        public void finish(Boolean resultado) {
                            if (resultado) {
                                FancyToast.makeText(getContext(), "solo puede opinar una vez", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                            } else {
                                controllerNoticiaFirebase.verfificarSiElUsuarioYaReaccionoAlComentario("disLike", idNoticia, idComentario, new ResultListener<Boolean>() {
                                    @Override
                                    public void finish(Boolean resultado) {
                                        if (resultado) {
                                            controllerNoticiaFirebase.descontarReaccionAUnComentario("disLike", idNoticia, idComentario, new ResultListener<Boolean>() {
                                                @Override
                                                public void finish(Boolean resultado) {

                                                    controllerNoticiaFirebase.darReaccionAUnComentario("like", idNoticia, idComentario, new ResultListener<Boolean>() {
                                                        @Override
                                                        public void finish(Boolean resultado) {
                                                            pedirComentarios();
                                                        }
                                                    });
                                                }
                                            });


                                        } else {
                                            controllerNoticiaFirebase.darReaccionAUnComentario("like", idNoticia, idComentario, new ResultListener<Boolean>() {
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
                    Helper.avisarNoEstasLogueado(getContext());
                }
            }

            @Override
            public void notificarTouchDisLikeButton(final String idComentario, final Integer idNoticia) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    controllerNoticiaFirebase.verfificarSiElUsuarioYaReaccionoAlComentario("disLike", idNoticia, idComentario, new ResultListener<Boolean>() {
                        @Override
                        public void finish(Boolean resultado) {
                            if (resultado) {
                                FancyToast.makeText(getContext(), "solo puede opinar una vez", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

                            } else {
                                controllerNoticiaFirebase.verfificarSiElUsuarioYaReaccionoAlComentario("like", idNoticia, idComentario, new ResultListener<Boolean>() {
                                    @Override
                                    public void finish(Boolean resultado) {
                                        if (resultado) {
                                            controllerNoticiaFirebase.descontarReaccionAUnComentario("like", idNoticia, idComentario, new ResultListener<Boolean>() {
                                                @Override
                                                public void finish(Boolean resultado) {
                                                    controllerNoticiaFirebase.darReaccionAUnComentario("disLike", idNoticia, idComentario, new ResultListener<Boolean>() {
                                                        @Override
                                                        public void finish(Boolean resultado) {
                                                            pedirComentarios();
                                                        }
                                                    });
                                                }
                                            });


                                        } else {
                                            controllerNoticiaFirebase.darReaccionAUnComentario("disLike", idNoticia, idComentario, new ResultListener<Boolean>() {
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
                    Helper.avisarNoEstasLogueado(getContext());


                }
            }

            @Override
            public void notificarTouchPublicarButton(String texto, String idComentario, final Integer idNoticia) {
                List<String> usuarios = new ArrayList<>();

                Like like = new Like(usuarios);
                DisLike disLike = new DisLike(usuarios);


                Respuesta respuesta = new Respuesta(idNoticia, 0, user.getDisplayName(), user.getUid(), user.getPhotoUrl().toString(), like, disLike, texto, System.currentTimeMillis());

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
                        listaDeRespuestas = new ArrayList<>();

                        user = FirebaseAuth.getInstance().getCurrentUser();

                        if (user.getDisplayName().equals("")) {
                            nombreDeUsuario = user.getEmail();
                            String nombre = "";
                            for (Integer i = 0; i < nombreDeUsuario.length(); i++) {
                                if (nombreDeUsuario.charAt(i) != '@') {
                                    nombre = nombre + nombreDeUsuario.charAt(i);
                                } else {
                                    break;
                                }
                            }
                            nombreDeUsuario = nombre;

                        } else {
                            nombreDeUsuario = user.getDisplayName();
                        }

                        try {
                            urlImagenDePerfil = user.getPhotoUrl().toString();
                        } catch (Exception e) {
                            urlImagenDePerfil = "http://www.comunidadriver.com/wp-content/uploads/2018/08/cuenta-1.png";
                        }

                        List<String> usuarios = new ArrayList<>();

                        Like like = new Like(usuarios);
                        DisLike disLike = new DisLike(usuarios);


                        comentario = new Comentario(idNoticia, user.getUid(), nombreDeUsuario, urlImagenDePerfil, like, disLike, editTextComentarioDelUsuario.getText().toString(), System.currentTimeMillis(), listaDeRespuestas, UUID.randomUUID().toString());

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
                    Helper.avisarNoEstasLogueado(getContext());

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
