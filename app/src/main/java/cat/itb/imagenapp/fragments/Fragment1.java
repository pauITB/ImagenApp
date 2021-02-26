package cat.itb.imagenapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cat.itb.imagenapp.R;
import cat.itb.imagenapp.SwipeToDeleteCallBack;
import cat.itb.imagenapp.adapter.MarcadorsAdapter;
import cat.itb.imagenapp.database.FirebaseAPI;
import cat.itb.imagenapp.models.Marcador;

public class Fragment1 extends Fragment implements MarcadorsAdapter.ItemClickListener {


    RecyclerView rvMarcadors;
    MarcadorsAdapter myAdapter;
    DatabaseReference myRef;
    List<Marcador> marcadores = new ArrayList<>();

    public Fragment1() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_1, container, false);
        rvMarcadors = v.findViewById(R.id.recyclerMarcadores);
        rvMarcadors.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseAPI firebaseAPI = new FirebaseAPI();
        myRef = firebaseAPI.getReference();
        FirebaseRecyclerOptions<Marcador> options = new FirebaseRecyclerOptions.Builder<Marcador>().setQuery(myRef, Marcador.class).build();
        myAdapter = new MarcadorsAdapter(options);
        myAdapter.setContext(getContext());
        myAdapter.setClickListener(this);
        rvMarcadors.setAdapter(myAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallBack(myAdapter));
        itemTouchHelper.attachToRecyclerView(rvMarcadors);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    Marcador marcador = s.getValue(Marcador.class);
                    marcadores.add(marcador);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

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

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(),"Hola",Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putDouble("longitud",marcadores.get(position).getLongitud());
        bundle.putDouble("latitud",marcadores.get(position).getLatitud());
        MapsFragment fragment = new MapsFragment();
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }
}