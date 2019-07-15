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

public class CandidatoAdapter extends RecyclerView.Adapter<CandidatoAdapter.ViewHolder>{
    private Cursor cursor;
    private OnItemClickListener listener;
    private OnItemLongClickListener listenerl;

    public CandidatoAdapter(Cursor c) {
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
    public void setOnItemClickListener(OnItemClickListener listener) { this.listener = listener; }
    public void setOnItemLongClickListener(OnItemLongClickListener listener) { this.listenerl = listener; }

    @NonNull
    @Override
    public CandidatoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.layout_candidato, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CandidatoAdapter.ViewHolder viewHolder, int i) {
        int idxID = cursor.getColumnIndexOrThrow(HeadhunterContract.Candidato._ID);
        int idxNome = cursor.getColumnIndexOrThrow(HeadhunterContract.Candidato.COLLUMN_NOME);
        int idxNascimento = cursor.getColumnIndexOrThrow(HeadhunterContract.Candidato.COLLUMN_NASCIMENTO);
        int idxPerfil = cursor.getColumnIndexOrThrow(HeadhunterContract.Candidato.COLLUMN_PERFIL);
        int idxTelefone = cursor.getColumnIndexOrThrow(HeadhunterContract.Candidato.COLLUMN_TELEFONE);
        int idxEmail = cursor.getColumnIndexOrThrow(HeadhunterContract.Candidato.COLLUMN_EMAIL);

        cursor.moveToPosition(i);

        viewHolder.itemID.setText(cursor.getString(idxID));
        viewHolder.itemNome.setText(cursor.getString(idxNome));
        viewHolder.itemNascimento.setText(cursor.getString(idxNascimento));
        viewHolder.itemPerfil.setText(cursor.getString(idxPerfil));
        viewHolder.itemTelefone.setText(cursor.getString(idxTelefone));
        viewHolder.itemEmail.setText(cursor.getString(idxEmail));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView itemID, itemNome, itemNascimento, itemPerfil, itemTelefone, itemEmail;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemID = itemView.findViewById(R.id.textIDCandidato);
            itemNome = itemView.findViewById(R.id.textNome);
            itemNascimento = itemView.findViewById(R.id.textNascimento);
            itemPerfil = itemView.findViewById(R.id.textPerfil);
            itemTelefone = itemView.findViewById(R.id.textTelefone);
            itemEmail = itemView.findViewById(R.id.textEmail);

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
