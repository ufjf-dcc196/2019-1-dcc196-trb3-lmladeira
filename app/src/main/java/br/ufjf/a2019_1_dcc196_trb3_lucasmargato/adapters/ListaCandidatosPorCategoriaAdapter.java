package br.ufjf.a2019_1_dcc196_trb3_lucasmargato.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.ufjf.a2019_1_dcc196_trb3_lucasmargato.HeadhunterContract;
import br.ufjf.a2019_1_dcc196_trb3_lucasmargato.R;

public class ListaCandidatosPorCategoriaAdapter extends RecyclerView.Adapter<ListaCandidatosPorCategoriaAdapter.ViewHolder>   {
    private Cursor cursor;
    private ListaCandidatosPorCategoriaAdapter.OnItemClickListener listener;

    public ListaCandidatosPorCategoriaAdapter(Cursor c) {
        this.cursor = c;
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    public void setCursor(Cursor c){
        this.cursor = c;
        notifyDataSetChanged();
    }
    public void setOnItemClickListener(ListaCandidatosPorCategoriaAdapter.OnItemClickListener listener) { this.listener = listener;}

    @NonNull
    @Override
    public ListaCandidatosPorCategoriaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.layout_lista_candidatos_por_categoria, viewGroup, false);
        ListaCandidatosPorCategoriaAdapter.ViewHolder viewHolder = new ListaCandidatosPorCategoriaAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListaCandidatosPorCategoriaAdapter.ViewHolder viewHolder, int i) {
        int idxNome = cursor.getColumnIndexOrThrow(HeadhunterContract.Candidato.COLLUMN_NOME);
        int idxHoras = cursor.getColumnIndexOrThrow("horastotais");

        cursor.moveToPosition(i);

        viewHolder.itemNome.setText(cursor.getString(idxNome));
        viewHolder.itemHoras.setText(cursor.getString(idxHoras));

    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView itemNome, itemHoras;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemNome = itemView.findViewById(R.id.textListaNome);
            itemHoras = itemView.findViewById(R.id.textListaHoras);


            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if(position !=RecyclerView.NO_POSITION){
                listener.onItemClick(v, position);
            }
        }

    }
}
