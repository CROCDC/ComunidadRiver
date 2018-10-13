package com.river.comunidad.comunidadriver.DAO;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DAOPosteosFirebase {
    private Context context;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public DAOPosteosFirebase(Context context){
        this.context = context;
        firebaseDatabase = FirebaseDatabase.getInstance();

    }

    public void obtenerPosteosDeLosUsuarios(){
        databaseReference = firebaseDatabase.getReference().child("videos");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
