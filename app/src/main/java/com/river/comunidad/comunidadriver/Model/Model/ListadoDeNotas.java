package com.river.comunidad.comunidadriver.Model.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by TCR on 05/06/2018.
 */

public class ListadoDeNotas implements Serializable {
    private List<Noticia> array;

    public ListadoDeNotas(List<Noticia> array) {
        this.array = array;
    }

    public List<Noticia> getArray() {
        return array;
    }
}
