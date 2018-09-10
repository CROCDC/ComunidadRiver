package com.river.comunidad.comunidadriver;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.river.comunidad.comunidadriver.Controller.ContrellerApiPushNotification;


public class FirebaseInstance extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {

        try {
            new ContrellerApiPushNotification().insertarToken();
        }catch (Exception e){
            Log.d(e.getMessage(),"notificaciones");
        }

    }



}
