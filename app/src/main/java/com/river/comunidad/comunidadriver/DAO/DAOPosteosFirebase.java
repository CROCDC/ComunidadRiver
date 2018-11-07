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
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.river.comunidad.comunidadriver.Model.Firebase.Posteo;
import com.river.comunidad.comunidadriver.Utils.ResultListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DAOPosteosFirebase {
    private Context context;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;


    public DAOPosteosFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public void publicarPosteo(Posteo posteo, final ResultListener<Boolean> escuchadorDelController) {
        databaseReference = firebaseDatabase.getReference().child("Posteos");

        databaseReference.push().setValue(posteo);

        escuchadorDelController.finish(true);
    }

    public void obtenerPosteosDeLosUsuarios(final ResultListener<List<Posteo>> escuchadorDelController) {
        databaseReference = firebaseDatabase.getReference().child("Posteos");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Posteo> listaDePosteos = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    listaDePosteos.add((Posteo) snapshot.getValue(Posteo.class));
                }
                escuchadorDelController.finish(listaDePosteos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void subirImageFileDelPosteoAFireStorage(File file, final ResultListener<String> escuchadorDelController, final ResultListener<Integer> escuchadorDelProceso) {
        String nombreDeLaImagen = UUID.randomUUID().toString();
        UploadTask progressListener = storageReference.child("Fotos").child(nombreDeLaImagen).putFile(Uri.fromFile(file));

        progressListener.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                storageReference.child("Fotos").child(nombreDeLaImagen).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        escuchadorDelController.finish(uri.toString());


                    }
                });
            }
        });

        progressListener.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                Double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                escuchadorDelProceso.finish(progress.intValue());

            }
        });

    }

    public void subirVideoDelPosteoAFireStorage(File file, final ResultListener<String> escuchadorDelController, final ResultListener<Integer> escuchadorDelProgreso) {
        String nombreDelVideo = UUID.randomUUID().toString();
        UploadTask progressListener = storageReference.child("videos").child(nombreDelVideo).putFile(Uri.fromFile(file));

        progressListener.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                storageReference.child("videos").child(nombreDelVideo).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        escuchadorDelController.finish(uri.toString());
                    }
                });

            }
        });

        progressListener.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                Double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                escuchadorDelProgreso.finish(progress.intValue());
            }
        });


    }

}
