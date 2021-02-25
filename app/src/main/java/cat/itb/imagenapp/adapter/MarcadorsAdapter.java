package cat.itb.imagenapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import cat.itb.imagenapp.R;
import cat.itb.imagenapp.database.FirebaseAPI;
import cat.itb.imagenapp.models.Marcador;

public class MarcadorsAdapter extends FirebaseRecyclerAdapter<Marcador,MarcadorsAdapter.MarcadorHolder> {

    private FirebaseRecyclerOptions<Marcador> marcadores;
    private Context context ;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MarcadorsAdapter(@NonNull FirebaseRecyclerOptions<Marcador> options) {
        super(options);
        marcadores=options;
    }


    @Override
    protected void onBindViewHolder(@NonNull MarcadorHolder holder, int position, @NonNull Marcador model) {
        holder.bind(model);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MarcadorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marcador_item_view,parent,false);

        return new MarcadorsAdapter.MarcadorHolder(view);
    }

    public void deleteItem(int position) {
        FirebaseAPI firebaseAPI = new FirebaseAPI();
        firebaseAPI.deleteItem(marcadores.getSnapshots().get(position).getId());
    }

    public class MarcadorHolder extends RecyclerView.ViewHolder{
        ImageView fotoMarcador;
        TextView nombreMarcador;
        TextView descripcionMarcador;

        public MarcadorHolder(@NonNull View itemView) {
            super(itemView);
            nombreMarcador = itemView.findViewById(R.id.nombre_text_view);
            fotoMarcador = itemView.findViewById(R.id.imagen_marcador);
            descripcionMarcador = itemView.findViewById(R.id.descripcion_text_view);

        }

        public void bind(final Marcador model){
            nombreMarcador.setText(model.getNombre());
            descripcionMarcador.setText(model.getDescripcion());
            Picasso.with(getContext()).load(model.getImagenURL()).into(fotoMarcador);
        }
    }
}
