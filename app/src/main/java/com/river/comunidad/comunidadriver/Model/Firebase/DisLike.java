package com.river.comunidad.comunidadriver.Model.Firebase;

import java.util.List;

public class DisLike {

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
