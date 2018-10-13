package com.river.comunidad.comunidadriver.Controller;

import android.content.Context;

import com.river.comunidad.comunidadriver.DAO.DAONoticiaFirebase;
import com.river.comunidad.comunidadriver.Model.API_REST.Noticia;
import com.river.comunidad.comunidadriver.Model.Firebase.Comentario;
import com.river.comunidad.comunidadriver.Model.Firebase.Respuesta;
import com.river.comunidad.comunidadriver.Utils.ResultListener;

import java.util.List;

public class ControllerNoticiaFirebase {
    private Context context;
    private DAONoticiaFirebase daoNoticiaFirebase;

    public ControllerNoticiaFirebase(Context context) {
        this.context = context;
        daoNoticiaFirebase = new DAONoticiaFirebase(context);
    }

    public void pedirListaDeNoticiasGuardadasDelUsuario(final ResultListener<List<Noticia>> escuchadorDeLaVista) {
        daoNoticiaFirebase.pedirListaDeNoticiasGuardadasDelUsuario(new ResultListener<List<Noticia>>() {
            @Override
            public void finish(List<Noticia> resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        });
    }

    public void verficiarSiLaNoticiaEstaEnFirebase(Noticia noticia, final ResultListener<Boolean> escuchadorDeLaVista) {
        new DAONoticiaFirebase(context).verficiarSiLaNoticiaEstaEnGuardado(noticia, new ResultListener<Boolean>() {
            @Override
            public void finish(Boolean resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        });
    }

    public void verfificarSiElUsuarioYaReaccionoAlComentario(String reaccion, Integer idNoticia, String idComentario, final ResultListener<Boolean> escuchadorDeLaVista) {
        new DAONoticiaFirebase(context).verificarSiElUsuarioYaReaccionoAlComentario(reaccion, idNoticia, idComentario, new ResultListener<Boolean>() {
            @Override
            public void finish(Boolean resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        });
    }


    public void agregarLaNoticiaAGuardado(Noticia noticia, final ResultListener<Boolean> escuchadorDeLaVista) {
        new DAONoticiaFirebase(context).agregarLaNoticiaAGuardado(noticia, new ResultListener<Boolean>() {
            @Override
            public void finish(Boolean resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        });
    }

    public void publicarComentario(Integer idNoticia, Comentario comentario, final ResultListener<Boolean> escuchadorDeLaVista) {
        new DAONoticiaFirebase(context).publicarComentario(idNoticia, comentario, new ResultListener<Boolean>() {
            @Override
            public void finish(Boolean resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        });
    }

    public void publicarRespuestas(Integer idNoticia, String idComentario, Respuesta respuesta, final ResultListener<Boolean> escuhadorDeLaVista) {
        daoNoticiaFirebase.publicarRespuestas(idNoticia, idComentario, respuesta, new ResultListener<Boolean>() {
            @Override
            public void finish(Boolean resultado) {
                escuhadorDeLaVista.finish(resultado);
            }
        });
    }

    public void pedirListaDeComentariosDeUnaNoticia(Integer idNoticia, final ResultListener<List<Comentario>> escuchadorDeLaVista) {
        new DAONoticiaFirebase(context).pedirListaDeComentariosDeUnaNoticia(idNoticia, new ResultListener<List<Comentario>>() {
            @Override
            public void finish(List<Comentario> resultado) {

                escuchadorDeLaVista.finish(resultado);
            }
        });
    }

    public void pedirListaDeComentariosDeUnUsuario(final ResultListener<List<Comentario>> escuchadorDeLaVista) {
        new DAONoticiaFirebase(context).pedirListaDeComentariosDeUnUsuario(new ResultListener<List<Comentario>>() {
            @Override
            public void finish(List<Comentario> resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        });
    }

    public void darReaccionAUnComentario(String reaccion, Integer idNoticia, String idComentario, final ResultListener<Boolean> escuchadorDeLaVista) {
        new DAONoticiaFirebase(context).darReaccionAUnComentario(reaccion, idNoticia, idComentario, new ResultListener<Boolean>() {
            @Override
            public void finish(Boolean resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        });
    }

    public void descontarReaccionAUnComentario(String reaccion, Integer idNoticia, String idComentario, final ResultListener<Boolean> escuchadorDeLaVista) {
        new DAONoticiaFirebase(context).descontarReaccionAUnComentario(reaccion, idNoticia, idComentario, new ResultListener<Boolean>() {
            @Override
            public void finish(Boolean resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        });
    }

    public void verificarSiElUsuarioYaReaccionoALaRespuesta(String reaccion, Integer idNoticia, String idComentario, Integer idRespuesta, final ResultListener<Boolean> escuchadorDeLaVista) {
        daoNoticiaFirebase.verificarSiElUsuarioYaReaccionoALaRespuesta(reaccion, idNoticia, idComentario, idRespuesta, new ResultListener<Boolean>() {
            @Override
            public void finish(Boolean resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        });
    }

    public void darReaccionAUnaRespuesta(String reaccion, Integer idNoticia, String idComentario, Integer idRespuesta, final ResultListener<Boolean> escuchadorDeLaVista) {
        daoNoticiaFirebase.darReaccionAUnaRespuesta(reaccion, idNoticia, idComentario, idRespuesta, new ResultListener<Boolean>() {
            @Override
            public void finish(Boolean resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        });
    }

    public void descontarReaccionAUnaRespueta(String reaccion, Integer idNoticia, String idComentario, Integer idRespuesta,final ResultListener<Boolean> escuchadorDeLaVista) {
        daoNoticiaFirebase.descontarReaccionAUnaRespuesta(reaccion, idNoticia, idComentario, idRespuesta, new ResultListener<Boolean>() {
            @Override
            public void finish(Boolean resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        });
    }


}
