package br.ufjf.a2019_1_dcc196_trb3_lucasmargato;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AlterarCandidatoActivity extends AppCompatActivity {
    HeadhunterDBHelper dbhelper;
    Cursor cursor;
    String IDCandidato;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_candidato);

        dbhelper = new HeadhunterDBHelper(this);
        final SQLiteDatabase db = dbhelper.getWritableDatabase();
        IDCandidato = getIntent().getStringExtra("IDCandidato");

        final String[] visao = {
                HeadhunterContract.Candidato._ID,
                HeadhunterContract.Candidato.COLLUMN_NOME,
                HeadhunterContract.Candidato.COLLUMN_NASCIMENTO,
                HeadhunterContract.Candidato.COLLUMN_PERFIL,
                HeadhunterContract.Candidato.COLLUMN_TELEFONE,
                HeadhunterContract.Candidato.COLLUMN_EMAIL
        };

        final EditText nome = findViewById(R.id.editAlterarCandidatoNome);
        final EditText nascimento = findViewById(R.id.editAlterarCandidatoNascimento);
        final EditText perfil = findViewById(R.id.editAlterarCandidatoPerfil);
        final EditText telefone = findViewById(R.id.editAlterarCandidatoTelefone);
        final EditText email = findViewById(R.id.editAlterarCandidatoEmail);

        Button btnConfirmar = findViewById(R.id.buttonAlterarCandidatoConfirmar);

        final String select = HeadhunterContract.Candidato._ID + " = ?";
        final String[] selectArgs = {IDCandidato};
        cursor = db.query(HeadhunterContract.Candidato.TABLE_NAME, visao, select, selectArgs, null, null, null);

        int idxNome = cursor.getColumnIndexOrThrow(HeadhunterContract.Candidato.COLLUMN_NOME);
        int idxNascimento = cursor.getColumnIndexOrThrow(HeadhunterContract.Candidato.COLLUMN_NASCIMENTO);
        int idxPerfil = cursor.getColumnIndexOrThrow(HeadhunterContract.Candidato.COLLUMN_PERFIL);
        int idxTelefone = cursor.getColumnIndexOrThrow(HeadhunterContract.Candidato.COLLUMN_TELEFONE);
        int idxEmail = cursor.getColumnIndexOrThrow(HeadhunterContract.Candidato.COLLUMN_EMAIL);

        cursor.moveToFirst();
        nome.setText(cursor.getString(idxNome));
        nascimento.setText(cursor.getString(idxNascimento));
        perfil.setText(cursor.getString(idxPerfil));
        telefone.setText(cursor.getString(idxTelefone));
        email.setText(cursor.getString(idxEmail));

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(HeadhunterContract.Candidato.COLLUMN_NOME, nome.getText().toString());
                values.put(HeadhunterContract.Candidato.COLLUMN_NASCIMENTO, nascimento.getText().toString());
                values.put(HeadhunterContract.Candidato.COLLUMN_PERFIL, perfil.getText().toString());
                values.put(HeadhunterContract.Candidato.COLLUMN_TELEFONE, telefone.getText().toString());
                values.put(HeadhunterContract.Candidato.COLLUMN_EMAIL, email.getText().toString());

                long id = db.update(HeadhunterContract.Candidato.TABLE_NAME, values, select, selectArgs);

                finish();
            }
        });
    }
}
