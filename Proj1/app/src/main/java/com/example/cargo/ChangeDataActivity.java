package com.example.cargo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ChangeDataActivity extends AppCompatActivity {
    EditText editPib, editAddress, editPhone;
    Button acceptChangesButton, cancelChanges;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_data);
        editPib = findViewById(R.id.edit_pib);
        editAddress = findViewById(R.id.edit_address);
        editPhone = findViewById(R.id.edit_phone);
        acceptChangesButton = findViewById(R.id.button_accept_changes);

        acceptChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                cancelChanges = findViewById(R.id.cancel_changes_button);

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(uid);

                String pib = editPib.getText().toString();
                String address = editAddress.getText().toString();
                String phone = editPhone.getText().toString();

                Map<String, Object> userUpdates = new HashMap<>();
                userUpdates.put("pib", pib);
                userUpdates.put("email", user.getEmail());
                userUpdates.put("address", address);
                userUpdates.put("phone", phone);

                databaseReference.updateChildren(userUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ChangeDataActivity.this, "Дані успішно оновлені", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ChangeDataActivity.this, "Помилка оновлення даних", Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = new Intent(ChangeDataActivity.this, ProfileMenu.class);
                        startActivity(intent);
                    }
                });

                cancelChanges.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ChangeDataActivity.this, ProfileMenu.class);
                        startActivity(intent);
                    }
                });

            }
        });
    }
}