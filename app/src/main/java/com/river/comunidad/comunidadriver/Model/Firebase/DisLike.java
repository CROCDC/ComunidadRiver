package com.river.comunidad.comunidadriver.Model.Firebase;

import java.io.Serializable;
import java.util.List;

public class DisLike implements Serializable {

    private List<String> usuarios;

    public DisLike() {
    }

    public DisLike(List<String> usuarios) {
        this.usuarios = usuarios;
    }


    public List<String> getUsuarios() {
        return usuarios;
    }
}
