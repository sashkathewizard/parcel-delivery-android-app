package com.example.cargo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PackageAdapter extends ArrayAdapter<PackageItem> {
    public PackageAdapter(Context context, ArrayList<PackageItem> packages) {
        super(context, 0, packages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Перевірка, чи існує convertView. Якщо ні, то створення нового елемента списку
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Отримання поточного об'єкту Package
        PackageItem currentPackage = getItem(position);

        // Заповнення елемента списку даними з об'єкту Package
        TextView tvProductName = convertView.findViewById(R.id.tv_product_name);
        tvProductName.setText(currentPackage.getProductName());

        TextView tvDeliveryAddress = convertView.findViewById(R.id.tv_delivery_address);
        tvDeliveryAddress.setText(currentPackage.getRecipientAddress());

        TextView tvContactInfo = convertView.findViewById(R.id.tv_contact_info);
        tvContactInfo.setText(currentPackage.getRecipientPib());

        TextView tvStatus = convertView.findViewById(R.id.tv_status);
        String status = currentPackage.getStatus();

        if (status.equals("В дорозі")) {
            tvStatus.setTextColor(Color.YELLOW);
        } else if (status.equals("Прибув у відділення")) {
            tvStatus.setTextColor(Color.GREEN);
        } else if (status.equals("Відмінено")) {
            tvStatus.setTextColor(Color.RED);
        } else {
            tvStatus.setTextColor(Color.BLACK);
        }

        tvStatus.setText(status);

        TextView tvDate = convertView.findViewById(R.id.tv_date);
        tvDate.setText(currentPackage.getDate());


        // Повернення елемента списку
        return convertView;
    }
}
