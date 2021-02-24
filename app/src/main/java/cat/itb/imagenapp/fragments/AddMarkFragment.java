package cat.itb.imagenapp.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import cat.itb.imagenapp.R;
import cat.itb.imagenapp.models.Marcador;
import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;

public class AddMarkFragment extends Fragment {

    TextInputEditText nameEditText, descriptionEditText;
    MaterialButton addButton, addImageButton;
    private View rootView;
    private ImageView imageView;
    DatabaseReference imgRef;
    StorageReference storageReference;
    Bitmap thumb_bitmap;
    String nombreImagen;
    byte[] thumb_byte;
    File url;
    Double longitud, latitud;
    Bundle bundle;


    public AddMarkFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_add_marker, container, false);


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameEditText = rootView.findViewById(R.id.nombre_edit_text);
        descriptionEditText = rootView.findViewById(R.id.descripcion_edit_text);
        addButton = rootView.findViewById(R.id.add_button);
        addImageButton = rootView.findViewById(R.id.add_image_button);
        imageView = rootView.findViewById(R.id.imageView_imagen);

        bundle = getArguments();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprimirImagen();
                subirImagen();

            }
        });
        //Conectarse a la base de datos
        imgRef = FirebaseDatabase.getInstance().getReference().child("Marcadores");
        storageReference = FirebaseStorage.getInstance().getReference().child("img_comprimidas");

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(getContext(),AddMarkFragment.this);
            }
        });



    }

    private void subirImagen() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        nombreImagen = timestamp + ".jpeg";

        final StorageReference ref = storageReference.child(nombreImagen);
        UploadTask uploadTask = ref.putBytes(thumb_byte);
        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()){
                    throw Objects.requireNonNull(task.getException());
                }
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Uri downloadUri = task.getResult();

                LatLng latLng = new LatLng(bundle.getDouble("latitud"),bundle.getDouble("longitud"));
                Marcador marcador = new Marcador(nameEditText.getText().toString(),descriptionEditText.getText().toString(),downloadUri.toString(),latLng);
                String key= imgRef.push().getKey();
                imgRef.child(key).setValue(marcador);
                Toast.makeText(getContext(),"Imagen Subida",Toast.LENGTH_SHORT).show();
                MapsFragment fragment = new MapsFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });
    }

    private void comprimirImagen() {
        try {
            thumb_bitmap = new Compressor(getContext()).setMaxHeight(125).setMaxWidth(125)
                    .setQuality(50)
                    .compressToBitmap(url);

        }catch (IOException e){
            e.printStackTrace();
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        thumb_bitmap.compress(Bitmap.CompressFormat.JPEG,90,byteArrayOutputStream);
        thumb_byte = byteArrayOutputStream.toByteArray();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK){//Result_Ok importado de activities
            Uri imageUri = CropImage.getPickImageResultUri(getContext(),data);
            recortarImagen(imageUri);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                url = new File(resultUri.getPath());
                Picasso.with(getContext()).load(url).into(imageView);
            }
        }
    }

    private void recortarImagen(Uri imageUri) {
        CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON)
                .setRequestedSize(640,480)
                .setAspectRatio(2,1).start(getContext(),AddMarkFragment.this);
    }


}

