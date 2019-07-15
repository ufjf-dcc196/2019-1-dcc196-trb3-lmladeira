package br.ufjf.a2019_1_dcc196_trb3_lucasmargato;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.ufjf.a2019_1_dcc196_trb3_lucasmargato.adapters.CandidatoAdapter;

public class HeadhunterActivity extends AppCompatActivity {

    HeadhunterDBHelper dbhelper;
    CandidatoAdapter adapter;
    SQLiteDatabase db;
    String[] visao = {
            HeadhunterContract.Candidato._ID,
            HeadhunterContract.Candidato.COLLUMN_NOME,
            HeadhunterContract.Candidato.COLLUMN_NASCIMENTO,
            HeadhunterContract.Candidato.COLLUMN_PERFIL,
            HeadhunterContract.Candidato.COLLUMN_TELEFONE,
            HeadhunterContract.Candidato.COLLUMN_EMAIL,
    };
    public static final int NOVO_CANDIDATO = 1;
    public static final int DETALHES_CANDIDATO = 2;
    public static final int GERENCIAR_CATEGORIAS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headhunter);
        RecyclerView rv = findViewById(R.id.rvHeadhunter);
        Button btnNovo = findViewById(R.id.buttonNovoCandidato);
        Button btnGerenciarTags = findViewById(R.id.buttonGerenciarCategorias);

        dbhelper = new HeadhunterDBHelper(this);
        db = dbhelper.getReadableDatabase();

        Cursor c = db.query(HeadhunterContract.Candidato.TABLE_NAME, visao, null, null, null, null, null);
        adapter = new CandidatoAdapter(c);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new CandidatoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                TextView txtID = (TextView) itemView.findViewById(R.id.textIDCandidato);
                Intent intent = new Intent(HeadhunterActivity.this, DetalhesCandidatoActivity.class);
                intent.putExtra("IDCandidato", txtID.getText().toString());

                startActivityForResult(intent, DETALHES_CANDIDATO);

            }
        });

        adapter.setOnItemLongClickListener(new CandidatoAdapter.OnItemLongClickListener() {
            public void onItemLongClick(View itemView, int position) {
                final TextView txtID = (TextView) itemView.findViewById(R.id.textIDCandidato);
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                db.delete(HeadhunterContract.Candidato.TABLE_NAME, "_ID = ?", new String[]{txtID.getText().toString()});
                                db.delete(HeadhunterContract.Producao.TABLE_NAME, "id_candidato = ?", new String[]{txtID.getText().toString()});
                                adapter.setCursor(db.query(HeadhunterContract.Candidato.TABLE_NAME, visao, null, null, null, null, null));
                                adapter.notifyDataSetChanged();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(HeadhunterActivity.this);
                builder.setMessage("Remover Produção?")
                        .setPositiveButton("Sim", dialogClickListener).setNegativeButton("Não", dialogClickListener).show();
            }
        });

        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HeadhunterActivity.this, NovoCandidatoActivity.class);
                startActivityForResult(intent, NOVO_CANDIDATO);

            }
        });

        btnGerenciarTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HeadhunterActivity.this, GerenciarCategoriasActivity.class);
                startActivityForResult(intent, GERENCIAR_CATEGORIAS);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        adapter.setCursor(db.query(HeadhunterContract.Candidato.TABLE_NAME, visao, null, null, null, null, null));
        adapter.notifyDataSetChanged();
    }
}
