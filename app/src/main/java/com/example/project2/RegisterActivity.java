package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;

public class RegisterActivity extends AppCompatActivity {
    EditText editUsername, editPassword, editEmail, editFullname, editSchool, editAddress;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editUsername = findViewById(R.id.et_user_reg);
        editPassword = findViewById(R.id.et_pass_reg);
        editEmail = findViewById(R.id.et_email_reg);
        editFullname = findViewById(R.id.et_fullname_reg);
        editSchool = findViewById(R.id.et_school_reg);
        editAddress = findViewById(R.id.et_address_reg);
        btnSave = findViewById(R.id.btn_save_reg);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidation()) {
                    saveRegisterData();
                } else {
                    Toast.makeText(RegisterActivity.this, "Mohon Lengkapi Seluruh Data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    boolean isValidation() {
        if (editUsername.getText().toString().equals("") ||
                editPassword.getText().toString().equals("") ||
                editEmail.getText().toString().equals("") ||
                editFullname.getText().toString().equals("") ||
                editSchool.getText().toString().equals("") ||
                editAddress.getText().toString().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    void saveRegisterData() {
        String registerData = editUsername.getText().toString()
                + ";" + editPassword.getText().toString()
                + ";" + editEmail.getText().toString()
                + ";" + editFullname.getText().toString()
                + ";" + editSchool.getText().toString()
                + ";" + editAddress.getText().toString();

        File file = new File(getFilesDir(), editUsername.getText().toString());
        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file, false);
            outputStream.write(registerData.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }
}