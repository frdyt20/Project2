package com.example.project2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    EditText editUsername, editPassword, editEmail,
            editFullname, editSchool, editAddress;
    Button btnSave;
    TextView tvPassword;

    public static final String FILENAME = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editUsername = findViewById(R.id.et_user_reg);
        editPassword = findViewById(R.id.et_pass_reg);
        editEmail = findViewById(R.id.et_email_reg);
        editFullname = findViewById(R.id.et_fullname_reg);
        editSchool = findViewById(R.id.et_school_reg);
        editAddress = findViewById(R.id.et_address_reg);
        tvPassword = findViewById(R.id.tv_pass_reg);
        btnSave = findViewById(R.id.btn_save_reg);

        btnSave.setVisibility(View.GONE);
        editUsername.setEnabled(false);
        editPassword.setVisibility(View.GONE);
        tvPassword.setVisibility(View.GONE);
        editEmail.setEnabled(false);
        editFullname.setEnabled(false);
        editSchool.setEnabled(false);
        editAddress.setEnabled(false);

        readLoginData();
    }

    void readLoginData() {
        File sdcard = getFilesDir();
        File file = new File(sdcard, FILENAME);

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
            String[] userData = data.split(";");
            readUserData(userData[0]);

        }
    }

    void readUserData(String fileName) {
        File sdcard = getFilesDir();
        File file = new File(sdcard, fileName);

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

            editUsername.setText(dataUser[0]);
            editEmail.setText(dataUser[2]);
            editFullname.setText(dataUser[3]);
            editSchool.setText(dataUser[4]);
            editAddress.setText(dataUser[5]);

        } else {
            Toast.makeText(this, "Username tidak ditemukan, harap Register terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                viewLogoutConfirmMsg();
                break;

            case R.id.menu_erase_data:
                eraseData();
                Toast.makeText(getApplicationContext(),
                        "Data Berhasil dihapus", Toast.LENGTH_LONG).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    void eraseData() {
        File file = new File(getFilesDir(), FILENAME);
        if (file.exists()) {
            file.deleteOnExit();
        } Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    void viewLogoutConfirmMsg() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Apakah Anda yakin ingin Logout?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }
}