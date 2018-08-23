package com.river.comunidad.comunidadriver.Model.Models;

import java.util.List;

public class DisLike {
    private Integer cantDisLikes;
    private List<String> usuarios;

    public DisLike() {
    }

    public DisLike(Integer cantDisLikes, List<String> usuarios) {
        this.cantDisLikes = cantDisLikes;
        this.usuarios = usuarios;
    }

    public Integer getCantDisLikes() {
        return cantDisLikes;
    }

    public List<String> getUsuarios() {
        return usuarios;
    }
}
