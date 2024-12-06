package com.example.milionario;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class GameActivity extends AppCompatActivity {

    TextView Question1;
    TextView Question2;
    TextView Question3;
    TextView Question4;
    TextView Question5;
    TextView Question6;
    TextView Question7;
    TextView Question8;
    TextView Question9;
    TextView Question10;
    TextView Question11;
    TextView Question12;
    TextView Question13;
    TextView Question14;
    TextView Question15;
    TextView Pergunta;
    Button OpcaoA;
    Button OpcaoB;
    Button OpcaoC;
    Button OpcaoD;
    Button buttonDesistir;
    Button buttonAjuda5050;
    Button buttonAjudaTroca;
    Button buttonParar;

    private int i = 1, nivel = 1, dinheiro = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Declarar ID's Ajudas
        buttonAjuda5050 = findViewById(R.id.buttonAjuda50);
        buttonAjudaTroca = findViewById(R.id.buttonAjudaTroca);

        //Declarar ID's dos níveis perguntas
        Question1 = findViewById(R.id.Question1);
        Question2 = findViewById(R.id.Question2);
        Question3 = findViewById(R.id.Question3);
        Question4 = findViewById(R.id.Question4);
        Question5 = findViewById(R.id.Question5);
        Question6 = findViewById(R.id.Question6);
        Question7 = findViewById(R.id.Question7);
        Question8 = findViewById(R.id.Question8);
        Question9 = findViewById(R.id.Question9);
        Question10 = findViewById(R.id.Question10);
        Question11 = findViewById(R.id.Question11);
        Question12 = findViewById(R.id.Question12);
        Question13 = findViewById(R.id.Question13);
        Question14 = findViewById(R.id.Question14);
        Question15 = findViewById(R.id.Question15);

        //Declarar ID da textView da Pergunta
        Pergunta = findViewById(R.id.ViewPergunta);

        //Declarar ID's das opções
        OpcaoA = findViewById(R.id.buttonA);
        OpcaoB = findViewById(R.id.ButtonB);
        OpcaoC = findViewById(R.id.ButtonC);
        OpcaoD = findViewById(R.id.ButtonD);

        //Declarar button desistir
        buttonDesistir = findViewById(R.id.buttonDesistir);

        //Declarar button parar
        buttonParar = findViewById(R.id.buttonParar);

        buttonDesistir.setOnClickListener(view -> Desistir());
        buttonParar.setOnClickListener(view -> {
            //Update dinheiro na BD e ir para Dashboard Activity
            Parar();
        });

        //=================================
        //==========Listar Perguntas=======
        buttonAjudaTroca.setOnClickListener(view -> {
            i++; //Incrementa o i para mover o cursor para a segunda pergunta
            ListaPerguntas();
            buttonAjudaTroca.setEnabled(false); //Desabilitar botão
        });

        ListaPerguntas();

        //===================================
        //===========Listener's opções=======

        OpcaoA.setOnClickListener(view -> {
            if (Verifica(OpcaoA.getText().toString())) {
                OpcaoA.setBackgroundColor(Color.GREEN);
                nivel++;

                // Adiciona um atraso de 2 segundos antes de chamar ListaPerguntas
                new Handler().postDelayed(() -> ListaPerguntas(), 2000);
            } else {
                OpcaoA.setBackgroundColor(Color.RED);
            }
        });

        OpcaoB.setOnClickListener(view -> {
            if (Verifica(OpcaoB.getText().toString())) {
                OpcaoB.setBackgroundColor(Color.GREEN);
                nivel++;

                // Adiciona um atraso de 2 segundos antes de chamar ListaPerguntas
                new Handler().postDelayed(() -> ListaPerguntas(), 2000);
            } else {
                OpcaoB.setBackgroundColor(Color.RED);
            }
        });

        OpcaoC.setOnClickListener(view -> {
            if (Verifica(OpcaoC.getText().toString())) {
                OpcaoC.setBackgroundColor(Color.GREEN);
                nivel++;

                // Adiciona um atraso de 2 segundos antes de chamar ListaPerguntas
                new Handler().postDelayed(() -> ListaPerguntas(), 2000);
            } else {
                OpcaoC.setBackgroundColor(Color.RED);
            }
        });

        OpcaoD.setOnClickListener(view -> {
            if (Verifica(OpcaoD.getText().toString())) {
                OpcaoD.setBackgroundColor(Color.GREEN);
                nivel++;

                // Adiciona um atraso de 2 segundos antes de chamar ListaPerguntas
                new Handler().postDelayed(() -> ListaPerguntas(), 2000);
            } else {
                OpcaoD.setBackgroundColor(Color.RED);
            }
        });
    }

    public void ListaPerguntas() {
        //Repôr as cores dos botões
        OpcaoA.setBackgroundColor(Color.parseColor("#0C63D4"));
        OpcaoB.setBackgroundColor(Color.parseColor("#0C63D4"));
        OpcaoC.setBackgroundColor(Color.parseColor("#0C63D4"));
        OpcaoD.setBackgroundColor(Color.parseColor("#0C63D4"));

        //Instância DB
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(getApplicationContext());

        //Modo Leitura DB
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //List Array Opções
        List<String> opcoes = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM pergunta WHERE nivel= " + nivel, null);

        if (cursor.move(i)) {
            String pergunta = cursor.getString(cursor.getColumnIndexOrThrow("pergunta"));
            String opcao1 = cursor.getString(cursor.getColumnIndexOrThrow("opcao1"));
            String opcao2 = cursor.getString(cursor.getColumnIndexOrThrow("opcao2"));
            String opcao3 = cursor.getString(cursor.getColumnIndexOrThrow("opcao3"));
            String opcao4 = cursor.getString(cursor.getColumnIndexOrThrow("opcao4"));

            opcoes.add(opcao1);
            opcoes.add(opcao2);
            opcoes.add(opcao3);
            opcoes.add(opcao4);

            //Collections shuffle para baralhar as opções
            Collections.shuffle(opcoes);

            Pergunta.setText(pergunta);
            OpcaoA.setText(opcoes.get(0));
            OpcaoB.setText(opcoes.get(1));
            OpcaoC.setText(opcoes.get(2));
            OpcaoD.setText(opcoes.get(3));

        } else {
            Log.d("Perguntas", "Sem perguntas para mostrar");
        }

        //Condição para mostrar o botão "parar" nos níveis 6 e 11
        if (nivel == 6) {
            buttonParar.setVisibility(View.VISIBLE);
        } else if(nivel == 11) {
            buttonParar.setVisibility(View.VISIBLE);
        } else {
            buttonParar.setVisibility(View.INVISIBLE);
        }

        //Condição para mudar a cor consoante o valor garantido
        if (nivel >= 15) {
            Question15.setBackgroundColor(Color.parseColor("#4CAF50"));
        } else if (nivel >= 10) {
            Question10.setBackgroundColor(Color.parseColor("#4CAF50"));
        } else if (nivel >= 5) {
            Question5.setBackgroundColor(Color.parseColor("#4CAF50"));
        }
    }

    public boolean Verifica(String escolhida) {
        Intent getIntent = getIntent();
        String email = getIntent.getStringExtra("Email");

        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(getApplicationContext());

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        try {
            String correta = "";
            int Money = 0;

            Cursor cursor = db.rawQuery("SELECT * FROM pergunta WHERE nivel = ?", new String[]{String.valueOf(nivel)});
            if (cursor.move(i)) {
                correta = cursor.getString(cursor.getColumnIndexOrThrow("opcao1")); // "opcao1" contém a resposta correta
            }

            Cursor cursor2 = db.rawQuery("SELECT * FROM utilizador WHERE email = ?", new String[]{email});
            if (cursor2.moveToFirst()) {
                Money = cursor2.getInt(cursor2.getColumnIndexOrThrow("dinheiro")); // Pega o valor do dinheiro atual
            }

            // Verificar se a resposta escolhida está correta
            if (Objects.equals(escolhida, correta)) {
                Log.d("Certo", "Está certa");
                return true;
            } else {
                Log.d("Errado", "Está errada");

                if (nivel >= 15) {
                    dinheiro = 2000000;
                } else if (nivel >= 10) {
                    dinheiro = 1000000;
                } else if (nivel >= 5) {
                    dinheiro = 10000;
                }

                int soma = Money + dinheiro;

                values.put("dinheiro", soma);

                int rows = db.update("utilizador", values, "email = ?", new String[]{email});

                if (rows > 0) { //Número de linhas atualizadas > 0
                    Log.d("dinheiro", "Dinheiro atualizado com sucesso!");
                    if (dinheiro > 0) {
                        Toast.makeText(this, "Ganhou " + dinheiro + " €", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Não ganhou nada", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("dinheiro", "Erro ao atualizar dinheiro!");
                }

                cursor.close();
                cursor2.close();
                db.close();

                Desistir();
                return false;
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erro: " + e, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void Parar() {
        Intent getIntent = getIntent();
        String email = getIntent.getStringExtra("Email");

        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(getApplicationContext());

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        int Money = 0;

        Cursor cursor2 = db.rawQuery("SELECT * FROM utilizador WHERE email = ?", new String[]{email});
        if (cursor2.moveToFirst()) {
            Money = cursor2.getInt(cursor2.getColumnIndexOrThrow("dinheiro")); // Pega o valor do dinheiro atual
        }

        if (nivel >= 15) {
            dinheiro = 2000000;
        } else if (nivel >= 10) {
            dinheiro = 1000000;
        } else if (nivel >= 5) {
            dinheiro = 10000;
        }

        int soma = Money + dinheiro;

        values.put("dinheiro", soma);

        int rows = db.update("utilizador", values, "email = ?", new String[] {email});

        if (rows > 0) { //Número de linhas atualizadas > 0
            Log.d("dinheiro", "Dinheiro atualizado com sucesso!");
            Toast.makeText(this, "Ganhou " + dinheiro + " €", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("dinheiro", "Erro ao atualizar dinheiro!");
        }

        cursor2.close();
        db.close();

        Desistir();
    }

    public void Desistir() {
        Intent getIntent = getIntent();
        Intent Dashboard = new Intent(this, DashboardActivity.class);

        String email = getIntent.getStringExtra("Email");

        Dashboard.putExtra("Email", email);

        startActivity(Dashboard);
    }
}