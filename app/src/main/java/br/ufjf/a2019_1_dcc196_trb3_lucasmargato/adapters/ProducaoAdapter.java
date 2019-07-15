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

public class ProducaoAdapter extends RecyclerView.Adapter<ProducaoAdapter.ViewHolder>  {
    private Cursor cursor;
    private ProducaoAdapter.OnItemClickListener listener;
    private ProducaoAdapter.OnItemLongClickListener listenerl;

    public ProducaoAdapter(Cursor c) {
        this.cursor = c;
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    public interface OnItemLongClickListener {
        void onItemLongClick(View itemView, int position);
    }

    public void setCursor(Cursor c){
        this.cursor = c;
        notifyDataSetChanged();
    }
    public void setOnItemClickListener(ProducaoAdapter.OnItemClickListener listener) { this.listener = listener; }
    public void setOnItemLongClickListener(OnItemLongClickListener listener) { this.listenerl = listener; }

    @NonNull
    @Override
    public ProducaoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.layout_producao, viewGroup, false);
        ProducaoAdapter.ViewHolder viewHolder = new ProducaoAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProducaoAdapter.ViewHolder viewHolder, int i) {
        int idxID = cursor.getColumnIndexOrThrow(HeadhunterContract.Producao._ID);
        int idxTitulo = cursor.getColumnIndexOrThrow(HeadhunterContract.Producao.COLLUMN_TITULO);
        int idxDescricao = cursor.getColumnIndexOrThrow(HeadhunterContract.Producao.COLLUMN_DESCRICAO);
        int idxInicio = cursor.getColumnIndexOrThrow(HeadhunterContract.Producao.COLLUMN_INICIO);
        int idxFim = cursor.getColumnIndexOrThrow(HeadhunterContract.Producao.COLLUMN_FIM);
        int idxCategoria = cursor.getColumnIndexOrThrow(HeadhunterContract.Producao.COLLUMN_CATEGORIA);

        cursor.moveToPosition(i);

        viewHolder.itemID.setText(cursor.getString(idxID));
        viewHolder.itemTitulo.setText(cursor.getString(idxTitulo));
        viewHolder.itemDescricao.setText(cursor.getString(idxDescricao));
        viewHolder.itemInicio.setText(cursor.getString(idxInicio));
        viewHolder.itemFim.setText(cursor.getString(idxFim));
        viewHolder.itemCategoria.setText(cursor.getString(idxCategoria));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView itemID, itemTitulo, itemDescricao, itemInicio, itemFim, itemCategoria;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemID = itemView.findViewById(R.id.textIDProducao);
            itemTitulo = itemView.findViewById(R.id.textTitulo);
            itemDescricao = itemView.findViewById(R.id.textDescricao);
            itemInicio = itemView.findViewById(R.id.textInicio);
            itemFim = itemView.findViewById(R.id.textFim);
            itemCategoria = itemView.findViewById(R.id.textCategoriaProducao);

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

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listenerl != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listenerl.onItemLongClick(itemView, position);
                        }
                    }
                    return true;
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

        public boolean onLongClick(View v) {
            if (listenerl != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listenerl.onItemLongClick(itemView, position);
                }
            }
            return true;
        }

    }
}
