package com.river.comunidad.comunidadriver.Controller;

import com.river.comunidad.comunidadriver.DAO.DAOPosteosFirebase;
import com.river.comunidad.comunidadriver.Model.Firebase.Posteo;
import com.river.comunidad.comunidadriver.Utils.ResultListener;

import java.util.List;

public class ControllerPosteosFirebase {
    private DAOPosteosFirebase daoPosteosFirebase;
    public ControllerPosteosFirebase(){
        daoPosteosFirebase = new DAOPosteosFirebase();
    }

    public void publicarPosteo(Posteo posteo, final ResultListener<Boolean> escuchadorDeLaVista){
        daoPosteosFirebase.publicarPosteo(posteo, new ResultListener<Boolean>() {
            @Override
            public void finish(Boolean resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        });
    }

    public void obtenerPosteosDeLosUsuarios(final ResultListener<List<Posteo>> escuchadorDeLaVista){
        daoPosteosFirebase.obtenerPosteosDeLosUsuarios(new ResultListener<List<Posteo>>() {
            @Override
            public void finish(List<Posteo> resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        });
    }
}
