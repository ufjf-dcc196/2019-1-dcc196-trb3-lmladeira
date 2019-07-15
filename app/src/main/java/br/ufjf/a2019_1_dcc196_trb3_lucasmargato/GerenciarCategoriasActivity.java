package br.ufjf.a2019_1_dcc196_trb3_lucasmargato;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.ufjf.a2019_1_dcc196_trb3_lucasmargato.adapters.CategoriaAdapter;

public class GerenciarCategoriasActivity extends AppCompatActivity {
    HeadhunterDBHelper dbhelper;
    CategoriaAdapter adapter;
    SQLiteDatabase db;
    Cursor c;
    String[] visao = {
            HeadhunterContract.Categoria._ID,
            HeadhunterContract.Categoria.COLLUMN_TITULO,
    };
    public static final int NOVA_CATEGORIA = 1;
    public static final int VER_CANDIDATOS = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_categorias);

        RecyclerView rv = findViewById(R.id.rvCategorias);
        Button btnNovo = findViewById(R.id.buttonNovaCategoria);

        dbhelper = new HeadhunterDBHelper(this);
        db = dbhelper.getReadableDatabase();


        c = db.query(HeadhunterContract.Categoria.TABLE_NAME, visao, null, null, null, null, null);
        adapter = new CategoriaAdapter(c);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GerenciarCategoriasActivity.this, NovaCategoriaActivity.class);
                startActivityForResult(intent, NOVA_CATEGORIA);
            }
        });

        adapter.setOnItemClickListener(new CategoriaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                TextView txtID = (TextView) itemView.findViewById(R.id.textIDCategoria);
                Intent intent = new Intent(GerenciarCategoriasActivity.this, ListaCandidatosPorCategoriaActivity.class);
                intent.putExtra("IDCategoria", txtID.getText().toString());
                startActivityForResult(intent, VER_CANDIDATOS);
            }
        });

        adapter.setOnItemLongClickListener(new CategoriaAdapter.OnItemLongClickListener() {
            public void onItemLongClick(View itemView, int position) {
                final TextView txtID = (TextView) itemView.findViewById(R.id.textIDCategoria);
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                db.delete(HeadhunterContract.Categoria.TABLE_NAME, "_ID = ?", new String[]{txtID.getText().toString()});
                                db.delete(HeadhunterContract.Producao.TABLE_NAME, "id_categoria = ?", new String[]{txtID.getText().toString()});
                                adapter.setCursor(db.query(HeadhunterContract.Categoria.TABLE_NAME, visao, null, null, null, null, null));
                                adapter.notifyDataSetChanged();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(GerenciarCategoriasActivity.this);
                builder.setMessage("Remover categoria?")
                        .setPositiveButton("Sim", dialogClickListener).setNegativeButton("Não", dialogClickListener).show();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        adapter.setCursor(db.query(HeadhunterContract.Categoria.TABLE_NAME, visao, null, null, null, null, null));
        adapter.notifyDataSetChanged();
    }
}
