package com.group9.buyall;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    // Biến tham chiếu Firebase Realtime Database
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.signup);

        // Khởi tạo Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Ánh xạ các trường nhập liệu và nút Sign Up
        EditText usernameEditText = findViewById(R.id.username);
        EditText emailEditText = findViewById(R.id.email);
        EditText phoneNumberEditText = findViewById(R.id.phone_number);
        EditText passwordEditText = findViewById(R.id.password);
        EditText confirmPasswordEditText = findViewById(R.id.confirm_password);
        Button signUpButton = findViewById(R.id.sign_up_button);
        TextView signInText = findViewById(R.id.sign_in_text);

        // Điều hướng đến màn hình Sign In
        signInText.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp.this, SignIn.class);
            startActivity(intent);
            finish();
        });

        // Sự kiện nút Sign Up
        signUpButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String phoneNumber = phoneNumberEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            // Kiểm tra đầu vào
            if (TextUtils.isEmpty(username)) {
                usernameEditText.setError("Username is required!");
                return;
            }

            if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.setError("Invalid email format!");
                return;
            }

            if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() != 10 || !phoneNumber.matches("\\d+")) {
                phoneNumberEditText.setError("Phone number must be exactly 10 digits!");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                passwordEditText.setError("Password is required!");
                return;
            }

            if (!password.equals(confirmPassword)) {
                confirmPasswordEditText.setError("Passwords do not match!");
                return;
            }

            // Kiểm tra tên người dùng đã tồn tại hay chưa
            databaseReference.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        usernameEditText.setError("Username already exists! Please choose another one.");
                    } else {
                        // Lưu dữ liệu vào Firebase
                        User user = new User(username, username, email, phoneNumber, password);

                        databaseReference.child(username).setValue(user)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUp.this, "User registered successfully!", Toast.LENGTH_SHORT).show();
                                        // Điều hướng đến màn hình Sign In
                                        Intent intent = new Intent(SignUp.this, SignIn.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(SignUp.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(SignUp.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
