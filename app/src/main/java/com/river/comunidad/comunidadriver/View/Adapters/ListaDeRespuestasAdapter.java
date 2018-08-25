package com.river.comunidad.comunidadriver.View.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.river.comunidad.comunidadriver.Model.Models.Respuesta;
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
    private NotificarClick NotificarClick;

    public ListaDeRespuestasAdapter(NotificarClick NotificarClick) {
        listaDeRespuestas = new ArrayList<>();
        this.NotificarClick = NotificarClick;

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

        NotificarClick.notificar();
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


        public RespuestasViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageViewUsuario = itemView.findViewById(R.id.circleImageViewUsuario_celdarespuesta);
            textViewNombreDeUsuario = itemView.findViewById(R.id.textViewNombreDeUsuario_celdarespuesta);
            textViewFechaDePublicacionDelComentario = itemView.findViewById(R.id.textViewFechaDePublicacionDelComentario_celdarespuesta);
            textViewContendioDelComentario = itemView.findViewById(R.id.textViewContenidoDelComentario_celdarespuesta);
            textViewCantidadDeLikes = itemView.findViewById(R.id.textViewCantidadDeLikesDelComentario_celdarespuesta);
            textViewCantidadDeDisLikes = itemView.findViewById(R.id.textViewCantidadDeDisLikes_celdarespuesta);
        }

        public void cargarRespuesta(Respuesta respuesta) {

            Helper.cargarImagenes(circleImageViewUsuario, context, respuesta.getUrlImagen());
            textViewNombreDeUsuario.setText(respuesta.getUsuario());
            textViewFechaDePublicacionDelComentario.setText(MiRelojDeArena.getTimeAgo(respuesta.getFechaDePublicacion()).toString());
            textViewContendioDelComentario.setText(respuesta.getTexto());
            textViewCantidadDeLikes.setText(respuesta.getLike().getCantLikes().toString());
            textViewCantidadDeDisLikes.setText(respuesta.getDisLike().getCantDisLikes().toString());


        }
    }

    public interface NotificarClick {
        public void notificar();
    }
}
