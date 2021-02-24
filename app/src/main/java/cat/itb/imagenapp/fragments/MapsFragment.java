package cat.itb.imagenapp.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import cat.itb.imagenapp.R;
import cat.itb.imagenapp.models.Marcador;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private static final int REQUEST_IMAGE_CAPTURE = 100;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private View rootView;
    private MapView mapView;
    private GoogleMap gMap;
    private FloatingActionButton floatingActionButton;
    DatabaseReference myRef;
    private List<Marcador> marcadores;


    FusedLocationProviderClient fusedLocationProviderClient;

    private MarkerOptions marker;
    private Bitmap fotoMarca;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView =inflater.inflate(R.layout.fragment_maps, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = rootView.findViewById(R.id.mapa);
        myRef = FirebaseDatabase.getInstance().getReference().child("Marcadores");
        floatingActionButton = rootView.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){

                    pedirPermiso();
                }

                enableMyLocation();
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null){
//                            Toast.makeText(getContext(),"Latitude"+location.getLatitude(),Toast.LENGTH_SHORT).show();
                            Bundle bundle = new Bundle();
                            bundle.putDouble("longitud",location.getLongitude());
                            bundle.putDouble("latitud",location.getLatitude());
                            AddMarkFragment fragment = new AddMarkFragment();
                            fragment.setArguments(bundle);
                            getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
                        }
                    }
                });


            }
        });


        if (mapView!=null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()){
                    Marcador marcador = s.getValue(Marcador.class);
                    marcadores.add(marcador);
                    for (int i = 0; i < marcadores.size(); i++) {

                        //TODO Afegim cada marcador al mapa
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void pedirPermiso() {
        ActivityCompat.requestPermissions(getActivity(),new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION_REQUEST_CODE);
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            if (gMap!=null){
                gMap.setMyLocationEnabled(true);
            }
        }
        else {
            pedirPermiso();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        gMap.setMaxZoomPreference(18);
        gMap.setMinZoomPreference(15);

        LatLng itb = new LatLng(41.4531987,2.1865311);
        marker = new MarkerOptions();
        marker.position(itb);
        marker.title("ITB");
        marker.snippet("Institut Tecnologic de Barcelona");
//        marker.icon(BitmapDescriptorFactory.fromResource(android.R.drawable.ic_dialog_info));
        marker.draggable(false);

        gMap.addMarker(marker);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(itb).zoom(15).bearing(0).tilt(30).build();

        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        gMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
//                Toast.makeText(getContext(),"Latitut:"+latLng.latitude,Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putDouble("longitud",latLng.longitude);
                bundle.putDouble("latitud",latLng.latitude);
                AddMarkFragment fragment = new AddMarkFragment();
                fragment.setArguments(bundle);
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
//                MarkerOptions marca = new MarkerOptions();
//                marca.position(latLng);
//                dispatchTakePictureIntent();
//                marca.icon(BitmapDescriptorFactory.fromBitmap(fotoMarca));
//                marca.draggable(false);
//                gMap.addMarker(marca);

            }
        });    }
        


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager())!= null){
            startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE){
            Bitmap image = (Bitmap) data.getExtras().get("data");
            fotoMarca=image;
        }
    }


}