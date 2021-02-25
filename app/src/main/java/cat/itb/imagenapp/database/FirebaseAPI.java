package cat.itb.imagenapp.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import cat.itb.imagenapp.models.Marcador;

public class FirebaseAPI {
     FirebaseDatabase database = FirebaseDatabase.getInstance();
     DatabaseReference reference= database.getReference("Marcadores");
    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("img_comprimidas");

    public FirebaseAPI() {
    }

    public DatabaseReference getReference() {
        return reference;
    }

    public DatabaseReference getReference(String s) {
        return database.getReference(s);
    }

    public void setReference(String marcador) {
        reference= database.getReference(marcador);
    }

    public void deleteItem(String key){
        reference.child(key).removeValue();
    }



    public void insert(Marcador marcador){
        String key = reference.push().getKey();
        marcador.setId(key);
        reference.child(key).setValue(marcador);
    }
}
