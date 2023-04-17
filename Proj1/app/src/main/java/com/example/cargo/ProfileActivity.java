package com.example.cargo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ArrayList<PackageItem> packageList = new ArrayList<>();
        PackageAdapter packageAdapter = new PackageAdapter(this, packageList);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(packageAdapter);

        // Отримання посилання на вузол "orders" з бази даних Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("orders/" + user.getUid());

        // Додавання слухача для отримання даних з бази даних Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Очищення списку перед додаванням нових даних
                packageList.clear();

                // Додавання об'єктів Package до списку
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    PackageItem packageItem = childSnapshot.getValue(PackageItem.class);
                    packageList.add(packageItem);
                }

                //Оновлення ListView
                packageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ProfileActivity", "Failed to read packages", databaseError.toException());
                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
            }
        });

        //Обробка натискання по listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Викликайте метод, що відображає повну інформацію про елемент
                showPackageDetails(packageList.get(position));
            }
        });
    }
    private void showPackageDetails(PackageItem packageItem) {
        // Створюйте новий діалоговий вікно, що містить всю інформацію про елемент
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(packageItem.getProductName())
                .setMessage("Повна інформація про посилку:\n" +
                        "\nВідправник\n" +
                        "ПІБ:  " + packageItem.getSenderPib() + "\n" +
                        "Адреса:  " + packageItem.getSenderAddress() + "\n" +
                        "Тел.:  " + packageItem.getSenderPhone() + "\n" +
                        "\nОдержувач\n" +
                        "ПІБ:  " + packageItem.getRecipientPib() + "\n" +
                        "Адреса:  " + packageItem.getRecipientAddress() + "\n" +
                        "Тел.:  " + packageItem.getRecipientPhone() + "\n" +
                        "\nХарактеристики посилки\n" +
                        "Вага: " + packageItem.getWeight() + "\n" +
                        "Статус:  " + packageItem.getStatus() + "\n" +
                        "Дата оформлення:  " + packageItem.getDate() + "\n" +
                        "Опис: " + packageItem.getProductName())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Закрийте діалогове вікно при натисканні на кнопку OK
                        dialog.dismiss();
                    }
                });

        // Показати діалогове вікно
        AlertDialog dialog = builder.create();
        dialog.show();
    }



}

