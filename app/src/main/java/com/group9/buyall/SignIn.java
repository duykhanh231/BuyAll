package com.group9.buyall;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.Button;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.signin);

        // Set padding for main layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.SigIn), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Click event for "Forgot password?" TextView
        TextView forgotPasswordText = findViewById(R.id.forgot_password);
        forgotPasswordText.setOnClickListener(v -> {
            Intent intent = new Intent(SignIn.this, ForgotPassword.class);
            startActivity(intent);
        });

        // Click event for "Don't have an account? Sign up" TextView
        TextView signUpText = findViewById(R.id.sign_up_text);
        signUpText.setOnClickListener(v -> {
            Log.d("SignInMainActivity", "Sign up text clicked");
            Intent intent = new Intent(SignIn.this, SignUp.class);
            startActivity(intent);
        });

        Button logInButton = findViewById(R.id.login_button);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, HomePageActivity.class);
                startActivity(intent);
            }
        });
    }
}
