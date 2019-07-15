package br.ufjf.a2019_1_dcc196_trb3_lucasmargato;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class HeadhunterDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "Headhunter.db";

    public HeadhunterDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HeadhunterContract.Categoria.CREATE_TABLE);
        db.execSQL(HeadhunterContract.Candidato.CREATE_TABLE);
        db.execSQL(HeadhunterContract.Producao.CREATE_TABLE);
        db.execSQL(HeadhunterContract.Atividade.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(HeadhunterContract.Categoria.DROP_TABLE);
        db.execSQL(HeadhunterContract.Candidato.DROP_TABLE);
        db.execSQL(HeadhunterContract.Producao.DROP_TABLE);
        db.execSQL(HeadhunterContract.Atividade.DROP_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public List<String> getCategorias(){
        List<String> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + HeadhunterContract.Categoria.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                list.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }

    public String getCategoriaID(String cat){
        String selectQuery = "SELECT _ID FROM " + HeadhunterContract.Categoria.TABLE_NAME + " WHERE " + HeadhunterContract.Categoria.COLLUMN_TITULO + "= ?";
        String[] queryArgs = {cat};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, queryArgs);
        String id = null;

        int idxID = cursor.getColumnIndexOrThrow(HeadhunterContract.Categoria._ID);
        if (cursor.moveToFirst()) {
            id = cursor.getString(idxID);
        }

        cursor.close();
        db.close();

        return id;
    }

    public String getCategoria(String id){
        String selectQuery = "SELECT " + HeadhunterContract.Categoria.COLLUMN_TITULO + " FROM " + HeadhunterContract.Categoria.TABLE_NAME + " WHERE _ID = ?";
        String[] queryArgs = {id};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, queryArgs);
        String titulo = null;

        int idxTitulo = cursor.getColumnIndexOrThrow(HeadhunterContract.Categoria.COLLUMN_TITULO);
        if (cursor.moveToFirst()) {
            titulo = cursor.getString(idxTitulo);
        }

        cursor.close();
        db.close();

        return titulo;
    }

}
