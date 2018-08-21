package com.river.comunidad.comunidadriver.Model.Models;

import java.io.Serializable;

/**
 * Created by TCR on 05/06/2018.
 */

public class Excerpt implements Serializable {
    private String rendered;//preview

    public String getRendered() {
        return rendered;
    }

    public void setRendered(String rendered) {
        this.rendered = rendered;
    }
}
