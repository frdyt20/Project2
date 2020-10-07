package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    public static final String FILENAME = "login";

    EditText editUsername, editPassword;
    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editUsername = findViewById(R.id.et_user);
        editPassword = findViewById(R.id.et_pass);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((editUsername.getText().length() > 0 ) && (editPassword.getText().length() > 0)) {
                    login();
                } else {
                    Toast toast = Toast.makeText(LoginActivity.this, "Mohon Input Username dan Password " +
                            "anda dengan benar", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    void saveLoginData() {
        String loginData = editUsername.getText().toString() + ";" + editPassword.getText().toString();

        File file = new File(getFilesDir(), FILENAME);
        FileOutputStream outputStream = null;

        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file, false);
            outputStream.write(loginData.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    void login() {
        File sdcard = getFilesDir();
        File file = new File(sdcard, editUsername.getText().toString());

        if (file.exists()) {
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br =
                        new BufferedReader(new FileReader(file));
                String line = br.readLine();
                while (line != null) {
                    text.append(line);
                    line = br.readLine();
                }
                br.close();
            } catch (IOException e) {
                System.out.println("Error " + e.getMessage());
            }

            String data = text.toString();
            String[] dataUser = data.split(";");

            if (dataUser[1].equals(editPassword.getText().toString())) {
                saveLoginData();

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(this, "Password Tidak Sesuai", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "User Tidak Ditemukan", Toast.LENGTH_SHORT).show();
        }
    }
}