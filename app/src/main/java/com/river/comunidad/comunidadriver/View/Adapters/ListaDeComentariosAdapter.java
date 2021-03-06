package com.river.comunidad.comunidadriver.View.Adapters;

import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.river.comunidad.comunidadriver.Model.Firebase.Comentario;
import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.Utils.Helper;
import com.river.comunidad.comunidadriver.Utils.MiRelojDeArena;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListaDeComentariosAdapter extends RecyclerView.Adapter {

    private List<Comentario> listaDeComentarios;
    private Context context;
    private long mLastClickTime;
    private NotificadorHaciaImplementadorDeComentariosAdapter notificadorHaciaImplementadorDeComentariosAdapter;

    public ListaDeComentariosAdapter(NotificadorHaciaImplementadorDeComentariosAdapter notificadorHaciaImplementadorDeComentariosAdapter) {
        listaDeComentarios = new ArrayList<>();
        this.notificadorHaciaImplementadorDeComentariosAdapter = notificadorHaciaImplementadorDeComentariosAdapter;
    }

    public void setListaDeComentarios(List<Comentario> listaDeComentarios) {
        this.listaDeComentarios = listaDeComentarios;
        notifyDataSetChanged();

    }


    public List<Comentario> getListaDeComentarios() {
        return listaDeComentarios;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View viewDeLaCelda = layoutInflater.inflate(R.layout.celda_comentario, viewGroup, false);

        ComentariosViewHolder comentariosViewHolder = new ComentariosViewHolder(viewDeLaCelda);

        return comentariosViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Comentario comentario = listaDeComentarios.get(i);

        ComentariosViewHolder comentariosViewHolder = (ComentariosViewHolder) viewHolder;

        comentariosViewHolder.cargarComentario(comentario);


    }

    @Override
    public int getItemCount() {
        return listaDeComentarios.size();
    }

    public class ComentariosViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView circleImageViewUsuario;
        private TextView textViewNombreDeUsuario;
        private TextView textViewFechaDePublicacionDelComentario;
        private TextView textViewContendioDelComentario;
        private ImageView imageViewButtonLike;
        private ImageView imageViewButtonDisLike;
        private TextView textViewCantidadDeLikes;
        private TextView textViewCantidadDeDisLikes;
        private ImageView imageViewButtonResponder;
        private LinearLayout linearLayoutContenedorCampoComentario;
        private EditText editTextRespuestaDelUsuario;
        private CardView cardViewButtonPublicarRespuesta;
        private RecyclerView recyclerViewListaDeRespuestas;

        private Integer cantLikes;
        private Integer cantDisLikes;

        public ComentariosViewHolder(@NonNull View itemView) {
            super(itemView);

            circleImageViewUsuario = itemView.findViewById(R.id.circleImageViewUsuario_celdacomentario);
            textViewNombreDeUsuario = itemView.findViewById(R.id.textViewNombreDeUsuario_celdacomentario);
            textViewFechaDePublicacionDelComentario = itemView.findViewById(R.id.textViewFechaDePublicacionDelComentario_celdacomentario);
            textViewContendioDelComentario = itemView.findViewById(R.id.textViewContenidoDelComentario_celdacomentario);
            imageViewButtonLike = itemView.findViewById(R.id.imageViewButtonLike_celdacomentario);
            imageViewButtonDisLike = itemView.findViewById(R.id.imageViewButtonDisLike_celdacomentario);
            textViewCantidadDeLikes = itemView.findViewById(R.id.textViewCantidadDeLikesDelComentario_celdacomentario);
            textViewCantidadDeDisLikes = itemView.findViewById(R.id.textViewCantidadDeDisLikes_celdacomentario);
            imageViewButtonResponder = itemView.findViewById(R.id.imageViewButtonResponder_celdacomentario);
            linearLayoutContenedorCampoComentario = itemView.findViewById(R.id.linearLayoutContenedorCampoDeRespuesta_celdacomentario);
            editTextRespuestaDelUsuario = itemView.findViewById(R.id.editTextRespuestaDelUsuario_celdacomentario);
            cardViewButtonPublicarRespuesta = itemView.findViewById(R.id.cardViewButtonPublicarRespuesta_celdacomentario);
            recyclerViewListaDeRespuestas = itemView.findViewById(R.id.recyclerViewListaDeRespuestas_celdacomentario);

        }


        public void cargarComentario(final Comentario comentario) {

            ListaDeRespuestasAdapter listaDeRespuestasAdapter = new ListaDeRespuestasAdapter(new ListaDeRespuestasAdapter.NotificadorHaciaListaDeComentariosAdapter() {
                @Override
                public void notificarTouchLikeButton(Integer idRespuesta) {
                    notificadorHaciaImplementadorDeComentariosAdapter.notificarTouchLikeButtonRespuesta(idRespuesta, comentario.getIdComentario(), listaDeComentarios.get(getAdapterPosition()).getIdNoticia());
                }

                @Override
                public void notificarTouchDisLikeButton(Integer idRespuesta) {
                    notificadorHaciaImplementadorDeComentariosAdapter.notificarTouchDisLikeButtonRespuesta(idRespuesta, comentario.getIdComentario(), listaDeComentarios.get(getAdapterPosition()).getIdNoticia());
                }
            });

            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true) {
                //UTILIZO ESTO PARA DESHABILITAR LA POSIBILIDAD DE SCROLLEAR EN EL RECYCLERVIEW
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };

            if (comentario.getListaDeRespuestas() != null) {
                listaDeRespuestasAdapter.setListaDeRespuestas(comentario.getListaDeRespuestas());

            }

            recyclerViewListaDeRespuestas.setLayoutManager(linearLayoutManager);

            recyclerViewListaDeRespuestas.setAdapter(listaDeRespuestasAdapter);

            Helper.cargarImagenes(circleImageViewUsuario, context, comentario.getUrlImagen());
            textViewNombreDeUsuario.setText(comentario.getUsuario());
            textViewContendioDelComentario.setText(comentario.getTexto());

            try {
                cantLikes = comentario.getLike().getUsuarios().size();

            } catch (Exception e) {
                cantLikes = 0;
            }

            try {
                cantDisLikes = comentario.getDisLike().getUsuarios().size();
            } catch (Exception e) {
                cantDisLikes = 0;

            }

            textViewCantidadDeLikes.setText(cantLikes.toString());
            textViewCantidadDeDisLikes.setText(cantDisLikes.toString());
            textViewFechaDePublicacionDelComentario.setText(MiRelojDeArena.getTimeAgo(comentario.getFechaDePublicacion()));


            imageViewButtonLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    notificadorHaciaImplementadorDeComentariosAdapter.notificarTouchLikeButton(comentario.getIdComentario(), listaDeComentarios.get(getAdapterPosition()).getIdNoticia());
                }
            });


            imageViewButtonDisLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    notificadorHaciaImplementadorDeComentariosAdapter.notificarTouchDisLikeButton(comentario.getIdComentario(), listaDeComentarios.get(getAdapterPosition()).getIdNoticia());
                }
            });


            imageViewButtonResponder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        linearLayoutContenedorCampoComentario.setVisibility(View.VISIBLE);
                    } else {
                        FancyToast.makeText(context, "debe estar logueado para acceder a esta funcion", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

                    }

                }
            });

            cardViewButtonPublicarRespuesta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!editTextRespuestaDelUsuario.getText().toString().equals("")) {
                        notificadorHaciaImplementadorDeComentariosAdapter.notificarTouchPublicarButton(editTextRespuestaDelUsuario.getText().toString(), comentario.getIdComentario(), listaDeComentarios.get(getAdapterPosition()).getIdNoticia());
                        linearLayoutContenedorCampoComentario.setVisibility(View.GONE);
                        editTextRespuestaDelUsuario.setText("");
                    } else {
                        FancyToast.makeText(context, "Por favor escriba una respuestas antes de publicarla", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

                    }
                }

            });
        }
    }

    public interface NotificadorHaciaImplementadorDeComentariosAdapter {
        public void notificarTouchLikeButtonRespuesta(Integer idRespuesta, String idComentario, Integer idNoticia);

        public void notificarTouchDisLikeButtonRespuesta(Integer idRespuesta, String idComentario, Integer idNoticia);

        public void notificarTouchLikeButton(String idComentario, Integer idNoticia);

        public void notificarTouchDisLikeButton(String idComentario, Integer idNoticia);

        public void notificarTouchPublicarButton(String texto, String idComentario, Integer idNoticia);
    }

}
