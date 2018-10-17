package com.river.comunidad.comunidadriver.Model.Firebase;

import java.io.Serializable;
import java.util.List;

public class Like implements Serializable {
    private List<String> usuarios;

    public Like() {
    }

    public Like(List<String> usuarios) {
        this.usuarios = usuarios;
    }



    public List<String> getUsuarios() {
        return usuarios;
    }
}
