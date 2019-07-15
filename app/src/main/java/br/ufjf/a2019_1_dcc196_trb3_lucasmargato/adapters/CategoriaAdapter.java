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

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolder> {
    private Cursor cursor;
    private CategoriaAdapter.OnItemClickListener listener;
    private CategoriaAdapter.OnItemLongClickListener listenerl;

    public CategoriaAdapter(Cursor c) {
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
    public void setOnItemClickListener(CategoriaAdapter.OnItemClickListener listener) { this.listener = listener; }
    public void setOnItemLongClickListener(OnItemLongClickListener listener) { this.listenerl = listener; }

    @NonNull
    @Override
    public CategoriaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.layout_categoria, viewGroup, false);
        CategoriaAdapter.ViewHolder viewHolder = new CategoriaAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaAdapter.ViewHolder viewHolder, int i) {
        int idxID = cursor.getColumnIndexOrThrow(HeadhunterContract.Categoria._ID);
        int idxTitulo = cursor.getColumnIndexOrThrow(HeadhunterContract.Categoria.COLLUMN_TITULO);

        cursor.moveToPosition(i);

        viewHolder.itemID.setText(cursor.getString(idxID));
        viewHolder.itemTitulo.setText(cursor.getString(idxTitulo));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView itemID, itemTitulo;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemID = itemView.findViewById(R.id.textIDCategoria);
            itemTitulo = itemView.findViewById(R.id.textCategoria);

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
