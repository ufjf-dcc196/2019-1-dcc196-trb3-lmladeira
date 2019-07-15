package br.ufjf.a2019_1_dcc196_trb3_lucasmargato;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import br.ufjf.a2019_1_dcc196_trb3_lucasmargato.adapters.CandidatoAdapter;
import br.ufjf.a2019_1_dcc196_trb3_lucasmargato.adapters.ListaCandidatosPorCategoriaAdapter;

public class ListaCandidatosPorCategoriaActivity extends AppCompatActivity {

    HeadhunterDBHelper dbhelper;
    ListaCandidatosPorCategoriaAdapter adapter;
    SQLiteDatabase db;
    String IDCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_candidatos_por_categoria);
        RecyclerView rv = findViewById(R.id.rvListaCandidatos);

        IDCategoria = getIntent().getStringExtra("IDCategoria");
        dbhelper = new HeadhunterDBHelper(this);
        db = dbhelper.getReadableDatabase();

        //final String query = "SELECT candidato.nome AS 'nome', SUM(atividade.horas) AS 'horastotais' FROM canditado, producao, atividade WHERE candidato." + HeadhunterContract.Candidato._ID + " = producao.id_candidato AND producao." + HeadhunterContract.Producao._ID + " = atividade.id_producao AND producao.id_categoria = ?";
        final String query = "SELECT candidato.nome AS 'nome', SUM(atividade.horas) AS 'horastotais' FROM candidato, producao, atividade WHERE candidato._ID = producao.id_candidato AND producao._ID = atividade.id_producao AND producao.id_categoria = ? GROUP BY nome ORDER BY horastotais DESC";
        String[] queryArgs = new String[]{IDCategoria};

        Cursor c = db.rawQuery(query, queryArgs);
        Toast.makeText(this, String.valueOf(c.getCount()), Toast.LENGTH_SHORT).show();
        adapter = new ListaCandidatosPorCategoriaAdapter(c);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }
}
