package com.river.comunidad.comunidadriver.View.Adapters;

import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.river.comunidad.comunidadriver.Model.Firebase.Respuesta;
import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.Utils.Helper;
import com.river.comunidad.comunidadriver.Utils.MiRelojDeArena;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListaDeRespuestasAdapter extends RecyclerView.Adapter {

    private List<Respuesta> listaDeRespuestas;
    private Context context;
    private RecyclerView recyclerView;
    private NotificadorHaciaListaDeComentariosAdapter notificadorHaciaListaDeComentariosAdapter;

    private long mLastClickTime;


    public ListaDeRespuestasAdapter(NotificadorHaciaListaDeComentariosAdapter NotificadorHaciaListaDeComentariosAdapter) {
        listaDeRespuestas = new ArrayList<>();
        this.notificadorHaciaListaDeComentariosAdapter = NotificadorHaciaListaDeComentariosAdapter;

    }

    public void setListaDeRespuestas(List<Respuesta> listaDeRespuestas) {
        this.listaDeRespuestas = listaDeRespuestas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View viewDeLaCelda = layoutInflater.inflate(R.layout.celda_respuesta, viewGroup, false);

        RespuestasViewHolder respuestasViewHolder = new RespuestasViewHolder(viewDeLaCelda);


        return respuestasViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Respuesta respuesta = listaDeRespuestas.get(i);

        RespuestasViewHolder respuestasViewHolder = (RespuestasViewHolder) viewHolder;

        respuestasViewHolder.cargarRespuesta(respuesta);


    }

    @Override
    public int getItemCount() {
        return listaDeRespuestas.size();
    }

    public class RespuestasViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView circleImageViewUsuario;
        private TextView textViewNombreDeUsuario;
        private TextView textViewFechaDePublicacionDelComentario;
        private TextView textViewContendioDelComentario;
        private TextView textViewCantidadDeLikes;
        private TextView textViewCantidadDeDisLikes;
        private ImageView imageViewLikeButton;
        private ImageView imageViewDisLikeButton;

        private Integer cantLikes;
        private Integer cantDisLikes;


        public RespuestasViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageViewUsuario = itemView.findViewById(R.id.circleImageViewUsuario_celdarespuesta);
            textViewNombreDeUsuario = itemView.findViewById(R.id.textViewNombreDeUsuario_celdarespuesta);
            textViewFechaDePublicacionDelComentario = itemView.findViewById(R.id.textViewFechaDePublicacionDelComentario_celdarespuesta);
            textViewContendioDelComentario = itemView.findViewById(R.id.textViewContenidoDelComentario_celdarespuesta);
            textViewCantidadDeLikes = itemView.findViewById(R.id.textViewCantidadDeLikesDelComentario_celdarespuesta);
            textViewCantidadDeDisLikes = itemView.findViewById(R.id.textViewCantidadDeDisLikes_celdarespuesta);
            imageViewLikeButton = itemView.findViewById(R.id.imageViewButtonMeGusta_celdarespuesta);
            imageViewDisLikeButton = itemView.findViewById(R.id.imageViewButtonNoMeGusta_celdarespuesta);


        }

        public void cargarRespuesta(final Respuesta respuesta) {

            Helper.cargarImagenes(circleImageViewUsuario, context, respuesta.getUrlImagen());
            textViewNombreDeUsuario.setText(respuesta.getUsuario());
            textViewFechaDePublicacionDelComentario.setText(MiRelojDeArena.getTimeAgo(respuesta.getFechaDePublicacion()).toString());
            textViewContendioDelComentario.setText(respuesta.getTexto());

            try {
                cantLikes = respuesta.getLike().getUsuarios().size();

            } catch (Exception e) {
                cantLikes = 0;
            }

            try {
                cantDisLikes = respuesta.getDisLike().getUsuarios().size();
            } catch (Exception e) {
                cantDisLikes = 0;
            }

            textViewCantidadDeLikes.setText(cantLikes.toString());
            textViewCantidadDeDisLikes.setText(cantDisLikes.toString());

            imageViewLikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    notificadorHaciaListaDeComentariosAdapter.notificarTouchLikeButton(getAdapterPosition());
                }
            });

            imageViewDisLikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    notificadorHaciaListaDeComentariosAdapter.notificarTouchDisLikeButton(getAdapterPosition());
                }
            });

        }
    }

    public interface NotificadorHaciaListaDeComentariosAdapter {
        public void notificarTouchLikeButton(Integer idRespuesta);

        public void notificarTouchDisLikeButton(Integer idRespuesta);

    }
}
