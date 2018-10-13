package com.river.comunidad.comunidadriver.Model.Firebase;

import java.util.List;

public class Like {
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
