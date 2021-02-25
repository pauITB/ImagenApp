package cat.itb.imagenapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

import cat.itb.imagenapp.R;
import cat.itb.imagenapp.SwipeToDeleteCallBack;
import cat.itb.imagenapp.adapter.MarcadorsAdapter;
import cat.itb.imagenapp.database.FirebaseAPI;
import cat.itb.imagenapp.models.Marcador;

public class Fragment1 extends Fragment {


    RecyclerView rvMarcadors;
    MarcadorsAdapter myAdapter;
    DatabaseReference myRef;
    public Fragment1() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_1, container, false);
        rvMarcadors = v.findViewById(R.id.recyclerMarcadores);
        rvMarcadors.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseAPI firebaseAPI = new FirebaseAPI();
        myRef = firebaseAPI.getReference();
        FirebaseRecyclerOptions<Marcador> options = new FirebaseRecyclerOptions.Builder<Marcador>().setQuery(myRef,Marcador.class).build();
        myAdapter = new MarcadorsAdapter(options);
        myAdapter.setContext(getContext());
        rvMarcadors.setAdapter(myAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallBack(myAdapter));
        itemTouchHelper.attachToRecyclerView(rvMarcadors);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        myAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        myAdapter.stopListening();
    }
}