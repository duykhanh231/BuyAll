package com.group9.buyall;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.forgotpassword);

        // Cài đặt padding cho bố cục chính
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Forgotpd), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Sự kiện khi nhấn vào "Back to Sign In"
        TextView signInText = findViewById(R.id.sign_in_text);
        signInText.setOnClickListener(v -> {
            Intent intent = new Intent(ForgotPassword.this, SignIn.class);
            startActivity(intent);
            finish(); // Đóng ForgotPasswordMainActivity
        });
    }
}