package com.river.comunidad.comunidadriver.View.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.river.comunidad.comunidadriver.Model.Firebase.Posteo;

import java.util.List;

public class ListaDePosteosAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Posteo> listaDePosteos;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //View viewDeLaCelda = layoutInflater.inflate()

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
