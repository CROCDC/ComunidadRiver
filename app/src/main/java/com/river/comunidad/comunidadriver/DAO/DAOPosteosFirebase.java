package com.river.comunidad.comunidadriver.DAO;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.river.comunidad.comunidadriver.Model.Firebase.Posteo;
import com.river.comunidad.comunidadriver.Utils.ResultListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DAOPosteosFirebase {
    private Context context;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;




    public DAOPosteosFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public void publicarPosteo(Posteo posteo , final ResultListener<Boolean> escuchadorDelController){
        databaseReference = firebaseDatabase.getReference().child("Posteos");

        databaseReference.push().setValue(posteo);
    }

    public void obtenerPosteosDeLosUsuarios(final ResultListener<List<Posteo>> escuchadorDelController){
        databaseReference = firebaseDatabase.getReference().child("Posteos");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Posteo> listaDePosteos = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    listaDePosteos.add((Posteo) snapshot.getValue(Posteo.class));
                }
                escuchadorDelController.finish(listaDePosteos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void subirImagenDelPosteoAFireStorage(File file,final ResultListener<String> escuchadorDelController){
        UploadTask uploadTask = storageReference.child("Fotos").child("image.jpg").putFile(Uri.fromFile(file));

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                escuchadorDelController.finish(taskSnapshot.toString());
            }
        });

    }

}
