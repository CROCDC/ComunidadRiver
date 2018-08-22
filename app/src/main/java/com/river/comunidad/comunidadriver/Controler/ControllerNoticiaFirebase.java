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
        new DAONoticiaFirebase(context).verficiarSiLaNoticiaEstaEnFirebase(noticia, new ResultListener<Boolean>() {
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
}
