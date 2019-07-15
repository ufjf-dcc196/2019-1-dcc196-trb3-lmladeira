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

import br.ufjf.a2019_1_dcc196_trb3_lucasmargato.adapters.AtividadeAdapter;

public class DetalhesProducaoActivity extends AppCompatActivity {
    HeadhunterDBHelper dbhelper;
    AtividadeAdapter adapter;
    SQLiteDatabase db;
    String select;
    String[] selectArgs;
    String IDCandidato, IDProducao;
    String[] visao = {
            HeadhunterContract.Atividade._ID,
            HeadhunterContract.Atividade.COLLUMN_DESCRICAO,
            HeadhunterContract.Atividade.COLLUMN_DATA,
            HeadhunterContract.Atividade.COLLUMN_HORAS,
            HeadhunterContract.Atividade.COLLUMN_PRODUCAO
    };
    public static final int NOVA_ATIVIDADE = 1;
    public static final int ALTERAR_PRODUCAO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_producao);
        RecyclerView rv = findViewById(R.id.rvDetalhesProducao);
        Button btnNovo = findViewById(R.id.buttonNovaAtividade);
        Button btnAlterarCandidato = findViewById(R.id.buttonAlterarProducao);

        dbhelper = new HeadhunterDBHelper(this);
        db = dbhelper.getReadableDatabase();

        IDCandidato = getIntent().getStringExtra("IDCandidato");
        IDProducao = getIntent().getStringExtra("IDProducao");
        select = HeadhunterContract.Atividade.COLLUMN_PRODUCAO + " = ?";
        selectArgs = new String[]{IDProducao};

        Cursor c = db.query(HeadhunterContract.Atividade.TABLE_NAME, visao, select, selectArgs, null, null, null);
        Toast.makeText(this, String.valueOf(c.getCount()), Toast.LENGTH_SHORT).show();
        adapter = new AtividadeAdapter(c);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalhesProducaoActivity.this, NovaAtividadeActivity.class);
                intent.putExtra("IDProducao", IDProducao);
                startActivityForResult(intent, NOVA_ATIVIDADE);
            }
        });

        btnAlterarCandidato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalhesProducaoActivity.this, AlterarProducaoActivity.class);
                intent.putExtra("IDProducao", IDProducao);
                startActivityForResult(intent, ALTERAR_PRODUCAO);
            }
        });

        adapter.setOnItemLongClickListener(new AtividadeAdapter.OnItemLongClickListener() {
            public void onItemLongClick(View itemView, int position) {
                final TextView txtID = (TextView) itemView.findViewById(R.id.textIDAtividade);
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                db.delete(HeadhunterContract.Atividade.TABLE_NAME, "_ID = ?", new String[]{txtID.getText().toString()});
                                adapter.setCursor(db.query(HeadhunterContract.Atividade.TABLE_NAME, visao, select, selectArgs, null, null, null));
                                adapter.notifyDataSetChanged();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(DetalhesProducaoActivity.this);
                builder.setMessage("Remover Atividade?")
                        .setPositiveButton("Sim", dialogClickListener).setNegativeButton("Não", dialogClickListener).show();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        adapter.setCursor(db.query(HeadhunterContract.Atividade.TABLE_NAME, visao, select, selectArgs, null, null, null));
        adapter.notifyDataSetChanged();
    }
}
