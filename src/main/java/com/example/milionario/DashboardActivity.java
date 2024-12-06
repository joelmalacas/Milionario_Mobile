package com.example.milionario;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DashboardActivity extends AppCompatActivity {

    private ImageView Logo;
    private TextView Jogo;
    private TextView TextLogOut;
    private TextView TextDef;
    private TextView textBoas;
    private TextView textDinheiro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Declarar os ID's
        Logo = findViewById(R.id.LogoView);
        Jogo = findViewById(R.id.textJogo);
        TextLogOut = findViewById(R.id.textLogout);
        TextDef = findViewById(R.id.textDef);
        textBoas = findViewById(R.id.textBoas);
        textDinheiro = findViewById(R.id.textDinheiro);

        Logo.setImageResource(R.mipmap.logo);

        //Event onclick para Logout e para retornar a MainActivity
        Intent Logout = new Intent(this, MainActivity.class);
        TextLogOut.setOnClickListener(view -> startActivity(Logout));

        //Event onclick para activity das Definições
        Intent Def = new Intent(this, DefinicoesActivity.class);
        TextDef.setOnClickListener(view -> startActivity(Def));

        //Evento onclick para activity GameActivity
        Intent Jogar = new Intent(this, GameActivity.class);
        Jogo.setOnClickListener(view -> startActivity(Jogar));


        Intent Dashboard = getIntent(); // INTENT PARA IR BUSCAR VALORES NA MainActivity

        //Obter String da MainActivity
        String email = Dashboard.getStringExtra("Email");

        //Colocar a String para o GameAcitivy e Def Activity
        Jogar.putExtra("Email", email);
        Def.putExtra("E_mail", email);


        //==========================================
        //======MOSTRAR / ATUALIZAR DINHEIRO =======

        //Instância DB
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(getApplicationContext());

        //Modo Leitura DB
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM utilizador WHERE email = '" + email + "'", null);

        if (cursor.moveToFirst()) {
            String Money = cursor.getString(cursor.getColumnIndexOrThrow("dinheiro"));
            String Nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
            textDinheiro.setText("Dinheiro: " + Money + " €");
            textBoas.setText("Bem-vindo/a " + Nome);
        } else {
            textDinheiro.setText("Dinheiro: ---");
        }
        cursor.close();
    }
}