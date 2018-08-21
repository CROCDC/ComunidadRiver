package com.river.comunidad.comunidadriver.Model.Models;

import java.io.Serializable;

/**
 * Created by TCR on 05/06/2018.
 */

public class wpfeaturedmedia implements Serializable {
    private String source_url;//pedido de imagenes
    private Media_details media_details;

    public String getsource_url() {
        return source_url;
    }

    public Media_details getMedia_details() {
        return media_details;
    }
}
