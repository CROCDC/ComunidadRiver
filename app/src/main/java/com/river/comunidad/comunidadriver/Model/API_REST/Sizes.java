package com.river.comunidad.comunidadriver.Model.API_REST;

import java.io.Serializable;

public class Sizes implements Serializable {
    private Thumbnail thumbnail;
    private Medium medium;
    private MediumLarge medium_large;

    public Medium getMedium() {
        return medium;
    }

    public MediumLarge getMedium_Large() {
        return medium_large;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }
}
