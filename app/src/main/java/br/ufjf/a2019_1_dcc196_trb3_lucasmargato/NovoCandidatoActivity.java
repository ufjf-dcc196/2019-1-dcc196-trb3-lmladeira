package br.ufjf.a2019_1_dcc196_trb3_lucasmargato;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NovoCandidatoActivity extends AppCompatActivity {
    HeadhunterDBHelper dbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_candidato);

        dbhelper = new HeadhunterDBHelper(this);

        final EditText nome = findViewById(R.id.editNovoCandidatoNome);
        final EditText nascimento = findViewById(R.id.editNovoCandidatoNascimento);
        final EditText perfil = findViewById(R.id.editNovoCandidatoPerfil);
        final EditText telefone = findViewById(R.id.editNovoCandidatoTelefone);
        final EditText email = findViewById(R.id.editNovoCandidatoEmail);
        Button btnConfirmar = findViewById(R.id.buttonNovoCandidatoConfirmar);

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultado = new Intent();

                SQLiteDatabase db = dbhelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(HeadhunterContract.Candidato.COLLUMN_NOME, nome.getText().toString());
                values.put(HeadhunterContract.Candidato.COLLUMN_NASCIMENTO, nascimento.getText().toString());
                values.put(HeadhunterContract.Candidato.COLLUMN_PERFIL, perfil.getText().toString());
                values.put(HeadhunterContract.Candidato.COLLUMN_TELEFONE, telefone.getText().toString());
                values.put(HeadhunterContract.Candidato.COLLUMN_EMAIL, email.getText().toString());

                long id = db.insert(HeadhunterContract.Candidato.TABLE_NAME, null, values);
                Toast.makeText(NovoCandidatoActivity.this, "id: "+id, Toast.LENGTH_SHORT).show();

                setResult(RESULT_OK, resultado);
                finish();
            }
        });
    }
}
