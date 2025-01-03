package com.group9.buyall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderHistoryActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_history);

        TextView cancelButton = findViewById(R.id.cancel_text);
        cancelButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, HomePageActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
