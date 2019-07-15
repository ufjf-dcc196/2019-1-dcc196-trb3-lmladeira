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

public class AtividadeAdapter extends RecyclerView.Adapter<AtividadeAdapter.ViewHolder>  {
    private Cursor cursor;
    private AtividadeAdapter.OnItemClickListener listener;
    private AtividadeAdapter.OnItemLongClickListener listenerl;

    public AtividadeAdapter(Cursor c) {
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
    public void setOnItemClickListener(AtividadeAdapter.OnItemClickListener listener) { this.listener = listener; }
    public void setOnItemLongClickListener(OnItemLongClickListener listener) { this.listenerl = listener; }

    @NonNull
    @Override
    public AtividadeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.layout_atividade, viewGroup, false);
        AtividadeAdapter.ViewHolder viewHolder = new AtividadeAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AtividadeAdapter.ViewHolder viewHolder, int i) {
        int idxID = cursor.getColumnIndexOrThrow(HeadhunterContract.Atividade._ID);
        int idxDescricao = cursor.getColumnIndexOrThrow(HeadhunterContract.Atividade.COLLUMN_DESCRICAO);
        int idxData = cursor.getColumnIndexOrThrow(HeadhunterContract.Atividade.COLLUMN_DATA);
        int idxHoras = cursor.getColumnIndexOrThrow(HeadhunterContract.Atividade.COLLUMN_HORAS);


        cursor.moveToPosition(i);

        viewHolder.itemID.setText(cursor.getString(idxID));
        viewHolder.itemDescricao.setText(cursor.getString(idxDescricao));
        viewHolder.itemData.setText(cursor.getString(idxData));
        viewHolder.itemHoras.setText(cursor.getString(idxHoras));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView itemID, itemDescricao, itemData, itemHoras;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemID = itemView.findViewById(R.id.textIDAtividade);
            itemDescricao = itemView.findViewById(R.id.textAtividadeDescricao);
            itemData = itemView.findViewById(R.id.textAtividadeData);
            itemHoras = itemView.findViewById(R.id.textAtividadeHoras);

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
