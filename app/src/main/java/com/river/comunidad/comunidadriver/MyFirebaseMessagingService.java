package com.river.comunidad.comunidadriver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.river.comunidad.comunidadriver.Controller.ControllerNoticiaRetrofit;
import com.river.comunidad.comunidadriver.Model.Models.ListadoDeNoticias;
import com.river.comunidad.comunidadriver.Model.Models.Noticia;
import com.river.comunidad.comunidadriver.Utils.MySingleton;
import com.river.comunidad.comunidadriver.Utils.ResultListener;
import com.river.comunidad.comunidadriver.View.Activitys.DetalleDeUnaNoticiaActivity;

import java.util.ArrayList;
import java.util.List;


public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private static final String CLAVE_ID = "id";
    private static final String CLAVE_TITULO = "title";

    Notification.Builder mBuilder;
    NotificationCompat.Builder nmBuilder;



    private String id = "";
    private String titulo = "";
    private String descripcion = "";

    private Intent intent;

    private Bitmap bitmap;

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {


        if (remoteMessage.getData().size() > 0) {


            id = remoteMessage.getData().get(CLAVE_ID);
            titulo = remoteMessage.getData().get(CLAVE_TITULO);


        }

        if (id.equals("0")) {
            Notification.Builder mBuilder = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setStyle(new Notification.BigTextStyle()
                            .bigText("texto grande que se muesta cuando se despliega la notificacion"))
                    .setContentTitle(titulo)
                    .setContentText("jajajja")
                    //.setSound(defaultSoundUri)
                    .setAutoCancel(true);

            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            mNotificationManager.notify(0, mBuilder.build());



        } else {
            new ControllerNoticiaRetrofit(getApplicationContext()).pedirNoticiaPorID(Integer.valueOf(id), new ResultListener<Noticia>() {
                @Override
                public void finish(final Noticia noticia) {

                    intent = new Intent(MyFirebaseMessagingService.this, DetalleDeUnaNoticiaActivity.class);

                    final NotificationCompat.BigPictureStyle notiStyle = new NotificationCompat.BigPictureStyle();


                    Bundle bundle = new Bundle();

                    List<Noticia> listaDeNoticias = new ArrayList<>();
                    listaDeNoticias.add(noticia);
                    ListadoDeNoticias listadoDeNoticias = new ListadoDeNoticias(listaDeNoticias);

                    bundle.putSerializable(DetalleDeUnaNoticiaActivity.CLAVE_LISTADODENOTICIAS, listadoDeNoticias);
                    bundle.putSerializable(DetalleDeUnaNoticiaActivity.CLAVE_POSICION, 0);


                    final PendingIntent pendingIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

                    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    nmBuilder = new NotificationCompat.Builder(MyFirebaseMessagingService.this)
                            .setSmallIcon(R.drawable.logofondonegropequeno)
                            .setStyle(notiStyle)
                            .setContentTitle(noticia.getTitle().getRendered())
                            .setContentText(noticia.getExcerpt().getRendered())
                            .setSound(defaultSoundUri)
                            .setContentIntent(pendingIntent);



                   ImageRequest imageRequest = new ImageRequest(noticia.getEmbedded().getListaDeImagenes().get(0).getMedia_details().getSizes().getMedium().getSource_url(), new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {

                            nmBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(response));

                            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                            notificationManager.notify(0, nmBuilder.build());

                        }
                    }, 0, 0, null, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });


                    MySingleton.getmInstance(getApplicationContext()).addToRequestQue(imageRequest);


                }


            });
        }
    }


}
