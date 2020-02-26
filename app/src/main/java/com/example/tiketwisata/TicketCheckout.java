package com.example.tiketwisata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class TicketCheckout extends AppCompatActivity {

    Button btn_buy_ticket, btn_minus, btn_plus;
    TextView jumlah_tiket, textTotalHarga, textMyBalance, nama_wisata, lokasi, ketentuan;
    Integer valueJumlahTiket = 1;
    Integer myBalance = 0;
    Integer valueTotalHarga = 0;
    Integer valueHargaTiket = 0;
    ImageView notice_uang;
    Integer sisa_balance = 0;
    LinearLayout btn_back;

    DatabaseReference reference, reference2, reference3, reference4, ref_username;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    String date_wisata = "";
    String time_wisata = "";

    Integer nomor_transaksi = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);

        getUsernameLocal();

        // mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis_tiket");

        btn_buy_ticket = findViewById(R.id.btn_buy_ticket);
        btn_minus = findViewById(R.id.btn_minus);
        btn_plus = findViewById(R.id.btn_plus);
        btn_back = findViewById(R.id.btn_back);

        jumlah_tiket = findViewById(R.id.jumlah_tiket);
        notice_uang = findViewById(R.id.notice_uang);

        nama_wisata = findViewById(R.id.nama_wisata);
        lokasi = findViewById(R.id.lokasi);
        ketentuan = findViewById(R.id.ketentuan);

        textTotalHarga = findViewById(R.id.textTotalHarga);
        textMyBalance = findViewById(R.id.textMyBalance);

        // setting value
        jumlah_tiket.setText(valueJumlahTiket.toString());
        valueTotalHarga = valueHargaTiket * valueJumlahTiket;
        textTotalHarga.setText("US$ " + valueTotalHarga + "");

        // secara default, tampilan awal hide button minus
        btn_minus.animate().alpha(0).setDuration(300).start();
        btn_minus.setEnabled(false);
        notice_uang.setVisibility(View.GONE);

        // mengambil data user dari firebase
        // textMyBalance.setText("US$ " + myBalance + "");
        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myBalance = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                textMyBalance.setText("US$ " + myBalance + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // mengambil data dari firebase berdasarkan intent
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // set data
                nama_wisata.setText((dataSnapshot.child("nama_wisata").getValue().toString()));
                lokasi.setText((dataSnapshot.child("lokasi").getValue().toString()));
                ketentuan.setText((dataSnapshot.child("ketentuan").getValue().toString()));

                date_wisata = dataSnapshot.child("date_wisata").getValue().toString();
                time_wisata = dataSnapshot.child("time_wisata").getValue().toString();

                valueHargaTiket = Integer.valueOf(dataSnapshot.child("harga_tiket").getValue().toString());

                valueTotalHarga = valueHargaTiket * valueJumlahTiket;
                textTotalHarga.setText("US$ " + valueTotalHarga + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueJumlahTiket += 1;
                jumlah_tiket.setText(valueJumlahTiket.toString());
                if (valueJumlahTiket > 1){
                    btn_minus.animate().alpha(1).setDuration(300).start();
                    btn_minus.setEnabled(true);
                }
                valueTotalHarga = valueHargaTiket * valueJumlahTiket;
                textTotalHarga.setText("US$ " + valueTotalHarga + "");
                if (valueTotalHarga > myBalance){
                    btn_buy_ticket.animate().translationY(250).alpha(0).setDuration(350).start();
                    btn_buy_ticket.setEnabled(false);
                    textMyBalance.setTextColor(Color.parseColor("#D1206B"));
                    notice_uang.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueJumlahTiket -= 1;
                jumlah_tiket.setText(valueJumlahTiket.toString());
                if (valueJumlahTiket < 2){
                    btn_minus.animate().alpha(0).setDuration(300).start();
                    btn_minus.setEnabled(false);
                }
                valueTotalHarga = valueHargaTiket * valueJumlahTiket;
                textTotalHarga.setText("US$ " + valueTotalHarga + "");
                if (valueTotalHarga < myBalance){
                    btn_buy_ticket.animate().translationY(0).alpha(1).setDuration(350).start();
                    btn_buy_ticket.setEnabled(true);
                    textMyBalance.setTextColor(Color.parseColor("#203DD1"));
                    notice_uang.setVisibility(View.GONE);
                }
            }
        });

        btn_buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // menyimpan data user ke firebase membuat table "Tickets"
                textMyBalance.setText("US$ " + myBalance + "");
                reference3 = FirebaseDatabase.getInstance().getReference().child("Tickets").child(username_key_new).child("wisata").child(nama_wisata.getText().toString() + nomor_transaksi);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference3.getRef().child("id_tiket").setValue(nama_wisata.getText().toString() + nomor_transaksi);
                        reference3.getRef().child("nama_wisata").setValue(nama_wisata.getText().toString());
                        reference3.getRef().child("lokasi").setValue(lokasi.getText().toString());
                        reference3.getRef().child("ketentuan").setValue(ketentuan.getText().toString());
                        reference3.getRef().child("jumlah_tiket").setValue(valueJumlahTiket.toString());
                        reference3.getRef().child("date_wisata").setValue(date_wisata);
                        reference3.getRef().child("time_wisata").setValue(time_wisata);

                        Intent gotosuccessbuy = new Intent(TicketCheckout.this, SuccessBuyTicket.class);
                        startActivity(gotosuccessbuy);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                ref_username = FirebaseDatabase.getInstance().getReference().child("Tickets").child(username_key_new).child("username");
                ref_username.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ref_username.getRef().setValue(username_key_new);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                // update data balance
                reference4 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sisa_balance = myBalance - valueTotalHarga;
                        reference4.getRef().child("user_balance").setValue(sisa_balance);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
