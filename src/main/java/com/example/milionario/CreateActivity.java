package com.example.milionario;

import static android.content.ContentValues.TAG;
import static java.security.AccessController.getContext;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateActivity extends AppCompatActivity {

    private ImageView Logo;
    private TextView TenhoConta;
    private Button buttonCriar;
    private EditText TextNome;
    private EditText TextEmail;
    private EditText TextCreatePassword;
    private EditText TextConfPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Declarar os ID's
        Logo = findViewById(R.id.LogoView);
        TenhoConta = findViewById(R.id.TextBackMain);
        buttonCriar = findViewById(R.id.buttonCriar);
        TextNome = findViewById(R.id.TextNome);
        TextEmail = findViewById(R.id.TextEmail);
        TextCreatePassword = findViewById(R.id.TextCreatePassword);
        TextConfPassword = findViewById(R.id.TextConfPassword);

        Logo.setImageResource(R.mipmap.logo);

        //Evento Onlick para retornar a main
        Intent intent = new Intent(this, MainActivity.class);
        TenhoConta.setOnClickListener(view -> startActivity(intent));

        buttonCriar.setOnClickListener(view -> CreateUser());
    }

    public void CreateUser() {
        try {
            //Instância DB
            FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(getApplicationContext());

            //Modo Escrita DB
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();

            //Condições FORM
            if (TextNome.length() == 0) {
                Toast.makeText(getApplicationContext(), "Erro: insira o nome", Toast.LENGTH_SHORT).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(TextEmail.getText()).matches() || TextEmail.length() == 0) {
                Toast.makeText(getApplicationContext(), "Erro: insira e-mail", Toast.LENGTH_SHORT).show();
            } else if (TextCreatePassword.length() == 0 || TextConfPassword.length() == 0) {
                Toast.makeText(getApplicationContext(), "Erro: insira password", Toast.LENGTH_SHORT).show();
            } else if (!TextCreatePassword.getText().toString().equals(TextConfPassword.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Erro: password's não condicidem", Toast.LENGTH_SHORT).show();
            } else {
                values.put("nome", TextNome.getText().toString());
                values.put("email", TextEmail.getText().toString());
                values.put("password", TextConfPassword.getText().toString());
                values.put("dinheiro", 0);

                db.insert("utilizador", null, values);
                Toast.makeText(getApplicationContext(), "Conta criada com sucesso", Toast.LENGTH_SHORT).show();

                Intent login = new Intent(this, MainActivity.class);
                startActivity(login);

                db.close();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Erro: " + e, Toast.LENGTH_SHORT).show();
        }
    }
}