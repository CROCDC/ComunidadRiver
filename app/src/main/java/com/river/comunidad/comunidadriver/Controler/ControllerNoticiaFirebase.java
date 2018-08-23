package com.river.comunidad.comunidadriver.Controler;

import android.content.Context;

import com.river.comunidad.comunidadriver.DAO.DAONoticiaFirebase;
import com.river.comunidad.comunidadriver.Model.Models.Comentario;
import com.river.comunidad.comunidadriver.Model.Models.Noticia;
import com.river.comunidad.comunidadriver.Utils.ResultListener;

import java.util.List;

public class ControllerNoticiaFirebase {
    private Context context;

    public ControllerNoticiaFirebase(Context context) {
        this.context = context;
    }

    public void verficiarSiLaNoticiaEstaEnFirebase(Noticia noticia, final ResultListener<Boolean> escuchadorDeLaVista) {
        new DAONoticiaFirebase(context).verficiarSiLaNoticiaEstaEnGuardado(noticia, new ResultListener<Boolean>() {
            @Override
            public void finish(Boolean resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        });
    }

    public void verfificarSiElUsuarioYaLikeoElComentario(Integer idNoticia, Integer idComentario, String usuarioUID, final ResultListener<Boolean> escuchadorDeLaVista){
        new DAONoticiaFirebase(context).verificarSiElUsuarioYaLikeo(idNoticia, idComentario, usuarioUID, new ResultListener<Boolean>() {
            @Override
            public void finish(Boolean resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        });
    }
    public void verfificarSiElUsuarioYaDisLikeoElComentario(Integer idNoticia, Integer idComentario, String usuarioUID, final ResultListener<Boolean> escuchadorDeLaVista){
        new DAONoticiaFirebase(context).verificarSiElUsuarioYaDislikeo(idNoticia, idComentario, usuarioUID, new ResultListener<Boolean>() {
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

    public void pedirListaDeComentariosDeUnaNoticia(Integer idNoticia, final ResultListener<List<Comentario>> escuchadorDeLaVista) {
        new DAONoticiaFirebase(context).pedirListaDeComentariosDeUnaNoticia(idNoticia, new ResultListener<List<Comentario>>() {
            @Override
            public void finish(List<Comentario> resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        });
    }

    public void pedirListaDeComentariosDeUnUsuario(String usuarioUID, final ResultListener<List<Comentario>> escuchadorDeLaVista) {
        new DAONoticiaFirebase(context).pedirListaDeComentariosDeUnUsuario(usuarioUID, new ResultListener<List<Comentario>>() {
            @Override
            public void finish(List<Comentario> resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        });
    }

    public void darLikeAUnComentario(Integer idNoticia,Integer idComentario,final ResultListener<Boolean> escuchadorDeLaVista){
        new DAONoticiaFirebase(context).darLikeAUnComentario(idNoticia, idComentario, new ResultListener<Boolean>() {
            @Override
            public void finish(Boolean resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        });
    }
    public void descontarLikeAUnComentario(Integer idNoticia,Integer idComentario,String usuarioUID,final ResultListener<Boolean> escuchadorDeLaVista){
        new DAONoticiaFirebase(context).descontarLikeAUnComentario(idNoticia, idComentario, usuarioUID, new ResultListener<Boolean>() {
            @Override
            public void finish(Boolean resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        });
    }

    public void descontarDisLikeAUnComentario(Integer idNoticia,Integer idComentario,String usuarioUID,final ResultListener<Boolean> escuchadorDeLaVista){
        new DAONoticiaFirebase(context).descontarDisLikeAUnComentario(idNoticia, idComentario, usuarioUID, new ResultListener<Boolean>() {
            @Override
            public void finish(Boolean resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        });
    }

    public void darDisLikeAUnComentario(Integer idNoticia,Integer idComentario,final ResultListener<Boolean> escuchadorDeLaVista){
        new DAONoticiaFirebase(context).darDisLikeAUnComentario(idNoticia, idComentario, new ResultListener<Boolean>() {
            @Override
            public void finish(Boolean resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        });
    }

}
