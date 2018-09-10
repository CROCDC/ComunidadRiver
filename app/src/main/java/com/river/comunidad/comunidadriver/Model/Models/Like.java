package com.river.comunidad.comunidadriver.Model.Models;

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
