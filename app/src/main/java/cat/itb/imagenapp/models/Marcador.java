package cat.itb.imagenapp.models;

import com.google.android.gms.maps.model.LatLng;

public class Marcador {
    String nombre;
    String descripcion;
    String imagenURL;
    LatLng latLng;

    public Marcador(String nombre, String descripcion, String imagenURL, LatLng latLng) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagenURL = imagenURL;
        this.latLng = latLng;
    }

    public Marcador(String nombre, String imagenURL, LatLng latLng) {
        this.nombre = nombre;
        this.imagenURL = imagenURL;
        this.latLng = latLng;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagenURL() {
        return imagenURL;
    }

    public void setImagenURL(String imagenURL) {
        this.imagenURL = imagenURL;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
