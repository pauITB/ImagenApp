package cat.itb.imagenapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import cat.itb.imagenapp.R;

public class AddMarkFragment extends Fragment {

    TextInputEditText nameEditText, descriptionEditText;
    MaterialButton addButton;
    private View rootView;
    DatabaseReference datRef;
    StorageReference imgRef;


//TODO Conectar FireBase y Storage https://drive.google.com/file/d/1AL35FD1ZGKODQQdmYIAiKfuVEZIo9KN7/view pg 9 

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

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

    }
}
