package com.river.comunidad.comunidadriver.Model.Models;

import java.util.List;

public class Like {
    private Integer cantLikes;
    private List<String> usuarios;

    public Like() {
    }

    public Like(Integer cantLikes, List<String> usuarios) {
        this.cantLikes = cantLikes;
        this.usuarios = usuarios;
    }

    public Integer getCantLikes() {
        return cantLikes;
    }

    public void setCantLikes(Integer cantLikes) {
        this.cantLikes = cantLikes;
    }

    public List<String> getUsuarios() {
        return usuarios;
    }
}
