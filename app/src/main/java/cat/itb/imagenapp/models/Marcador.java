package cat.itb.imagenapp.models;

import com.google.android.gms.maps.model.LatLng;

public class Marcador {
    String id;
    String nombre;
    String descripcion;
    String imagenURL;
    Double latitud;
    Double longitud;

    public Marcador() {
    }

    public Marcador(String nombre, String descripcion, String imagenURL, Double latitud, Double longitud) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagenURL = imagenURL;
        this.latitud = latitud;
        this.longitud = longitud;
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

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
