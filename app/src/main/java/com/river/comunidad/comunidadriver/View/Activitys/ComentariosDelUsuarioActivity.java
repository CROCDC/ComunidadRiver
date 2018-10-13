package com.river.comunidad.comunidadriver.View.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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

public class ComentariosDelUsuarioActivity extends AppCompatActivity {

    private ControllerNoticiaFirebase controllerNoticiaFirebase;
    private RecyclerView recyclerViewListaDeComentariosDelUsuario;
    private ListaDeComentariosAdapter listaDeComentariosAdapter;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseUser user;
    private Toolbar toolbar;
    private TextView textViewFijoAdvertencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios_del_usuario);

        user = FirebaseAuth.getInstance().getCurrentUser();

        toolbar = findViewById(R.id.toolbarPrincipal_toolbar);
        recyclerViewListaDeComentariosDelUsuario = findViewById(R.id.recyclerViewListaDeComentariosDelUsuario_activitycomentariosdelusuario);
        textViewFijoAdvertencia = findViewById(R.id.textViewFijoAdvertenciaComentarios_activitycomentariodelusuario);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listaDeComentariosAdapter = new ListaDeComentariosAdapter(new ListaDeComentariosAdapter.NotificadorHaciaImplementadorDeComentariosAdapter() {
            @Override
            public void notificarTouchLikeButtonRespuesta(final Integer idRespuesta, final String idComentario, final Integer idNoticia) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    controllerNoticiaFirebase.verificarSiElUsuarioYaReaccionoALaRespuesta("like", idNoticia, idComentario, idRespuesta, new ResultListener<Boolean>() {
                        @Override
                        public void finish(Boolean resultado) {
                            if (resultado) {
                                FancyToast.makeText(getApplicationContext(), "solo puede opinar una vez", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
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
                    Helper.avisarNoEstasLogueado(getApplicationContext());

                }
            }

            @Override
            public void notificarTouchDisLikeButtonRespuesta(final Integer idRespuesta, final String idComentario, final Integer idNoticia) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    controllerNoticiaFirebase.verificarSiElUsuarioYaReaccionoALaRespuesta("disLike", idNoticia, idComentario, idRespuesta, new ResultListener<Boolean>() {
                        @Override
                        public void finish(Boolean resultado) {
                            if (resultado) {
                                FancyToast.makeText(getApplicationContext(), "solo puede opinar una vez", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

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
                    Helper.avisarNoEstasLogueado(getApplicationContext());

                }
            }

            @Override
            public void notificarTouchLikeButton(final String idComentario, final Integer idNoticia) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    controllerNoticiaFirebase.verfificarSiElUsuarioYaReaccionoAlComentario("like", idNoticia, idComentario, new ResultListener<Boolean>() {
                        @Override
                        public void finish(Boolean resultado) {
                            if (resultado) {
                                FancyToast.makeText(getApplicationContext(), "solo puede opinar una vez", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
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
                    Helper.avisarNoEstasLogueado(getApplicationContext());
                }
            }

            @Override
            public void notificarTouchDisLikeButton(final String idComentario, final Integer idNoticia) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    controllerNoticiaFirebase.verfificarSiElUsuarioYaReaccionoAlComentario("disLike", idNoticia, idComentario, new ResultListener<Boolean>() {
                        @Override
                        public void finish(Boolean resultado) {
                            if (resultado) {
                                FancyToast.makeText(getApplicationContext(), "solo puede opinar una vez", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

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
                    Helper.avisarNoEstasLogueado(getApplicationContext());


                }
            }

            @Override
            public void notificarTouchPublicarButton(String texto, String idComentario, Integer idNoticia) {
                List<String> usuarios = new ArrayList<>();

                Like like = new Like(usuarios);
                DisLike disLike = new DisLike(usuarios);


                Respuesta respuesta = new Respuesta(idNoticia, 0, user.getDisplayName(), user.getUid(), user.getPhotoUrl().toString(), like, disLike, texto, System.currentTimeMillis());

                controllerNoticiaFirebase.publicarRespuestas(idNoticia, idComentario, respuesta, new ResultListener<Boolean>() {
                    @Override
                    public void finish(Boolean resultado) {
                        if (resultado) {
                            pedirComentarios();
                        } else
                            FancyToast.makeText(getApplicationContext(), "a ocurrido un error por favor intente nuevamente", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                    }
                });
            }


        });

        controllerNoticiaFirebase = new ControllerNoticiaFirebase(getApplicationContext());

        pedirComentarios();


        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true);

        recyclerViewListaDeComentariosDelUsuario.setAdapter(listaDeComentariosAdapter);

        recyclerViewListaDeComentariosDelUsuario.setLayoutManager(linearLayoutManager);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 16908332:
                ComentariosDelUsuarioActivity.this.onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);

    }

    public void pedirComentarios() {
        controllerNoticiaFirebase.pedirListaDeComentariosDeUnUsuario(new ResultListener<List<Comentario>>() {
            @Override
            public void finish(List<Comentario> resultado) {
                listaDeComentariosAdapter.setListaDeComentarios(resultado);
                if (resultado.size() == 0) {
                    textViewFijoAdvertencia.setVisibility(View.VISIBLE);
                    recyclerViewListaDeComentariosDelUsuario.setVisibility(View.GONE);
                }
            }
        });
    }


}
