package br.ufjf.a2019_1_dcc196_trb3_lucasmargato;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NovaCategoriaActivity extends AppCompatActivity {
    HeadhunterDBHelper dbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_categoria);

        dbhelper = new HeadhunterDBHelper(this);

        final EditText categoria = findViewById(R.id.editNovaCategoria);
        Button btnConfirmar = findViewById(R.id.buttonNovaCategoriaConfirma);

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbhelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(HeadhunterContract.Categoria.COLLUMN_TITULO, categoria.getText().toString());

                long id = db.insert(HeadhunterContract.Categoria.TABLE_NAME, null, values);
                Toast.makeText(NovaCategoriaActivity.this, "id: "+id, Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }
}
