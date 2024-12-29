package com.group9.buyall;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Ánh xạ các trường nhập liệu và nút Sign Up
        EditText fullNameEditText = findViewById(R.id.full_name);
        EditText usernameEditText = findViewById(R.id.username); // thêm trường username
        EditText emailEditText = findViewById(R.id.email);
        EditText phoneNumberEditText = findViewById(R.id.phone_number); // thêm trường phone number
        EditText passwordEditText = findViewById(R.id.password);
        EditText confirmPasswordEditText = findViewById(R.id.confirm_password);
        Button signUpButton = findViewById(R.id.sign_up_button);
        TextView signInText = findViewById(R.id.sign_in_text);

        // Chuyển sang màn hình Sign In
        signInText.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp.this, SignIn.class);
            startActivity(intent);
            finish();
        });

        // Xử lý đăng ký khi người dùng bấm nút Sign Up
        signUpButton.setOnClickListener(v -> {
            String fullName = fullNameEditText.getText().toString().trim();
            String username = usernameEditText.getText().toString().trim(); // Lấy giá trị từ trường username
            String email = emailEditText.getText().toString().trim();
            String phoneNumber = phoneNumberEditText.getText().toString().trim(); // Lấy giá trị từ trường phone number
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(fullName)) {
                fullNameEditText.setError("Full name is required!");
                return;
            }

            if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.setError("Invalid email format!");
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

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                // Gửi email xác minh
                                user.sendEmailVerification()
                                        .addOnCompleteListener(emailTask -> {
                                            if (emailTask.isSuccessful()) {
                                                Toast.makeText(SignUp.this, "Verification email sent. Please check your inbox.", Toast.LENGTH_SHORT).show();

                                                new Thread(() -> {
                                                    while (true) {
                                                        user.reload();
                                                        if (user.isEmailVerified()) {
                                                            // Lưu tất cả thông tin người dùng vào Realtime Database
                                                            saveUserToDatabase(user.getUid(), fullName, username, email, phoneNumber, password);
                                                            runOnUiThread(() -> {
                                                                Toast.makeText(SignUp.this, "Email verified successfully!", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(SignUp.this, SignIn.class);
                                                                startActivity(intent);
                                                                finish();
                                                            });
                                                            break;
                                                        }
                                                        try {
                                                            Thread.sleep(3000); // Kiểm tra mỗi 3 giây
                                                        } catch (InterruptedException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }).start();

                                            } else {
                                                Toast.makeText(SignUp.this, "Failed to send verification email. Please try again.", Toast.LENGTH_SHORT).show();
                                                user.delete(); // Xóa tài khoản nếu email không gửi thành công
                                            }
                                        });
                            }
                        } else {
                            Toast.makeText(SignUp.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    // Lưu thông tin người dùng vào Realtime Database
    private void saveUserToDatabase(String userId, String fullName, String username, String email, String phoneNumber, String password) {
        User user = new User(userId, fullName, username, email, phoneNumber, password);
        databaseReference.child(userId).setValue(user)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(SignUp.this, "Failed to save user data. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
