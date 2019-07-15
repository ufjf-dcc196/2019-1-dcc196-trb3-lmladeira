package br.ufjf.a2019_1_dcc196_trb3_lucasmargato;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AlterarProducaoActivity extends AppCompatActivity {
    HeadhunterDBHelper dbhelper;
    SQLiteDatabase db;
    Cursor cursor;
    String IDProducao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_producao);

        dbhelper = new HeadhunterDBHelper(this);
        IDProducao = getIntent().getStringExtra("IDProducao");

        final String[] visao = {
                HeadhunterContract.Producao._ID,
                HeadhunterContract.Producao.COLLUMN_TITULO,
                HeadhunterContract.Producao.COLLUMN_DESCRICAO,
                HeadhunterContract.Producao.COLLUMN_INICIO,
                HeadhunterContract.Producao.COLLUMN_FIM,
                HeadhunterContract.Producao.COLLUMN_CATEGORIA
        };

        final EditText titulo = findViewById(R.id.editAlterarProducaoTitulo);
        final EditText descricao = findViewById(R.id.editAlterarProducaoDescricao);
        final EditText inicio = findViewById(R.id.editAlterarProducaoInicio);
        final EditText fim = findViewById(R.id.editAlterarProducaoFim);
        final Spinner categoria = findViewById(R.id.editAlterarProducaoCategoria);

        Button btnConfirmar = findViewById(R.id.buttonAlterarProducaoConfirmar);

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dbhelper.getCategorias());
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoria.setAdapter(adp);


        db = dbhelper.getReadableDatabase();
        final String select = HeadhunterContract.Producao._ID + " = ?";
        final String[] selectArgs = {IDProducao};
        cursor = db.query(HeadhunterContract.Producao.TABLE_NAME, visao, select, selectArgs, null, null, null);

        int idxTitulo = cursor.getColumnIndexOrThrow(HeadhunterContract.Producao.COLLUMN_TITULO);
        int idxDescricao = cursor.getColumnIndexOrThrow(HeadhunterContract.Producao.COLLUMN_DESCRICAO);
        int idxInicio = cursor.getColumnIndexOrThrow(HeadhunterContract.Producao.COLLUMN_INICIO);
        int idxFim = cursor.getColumnIndexOrThrow(HeadhunterContract.Producao.COLLUMN_FIM);
        int idxCategoria = cursor.getColumnIndexOrThrow(HeadhunterContract.Producao.COLLUMN_CATEGORIA);

        cursor.moveToFirst();

        titulo.setText(cursor.getString(idxTitulo));
        descricao.setText(cursor.getString(idxDescricao));
        inicio.setText(cursor.getString(idxInicio));
        fim.setText(cursor.getString(idxFim));
        categoria.setSelection(adp.getPosition(dbhelper.getCategoria(cursor.getString(idxCategoria))));
        Toast.makeText(this, "String: " + dbhelper.getCategoria(cursor.getString(idxCategoria)) + " Pos: " + adp.getPosition(dbhelper.getCategoria(cursor.getString(idxCategoria))), Toast.LENGTH_SHORT).show();
        db.close();

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(HeadhunterContract.Producao.COLLUMN_TITULO, titulo.getText().toString());
                values.put(HeadhunterContract.Producao.COLLUMN_DESCRICAO, descricao.getText().toString());
                values.put(HeadhunterContract.Producao.COLLUMN_INICIO, inicio.getText().toString());
                values.put(HeadhunterContract.Producao.COLLUMN_FIM, fim.getText().toString());
                values.put(HeadhunterContract.Producao.COLLUMN_CATEGORIA, dbhelper.getCategoriaID(categoria.getSelectedItem().toString()));

                db = dbhelper.getWritableDatabase();
                long id = db.update(HeadhunterContract.Producao.TABLE_NAME, values, select, selectArgs);
                db.close();
                finish();
            }
        });
    }
}
