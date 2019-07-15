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
import android.widget.Toast;

import br.ufjf.a2019_1_dcc196_trb3_lucasmargato.adapters.ProducaoAdapter;

public class DetalhesCandidatoActivity extends AppCompatActivity {
    HeadhunterDBHelper dbhelper;
    ProducaoAdapter adapter;
    SQLiteDatabase db;
    String select;
    String[] selectArgs;
    String IDCandidato;
    String[] visao = {
            HeadhunterContract.Producao._ID,
            HeadhunterContract.Producao.COLLUMN_TITULO,
            HeadhunterContract.Producao.COLLUMN_DESCRICAO,
            HeadhunterContract.Producao.COLLUMN_INICIO,
            HeadhunterContract.Producao.COLLUMN_FIM,
            HeadhunterContract.Producao.COLLUMN_CATEGORIA
    };
    public static final int NOVA_PRODUCAO = 1;
    public static final int DETALHES_PRODUCAO = 2;
    public static final int ALTERAR_CANDIDATO = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_candidato);
        RecyclerView rv = findViewById(R.id.rvDetalhesCandidato);
        Button btnNovo = findViewById(R.id.buttonNovaProducao);
        Button btnAlterarCandidato = findViewById(R.id.buttonAlterarCandidato);

        dbhelper = new HeadhunterDBHelper(this);
        db = dbhelper.getReadableDatabase();

        IDCandidato = getIntent().getStringExtra("IDCandidato");
        select = HeadhunterContract.Producao.COLLUMN_CANDIDATO + " = ?";
        selectArgs = new String[]{IDCandidato};

        Cursor c = db.query(HeadhunterContract.Producao.TABLE_NAME, visao, select, selectArgs, null, null, null);
        Toast.makeText(this, String.valueOf(c.getCount()), Toast.LENGTH_SHORT).show();
        adapter = new ProducaoAdapter(c);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new ProducaoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                TextView txtID = (TextView) itemView.findViewById(R.id.textIDProducao);
                Intent intent = new Intent(DetalhesCandidatoActivity.this, DetalhesProducaoActivity.class);
                intent.putExtra("IDCandidato", IDCandidato);
                intent.putExtra("IDProducao", txtID.getText().toString());

                startActivityForResult(intent, DETALHES_PRODUCAO);
            }
        });

        adapter.setOnItemLongClickListener(new ProducaoAdapter.OnItemLongClickListener() {
            public void onItemLongClick(View itemView, int position) {
                final TextView txtID = (TextView) itemView.findViewById(R.id.textIDProducao);
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                db.delete(HeadhunterContract.Producao.TABLE_NAME, "_ID = ?", new String[]{txtID.getText().toString()});
                                db.delete(HeadhunterContract.Atividade.TABLE_NAME, "id_producao = ?", new String[]{txtID.getText().toString()});
                                adapter.setCursor(db.query(HeadhunterContract.Producao.TABLE_NAME, visao, select, selectArgs, null, null, null));
                                adapter.notifyDataSetChanged();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(DetalhesCandidatoActivity.this);
                builder.setMessage("Remover Produção?")
                        .setPositiveButton("Sim", dialogClickListener).setNegativeButton("Não", dialogClickListener).show();
            }
        });

        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalhesCandidatoActivity.this, NovaProducaoActivity.class);
                intent.putExtra("IDCandidato", IDCandidato);
                startActivityForResult(intent, NOVA_PRODUCAO);

            }
        });

        btnAlterarCandidato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalhesCandidatoActivity.this, AlterarCandidatoActivity.class);
                intent.putExtra("IDCandidato", IDCandidato);
                startActivityForResult(intent, ALTERAR_CANDIDATO);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        adapter.setCursor(db.query(HeadhunterContract.Producao.TABLE_NAME, visao, select, selectArgs, null, null, null));
        adapter.notifyDataSetChanged();
    }
}
