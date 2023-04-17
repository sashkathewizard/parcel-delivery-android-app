package com.example.cargo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference ordersDatabase;
    private String dateString;
    private User sender;
    private User recipient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        ordersDatabase = FirebaseDatabase.getInstance().getReference();

        // Отримуємо поточну дату
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateString = dateFormat.format(currentDate);

        // Отримуємо дані відправника з бази даних
        DatabaseReference senderRef = FirebaseDatabase.getInstance().getReference("users/" + mCurrentUser.getUid());
        senderRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    sender = snapshot.getValue(User.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Обробляємо помилки
            }
        });

        // Кнопка "Додати товар"
        Button orderButton = findViewById(R.id.order_button);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Отримуємо дані з віджетів активності
                String productName = ((EditText) findViewById(R.id.product_name)).getText().toString();
                String deliveryAddress = ((EditText) findViewById(R.id.delivery_address)).getText().toString();
                String pibOtr = ((EditText) findViewById(R.id.pib_otpr)).getText().toString();
                String weight = ((EditText) findViewById(R.id.weight)).getText().toString();
                String phone = ((EditText) findViewById(R.id.phone)).getText().toString();
                String status = "Очікує підтвердження";

                // Створюємо об'єкт отримувача
                recipient = new User(pibOtr, "none", deliveryAddress, phone);

                // Створюємо об'єкт для збереження даних посилки
                PackageItem orderPackage = new PackageItem(sender.getPib(), sender.getAddress(), sender.getPhone(),
                        recipient.getPib(), recipient.getAddress(), recipient.getPhone(), weight, productName, status, dateString);

                // Записуємо дані посилки в базу даних
                Map<String, Object> orderData = new HashMap<>();
                orderData.put("senderPib", sender.getPib());
                orderData.put("senderAddress", sender.getAddress());
                orderData.put("senderPhone", sender.getPhone());
                orderData.put("recipientPib", recipient.getPib());
                orderData.put("recipientAddress", recipient.getAddress());
                orderData.put("recipientPhone", recipient.getPhone());
                orderData.put("productName", productName);
                orderData.put("weight", weight);
                orderData.put("status", status);
                orderData.put("date", dateString);
                ordersDatabase.child("orders").child(mCurrentUser.getUid()).push().setValue(orderData)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Перехід на головний екран
                                    Intent intent = new Intent(OrderActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Обробляємо помилки
                                }
                            }
                        });
            }
        });
    }
}
