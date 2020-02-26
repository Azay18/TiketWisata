package com.example.tiketwisata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    TextView btn_new_account;
    Button btn_sign_in;
    EditText username, password;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btn_new_account = findViewById(R.id.btn_new_account);
        btn_sign_in = findViewById(R.id.btn_sign_in);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        btn_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoregisterone = new Intent(SignIn.this, RegisterOne.class);
                startActivity(gotoregisterone);
            }
        });

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ubah state menjadi loading
                btn_sign_in.setEnabled(false);
                btn_sign_in.setText("Loading...");

                final String xusername = username.getText().toString();
                final String xpassword = password.getText().toString();

                if (xusername.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username tidak ada!", Toast.LENGTH_SHORT).show();
                    // ubah button loading
                    btn_sign_in.setEnabled(true);
                    btn_sign_in.setText("Sign In");
                } else {
                    if (xpassword.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Password salah!", Toast.LENGTH_SHORT).show();
                        // ubah button loading
                        btn_sign_in.setEnabled(true);
                        btn_sign_in.setText("Sign In");
                    } else {
                        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(xusername);
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    // ambil data password dari firebase
                                    String passwodFromFirebase = dataSnapshot.child("password").getValue().toString();

                                    // validasi akun
                                    if (xpassword.equals(passwodFromFirebase)){
                                        // simpan username (key) ke lokal
                                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username_key, username.getText().toString());
                                        editor.apply();

                                        // pinda activity
                                        Intent gotohome = new Intent(SignIn.this, Home.class);
                                        startActivity(gotohome);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Password salah!", Toast.LENGTH_SHORT);
                                        // ubah button loading
                                        btn_sign_in.setEnabled(true);
                                        btn_sign_in.setText("Sign In");
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Username tidak ada!", Toast.LENGTH_SHORT);
                                    // ubah button loading
                                    btn_sign_in.setEnabled(true);
                                    btn_sign_in.setText("Sign In");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
        });
    }
}
