package com.river.comunidad.comunidadriver.Model.API_REST;

import java.io.Serializable;
import java.util.List;

/**
 * Created by TCR on 05/06/2018.
 */

public class ListadoDeNoticias implements Serializable {
    private List<Noticia> array;

    public ListadoDeNoticias(List<Noticia> array) {
        this.array = array;
    }

    public List<Noticia> getArray() {
        return array;
    }
}
