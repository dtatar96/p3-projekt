package ffos.p3.ontologija;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


//Za zakomentiratu Filter metodu treba vratiti "implements Filterable"
public class AdapterListe extends RecyclerView.Adapter<AdapterListe.Red> {

    private List<Ontologija> podaci;
    private List<Ontologija> podaciTemp;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // Podatke proslijedimo kroz konstruktor
    public AdapterListe(Context context) {
        this.mInflater = LayoutInflater.from(context);
        podaci = new ArrayList<>();
    }

    // napuni predložak red (datoteka red_liste.xml)
    @Override
    public Red onCreateViewHolder(ViewGroup roditelj, int viewType) {
        podaciTemp = new ArrayList<>(podaci);
        View view = mInflater.inflate(R.layout.red_liste, roditelj, false);
        return new Red(view);
    }

    // Veže podatke za svaki red
    @Override
    public void onBindViewHolder(Red red, int position) {
        Ontologija o = podaci.get(position);
        red.naziv.setText(o.getNaziv());
        red.autor.setText(o.getAutor());
        red.producent.setText(o.getProducent());
        red.trajanje.setText(o.getTrajanje());
    }

    // Ukupan broj redova (mora biti implementirano)
    @Override
    public int getItemCount() {
        return podaci==null ? 0 : podaci.size();
    }

    // Pohranjuje i reciklira pogled kako se prolazi kroz listu
    public class Red extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView naziv;
        private TextView autor;
        private TextView producent;
        private TextView trajanje;

        Red(View itemView) {
            super(itemView);
            naziv = itemView.findViewById(R.id.naziv);
            autor = itemView.findViewById(R.id.tip);
            producent = itemView.findViewById(R.id.opis);
            trajanje = itemView.findViewById(R.id.anotacija);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // klikom na listu dobijemo samo poziciju koju stavku liste smo odabrali.
    // Ova metoda pomaže da na osnovu pozicije dobijemo cijeli objekt u toj stavci
    public Ontologija getItem(int id) {
        return podaci.get(id);
    }

    public void setPodaci(List<Ontologija> itemList) {
        this.podaci = itemList;
    }

    // dopusti hvatanje odabira (klik/dotakni)
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // potrebno kako bi mogli hvatati klikove/dodire
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    /*
    ***Filter način***
    @Override
    public Filter getFilter() {
        return new Filter() {
            //background
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Ontologija> filteredList = new ArrayList<>();
                if (constraint.toString().isEmpty()) {
                    //filteredList.addAll(podaciTemp);
                } else {
                        for (Ontologija element : podaciTemp) {
                            if (element.getNaziv().toLowerCase().contains(constraint.toString().toLowerCase())) {
                                filteredList.add(element);
                            }
                        }
                    }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            //ui
            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
                podaci.clear();
                podaci.addAll((Collection<? extends Ontologija>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }*/
}
