package br.ufjf.a2019_1_dcc196_trb3_lucasmargato;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NovaProducaoActivity extends AppCompatActivity {
    HeadhunterDBHelper dbhelper;
    String IDCandidato;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_producao);

        dbhelper = new HeadhunterDBHelper(this);

        IDCandidato = getIntent().getStringExtra("IDCandidato");
        final EditText titulo = findViewById(R.id.editNovaProducaoTitulo);
        final EditText descricao = findViewById(R.id.editNovaProducaoDescricao);
        final EditText inicio = findViewById(R.id.editNovaProducaoInicio);
        final EditText fim = findViewById(R.id.editNovaProducaoFim);
        final Spinner categoria = findViewById(R.id.editNovaProducaoCategoria);
        Button btnConfirmar = findViewById(R.id.buttonNovaProducaoConfirmar);

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dbhelper.getCategorias());
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoria.setAdapter(adp);

        Toast.makeText(NovaProducaoActivity.this, dbhelper.getCategoriaID(categoria.getSelectedItem().toString()), Toast.LENGTH_SHORT).show();

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultado = new Intent();


                ContentValues values = new ContentValues();
                values.put(HeadhunterContract.Producao.COLLUMN_TITULO, titulo.getText().toString());
                values.put(HeadhunterContract.Producao.COLLUMN_DESCRICAO, descricao.getText().toString());
                values.put(HeadhunterContract.Producao.COLLUMN_INICIO, inicio.getText().toString());
                values.put(HeadhunterContract.Producao.COLLUMN_FIM, fim.getText().toString());
                values.put(HeadhunterContract.Producao.COLLUMN_CANDIDATO, IDCandidato);
                values.put(HeadhunterContract.Producao.COLLUMN_CATEGORIA, dbhelper.getCategoriaID(categoria.getSelectedItem().toString()));

                //Toast.makeText(NovaProducaoActivity.this, dbhelper.getCategoriaID(categoria.getSelectedItem().toString()), Toast.LENGTH_SHORT).show();
                SQLiteDatabase db = dbhelper.getWritableDatabase();
                long id = db.insert(HeadhunterContract.Producao.TABLE_NAME, null, values);
                //Toast.makeText(NovaProducaoActivity.this, "id: "+id, Toast.LENGTH_SHORT).show();

                setResult(RESULT_OK, resultado);
                finish();
            }
        });
    }
}
