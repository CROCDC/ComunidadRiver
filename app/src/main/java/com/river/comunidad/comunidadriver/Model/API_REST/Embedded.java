package com.river.comunidad.comunidadriver.Model.API_REST;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Embedded implements Serializable {
    @SerializedName("wp:featuredmedia")
    @Expose
    private List<wpfeaturedmedia> listaDeImagenes;

    public List<wpfeaturedmedia> getListaDeImagenes() {
        return listaDeImagenes;
    }
}
