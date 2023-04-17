package com.example.cargo;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ProfileMenu extends AppCompatActivity {
    User userData = new User();
    TextView textPib, textEmail, textPhone, textAdress;
    Button changeDataBtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_menu);
        changeDataBtn = findViewById(R.id.change_data_button);
        textPib = findViewById(R.id.text_pib);
        textEmail = findViewById(R.id.text_email);
        textPhone = findViewById(R.id.text_phone);
        textAdress = findViewById(R.id.text_address);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(uid);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User userData = dataSnapshot.getValue(User.class);
                    //Відображення даних
                    textAdress.setText(userData.getAddress());
                    textEmail.setText(userData.getEmail());
                    textPhone.setText(userData.getPhone());
                    textPib.setText(userData.getPib());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // тут можна обробити помилку
            }
        });

        changeDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileMenu.this, ChangeDataActivity.class);
                startActivity(intent);
            }
        });

    }
}