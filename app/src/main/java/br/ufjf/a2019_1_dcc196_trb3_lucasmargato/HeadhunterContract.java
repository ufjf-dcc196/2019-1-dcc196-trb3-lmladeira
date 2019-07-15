package br.ufjf.a2019_1_dcc196_trb3_lucasmargato;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class HeadhunterContract {

    public static final class Candidato implements BaseColumns {
        public static final String TABLE_NAME = "candidato";
        public static final String COLLUMN_NOME = "nome";
        public static final String COLLUMN_NASCIMENTO = "nascimento";
        public static final String COLLUMN_PERFIL = "perfil";
        public static final String COLLUMN_TELEFONE = "telefone";
        public static final String COLLUMN_EMAIL = "email";

        public static final String CREATE_TABLE = String.format("CREATE TABLE %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT, " + // Nome
                        "%s DATE, " + // Nascimento
                        "%s TEXT, " + // Perfil
                        "%s TEXT, " + // Telefone
                        "%s TEXT)", // Email
                TABLE_NAME, _ID, COLLUMN_NOME, COLLUMN_NASCIMENTO, COLLUMN_PERFIL, COLLUMN_TELEFONE, COLLUMN_EMAIL);

        public static final String DROP_TABLE = String.format("DROP TABLE %s", TABLE_NAME);
    }

    public static final class Producao implements BaseColumns {
        public static final String TABLE_NAME = "producao";
        public static final String COLLUMN_TITULO = "titulo";
        public static final String COLLUMN_DESCRICAO = "descricao";
        public static final String COLLUMN_INICIO = "inicio";
        public static final String COLLUMN_FIM = "fim";
        public static final String COLLUMN_CATEGORIA = "id_categoria";
        public static final String COLLUMN_CANDIDATO = "id_candidato";

        public static final String CREATE_TABLE = String.format("CREATE TABLE %s (" +
                "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "%s TEXT, " + // Título
                "%s TEXT, " + // Descrição
                "%s DATE, " + // Início
                "%s DATE, " + // Fim
                "%s INTEGER, " + // ID Categoria
                "%s INTEGER)", // ID Candidato
                TABLE_NAME, _ID, COLLUMN_TITULO, COLLUMN_DESCRICAO, COLLUMN_INICIO, COLLUMN_FIM, COLLUMN_CATEGORIA, COLLUMN_CANDIDATO);

        public static final String DROP_TABLE = String.format("DROP TABLE %s", TABLE_NAME);
    }

    public static final class Categoria implements BaseColumns {
        public static final String TABLE_NAME = "categoria";
        public static final String COLLUMN_TITULO = "titulo";

        public static final String CREATE_TABLE = String.format("CREATE TABLE %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT)", // Título
                TABLE_NAME, _ID, COLLUMN_TITULO);

        public static final String DROP_TABLE = String.format("DROP TABLE %s", TABLE_NAME);


    }

    public static final class Atividade implements  BaseColumns {
        public static final String TABLE_NAME = "atividade";
        public static final String COLLUMN_DESCRICAO = "descricao";
        public static final String COLLUMN_DATA = "data";
        public static final String COLLUMN_HORAS = "horas";
        public static final String COLLUMN_PRODUCAO = "id_producao";

        public static final String CREATE_TABLE = String.format("CREATE TABLE %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT, " + // Descrição
                        "%s DATE, " + // Data
                        "%s INTEGER, " + // Horas
                        "%s INTEGER)", // ID Producao
                TABLE_NAME, _ID, COLLUMN_DESCRICAO, COLLUMN_DATA, COLLUMN_HORAS, COLLUMN_PRODUCAO);

        public static final String DROP_TABLE = String.format("DROP TABLE %s", TABLE_NAME);
    }

}
