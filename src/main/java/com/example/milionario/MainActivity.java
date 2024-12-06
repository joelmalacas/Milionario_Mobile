package com.example.milionario;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private ImageView Logo;
    private TextView CriarConta;
    private Button buttonLogin;
    private EditText TextNome;
    private EditText TextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Declarar os ID's
        Logo = findViewById(R.id.LogoView);
        CriarConta = findViewById(R.id.TextConta);
        buttonLogin = findViewById(R.id.ButtonLogin);
        TextNome = findViewById(R.id.TextNome);
        TextPassword = findViewById(R.id.TextPassword);

        Logo.setImageResource(R.mipmap.logo);

        //Mudar activity após clique no "Criar conta"
        Intent intent = new Intent(this, CreateActivity.class);
        CriarConta.setOnClickListener(view -> startActivity(intent));

        //Evento onclick para dashboard após verificar login na BD
        buttonLogin.setOnClickListener(view -> Login());
    }

    public void Login() {
        try{
            //Instância DB
            FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(getApplicationContext());

            //Modo Leitura DB
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            if (!Patterns.EMAIL_ADDRESS.matcher(TextNome.getText()).matches() || TextNome.length() == 0) {
                Toast.makeText(getApplication(), "Erro: introduz e-mail", Toast.LENGTH_SHORT).show();
            } else if (TextPassword.length() == 0) {
                Toast.makeText(getApplication(), "Erro: introduz password", Toast.LENGTH_SHORT).show();
            } else {
                Cursor cursor = db.rawQuery("SELECT * FROM utilizador WHERE email = '" + TextNome.getText().toString() + "'", null);
                if (cursor.moveToFirst()) {
                    String emailDB = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                    String passDB = cursor.getString(cursor.getColumnIndexOrThrow("password"));

                    if (TextNome.getText().toString().equals(emailDB) && TextPassword.getText().toString().equals(passDB)) {
                        Intent dashboard = new Intent(this,DashboardActivity.class);

                        //Usar o Intent para usar a variável na dashboard
                        dashboard.putExtra("Email", emailDB);
                        dashboard.putExtra("Pass", passDB);

                        startActivity(dashboard);
                    } else {
                        Toast.makeText(getApplicationContext(), "Credenciais inválidas", Toast.LENGTH_SHORT).show();
                        TextNome.setText("");
                        TextPassword.setText("");
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Credenciais inválidas", Toast.LENGTH_SHORT).show();
                    TextNome.setText("");
                    TextPassword.setText("");
                }
            }
            db.close();
        } catch (Exception e) {
            Toast.makeText(getApplication(), "Erro: " + e, Toast.LENGTH_SHORT).show();
        }
    }
}