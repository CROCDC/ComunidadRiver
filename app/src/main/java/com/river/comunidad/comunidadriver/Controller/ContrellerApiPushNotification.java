package com.river.comunidad.comunidadriver.Controller;

import com.river.comunidad.comunidadriver.DAO.DAOApiPushNotification;

public class ContrellerApiPushNotification {

    public void insertarToken(){
        new DAOApiPushNotification().insertarToken();
    }
}
