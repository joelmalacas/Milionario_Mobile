package com.example.milionario;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DefinicoesActivity extends AppCompatActivity {

    private ImageView Logo;
    private Button buttonBack;
    private Button ButtonReset;
    private Button ButtonSave;
    private EditText editTextPassword;
    private EditText editTextUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_definicoes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Declarar os ID's
        Logo = findViewById(R.id.LogoView);
        editTextUser = findViewById(R.id.editTextUser);
        editTextPassword = findViewById(R.id.editTextPassword);
        ButtonSave = findViewById(R.id.ButtonSave);
        ButtonReset = findViewById(R.id.ButtonReset);
        buttonBack = findViewById(R.id.buttonBack);

        Logo.setImageResource(R.mipmap.logo);

        //Update Dados SQL
        ButtonSave.setOnClickListener(view -> Guardar());
        ButtonReset.setOnClickListener(view -> Reset());

        buttonBack.setOnClickListener(view -> voltar());
    }

    public void Guardar() {
        // Instância do DB Helper
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(getApplicationContext());

        //Base dados Modo Escrita
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Criar objeto ContentValues para armazenar os novos valores
        ContentValues values = new ContentValues();
        values.put("nome", editTextUser.getText().toString());
        values.put("password", editTextPassword.getText().toString());

        Intent intent = getIntent();
        // Recupera o valor associado à chave "Email"
        String email = intent.getStringExtra("E_mail");

        if (!editTextPassword.getText().toString().isEmpty() && !editTextPassword.getText().toString().isEmpty()) {
            try {
                int rows = db.update("utilizador",values,"email = ?",new String[]{email});

                if (rows > 0) {
                    Toast.makeText(getApplicationContext(), "Atualizado com sucesso!!!", Toast.LENGTH_SHORT).show();

                    Intent Login = new Intent(this, MainActivity.class);
                    startActivity(Login);
                } else {
                    Toast.makeText(getApplicationContext(), "Nenhum registro encontrado para atualizar.", Toast.LENGTH_SHORT).show();
                    Intent Dashboard = new Intent(this, DashboardActivity.class);
                    String Email = intent.getStringExtra("E_mail");
                    Dashboard.putExtra("Email", Email);
                    startActivity(Dashboard);
                }

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Erro: " + e, Toast.LENGTH_SHORT).show();
                Intent Dashboard = new Intent(this, DashboardActivity.class);
                String Email = intent.getStringExtra("E_mail");
                Dashboard.putExtra("Email", Email);
                startActivity(Dashboard);
            }
            db.close();
        } else {
            Toast.makeText(getApplicationContext(), "Campo não preenchido", Toast.LENGTH_SHORT).show();
        }
    }

    public void Reset() {
        Intent intent = getIntent();
        // Recupera o valor associado à chave "Email"
        String email = intent.getStringExtra("E_mail");

        // Instância do DB Helper
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(getApplicationContext());

        //Base dados Modo Escrita
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("dinheiro", 0);

        try {
           int rows = db.update("utilizador", values, "email = ?", new String[]{email});

           if (rows > 0) {
               Toast.makeText(getApplicationContext(), "Reset efetuado com sucesso!!!", Toast.LENGTH_SHORT).show();
           } else {
               Toast.makeText(getApplicationContext(), "Erro ao atualizar dados", Toast.LENGTH_SHORT).show();
           }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Erro: " + e, Toast.LENGTH_SHORT).show();
        }

        db.close();

        Intent Dash = new Intent(this, DashboardActivity.class);

        String Email = intent.getStringExtra("E_mail");
        Dash.putExtra("Email", Email);

        startActivity(Dash);
    }

    public void voltar() {
        Intent getIntent = getIntent();
        Intent voltar = new Intent(this, DashboardActivity.class);

        String email = getIntent.getStringExtra("E_mail");

        voltar.putExtra("Email", email);

        startActivity(voltar);
    }
}