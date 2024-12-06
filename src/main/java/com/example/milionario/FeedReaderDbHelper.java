package com.example.milionario;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class FeedReaderDbHelper  extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Milionario.db";
    public static final String TABELA_USER = "utilizador";
    public static final String TABELA_PERGUNTA = "pergunta";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Query Create User
    private static final String SQL_CREATE_USER =
            "CREATE TABLE " + TABELA_USER + "(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nome VARCHAR(40), " +
                    "email VARCHAR(50), " +
                    "password VARCHAR(30), " +
                    "dinheiro INT" +
                    ");";

    //Query Create nivel 1/15
    private static final String SQL_CREATE_LEVEL =
                    "CREATE TABLE " + TABELA_PERGUNTA +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "nivel INTEGER," +
                            "pergunta VARCHAR(100)," +
                            "opcao1 VARCHAR(50)," +
                            "opcao2 VARCHAR(50)," +
                            "opcao3 VARCHAR(50)," +
                            "opcao4 VARCHAR(50));";


    private static final String SQL_DROP_ENTRIES =
            "DROP TABLE IF EXISTS " + TABELA_USER + ";" +
                    "DROP TABLE IF EXISTS " + TABELA_PERGUNTA + ";";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER);
        db.execSQL(SQL_CREATE_LEVEL);

        //========Criar as perguntas=====
        //Nível 1
        InsertPerguntasDB(db,"Qual é a capital da Itália?", "Roma", "Milão", "Veneza", "Florença", 1);
        InsertPerguntasDB(db,"Qual é o nome da montanha mais alta do mundo?", "Monte Everest", "Monte Fuji", "Serra da Estrela", "Kilimanjaro", 1);
        //Nível 2
        InsertPerguntasDB(db,"Qual é o maior oceano do mundo?", "Pacífico", "Atlântico", "Índico", "Ártico", 2);
        InsertPerguntasDB(db,"Qual é o país mais populoso do mundo?", "China", "Índia", "Estados Unidos", "Indonésia", 2);
        //Nível 3
        InsertPerguntasDB(db,"Quantos continentes existem no mundo?", "7", "6", "5", "8", 3);
        InsertPerguntasDB(db,"Qual é o maior país do mundo em área territorial?", "Rússia", "China", "Canadá", "Brasil", 3);
        //Nível 4
        InsertPerguntasDB(db,"Quem pintou a Mona Lisa?", "Leonardo da Vinci", "Michelangelo", "Van Gogh", "Picasso", 4);
        InsertPerguntasDB(db,"Qual é o nome do navegador português que descobriu o caminho marítimo para a Índia?", "Vasco da Gama", "Pedro Álvares Cabral", "Fernão de Magalhães", "Bartolomeu Dias", 4);
        //Nível 5
        InsertPerguntasDB(db,"Quem foi o primeiro homem a pisar na Lua?", "Neil Armstrong", "Buzz Aldrin", "Yuri Gagarin", "Alan Shepard", 5);
        InsertPerguntasDB(db,"Qual é o menor país do mundo em termos de área?", "Vaticano", "Mónaco", "San Marino", "Liechtenstein", 5);
        //Nível 6
        InsertPerguntasDB(db,"Em que ano começou a Segunda Guerra Mundial?", "1939", "1938", "1940", "1941", 6);
        InsertPerguntasDB(db,"Qual foi a primeira universidade fundada em Portugal?", "Universidade de Coimbra", "Universidade do Porto", "Universidade de Lisboa", "Universidade de Évora", 6);
        //Nível 7
        InsertPerguntasDB(db,"Quem escreveu 'Dom Quixote'?", "Miguel de Cervantes", "William Shakespeare", "Dante Alighieri", "Vitor Hugo", 7);
        InsertPerguntasDB(db,"Qual cientista formulou a Teoria da Relatividade?", "Albert Einstein", "Isaac Newton", "Nikola Tesla", "Stephen Hawking", 7);
        //Nível 8
        InsertPerguntasDB(db,"Quantas estrelas estão na bandeira da União Europeia?", "12", "10", "15", "20", 8);
        InsertPerguntasDB(db,"Qual é o elemento químico mais abundante no universo?", "Hidrogénio", "Hélio", "Oxigénio", "Carbono", 8);
        //Nível 9
        InsertPerguntasDB(db,"Qual é o nome do escritor português que ganhou o Prémio Nobel da Literatura?", "José Saramago", "Eça de Queirós", "António Lobo Antunes", "Fernando Pessoa", 9);
        InsertPerguntasDB(db,"Em que ano Portugal entrou na União Europeia?", "1986", "1980", "1990", "1995", 9);
        //Nível 10
        InsertPerguntasDB(db,"Qual é a menor unidade de vida?", "Célula", "Átomo", "Molécula", "Órgão", 10);
        InsertPerguntasDB(db,"Qual elemento químico tem o símbolo 'Au'?", "Ouro", "Prata", "Platina", "Alumínio", 10);
        //Nível 11
        InsertPerguntasDB(db,"Qual é a profundidade máxima registada na Fossa das Marianas?", "10.924 metros", "11.457 metros", "9.102 metros", "12.005 metros", 11);
        InsertPerguntasDB(db,"Qual é o nome oficial do Parlamento Português?", "Palácio de São Bento", "Palácio de Belém", "Assembleia Nacional", "Assembleia da República", 11);
        //Nível 12
        InsertPerguntasDB(db,"Qual é a maior ilha do mundo?", "Gronelândia", "Nova Guiné", "Bornéu", "Madagascar", 12);
        InsertPerguntasDB(db,"Em que ano foi assinado o Tratado de Maastricht, que deu origem à União Europeia?", "1992", "1986", "1991", "1995", 12);
        //Nível 13
        InsertPerguntasDB(db,"Qual é o órgão mais pesado no corpo humano?", "Fígado", "Coração", "Pulmões", "Cérebro", 13);
        InsertPerguntasDB(db,"Qual país possui o maior número de Patrimónios Mundiais da UNESCO?", "Itália", "China", "Índia", "França", 13);
        //Nível 14
        InsertPerguntasDB(db,"Qual foi o primeiro satélite artificial lançado pela humanidade?", "Sputnik 1", "Explorer 1", "Vostok 1", "Luna 2", 14);
        InsertPerguntasDB(db,"Qual é o valor considerado o zero absoluto em termos de temperatura?", "-273,15 °C", "0 °C", "-459,67 °C", "-273,15 K", 14);
        //Nível 15
        InsertPerguntasDB(db,"Qual é a temperatura mais baixa alguma vez registada na superfície terrestre?", "-89,2 ºC", "-79,2 ºC", "-94,5 ºC", "-102,3 ºC", 15);
        InsertPerguntasDB(db,"Qual é a molécula mais abundante na atmosfera terrestre?", "Nitrogénio", "Oxigénio", "Dióxido de Carbono", "Vapor de Água", 15);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DROP_ENTRIES);
        onCreate(db);
    }

    // Função para inserir perguntas
    public void InsertPerguntasDB(SQLiteDatabase db, String pergunta, String opA, String opB, String opC, String opD, int nivel) {
        ContentValues values = new ContentValues();
        values.put("pergunta", pergunta);
        values.put("opcao1", opA);
        values.put("opcao2", opB);
        values.put("opcao3", opC);
        values.put("opcao4", opD);
        values.put("nivel", nivel);

        long result = db.insert("pergunta", null, values);
        if (result == -1) { // Condição para Logs
            Log.e("ErroPergunta", "Erro ao inserir pergunta: " + values);
        } else {
            Log.d("InsertPergunta", "Pergunta inserida com sucesso: " + values);
        }
    }
}