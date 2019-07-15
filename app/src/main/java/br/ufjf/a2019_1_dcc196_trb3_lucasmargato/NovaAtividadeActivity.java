package br.ufjf.a2019_1_dcc196_trb3_lucasmargato;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NovaAtividadeActivity extends AppCompatActivity {
    HeadhunterDBHelper dbhelper;
    String IDProducao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_atividade);

        dbhelper = new HeadhunterDBHelper(this);

        IDProducao = getIntent().getStringExtra("IDProducao");
        final EditText descricao = findViewById(R.id.editNovaAtividadeDescricao);
        final EditText data = findViewById(R.id.editNovaAtividadeData);
        final EditText horas = findViewById(R.id.editNovaAtividadeHoras);
        Button btnConfirmar = findViewById(R.id.buttonNovaAtividadeConfirmar);

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbhelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(HeadhunterContract.Atividade.COLLUMN_DESCRICAO, descricao.getText().toString());
                values.put(HeadhunterContract.Atividade.COLLUMN_DATA, data.getText().toString());
                values.put(HeadhunterContract.Atividade.COLLUMN_HORAS, horas.getText().toString());
                values.put(HeadhunterContract.Atividade.COLLUMN_PRODUCAO, IDProducao);

                long id = db.insert(HeadhunterContract.Atividade.TABLE_NAME, null, values);
                Toast.makeText(NovaAtividadeActivity.this, "id: "+id, Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }
}
