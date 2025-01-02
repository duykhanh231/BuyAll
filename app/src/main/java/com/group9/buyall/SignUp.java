package com.group9.buyall;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

        // Ánh xạ các trường nhập liệu và nút
        EditText fullNameEditText = findViewById(R.id.full_name);
        EditText usernameEditText = findViewById(R.id.username);
        EditText emailEditText = findViewById(R.id.email);
        EditText phoneNumberEditText = findViewById(R.id.phone_number);
        EditText passwordEditText = findViewById(R.id.password);
        EditText confirmPasswordEditText = findViewById(R.id.confirm_password);
        Button signUpButton = findViewById(R.id.sign_up_button);
        TextView signInText = findViewById(R.id.sign_in_text);

        // Ánh xạ biểu tượng mắt
        ImageView showPasswordIconOff = findViewById(R.id.show_password_icon_off);
        ImageView showPasswordIconOn = findViewById(R.id.show_password_icon_on);
        ImageView showConfirmPasswordIconOff = findViewById(R.id.show_confirm_password_icon_off);
        ImageView showConfirmPasswordIconOn = findViewById(R.id.show_confirm_password_icon_on);

        // Chuyển sang màn hình Sign In
        signInText.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp.this, SignIn.class);
            startActivity(intent);
            finish();
        });

        // Xử lý hiển thị/ẩn mật khẩu cho trường Password
        showPasswordIconOff.setOnClickListener(v -> {
            passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            showPasswordIconOff.setVisibility(View.GONE);
            showPasswordIconOn.setVisibility(View.VISIBLE);
        });

        showPasswordIconOn.setOnClickListener(v -> {
            passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            showPasswordIconOn.setVisibility(View.GONE);
            showPasswordIconOff.setVisibility(View.VISIBLE);
        });

        // Xử lý hiển thị/ẩn mật khẩu cho trường Confirm Password
        showConfirmPasswordIconOff.setOnClickListener(v -> {
            confirmPasswordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            showConfirmPasswordIconOff.setVisibility(View.GONE);
            showConfirmPasswordIconOn.setVisibility(View.VISIBLE);
        });

        showConfirmPasswordIconOn.setOnClickListener(v -> {
            confirmPasswordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            showConfirmPasswordIconOn.setVisibility(View.GONE);
            showConfirmPasswordIconOff.setVisibility(View.VISIBLE);
        });

        // Xử lý đăng ký khi người dùng bấm nút Sign Up
        signUpButton.setOnClickListener(v -> {
            String fullName = fullNameEditText.getText().toString().trim();
            String username = usernameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String phoneNumber = phoneNumberEditText.getText().toString().trim();
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

            if (!isPasswordComplex(password)) {
                passwordEditText.setError("Password must have at least 6 characters, including an uppercase letter, a lowercase letter, a number, and a special character!");
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
                                user.sendEmailVerification()
                                        .addOnCompleteListener(emailTask -> {
                                            if (emailTask.isSuccessful()) {
                                                Toast.makeText(SignUp.this, "Verification email sent. Please check your inbox.", Toast.LENGTH_SHORT).show();

                                                new Thread(() -> {
                                                    while (true) {
                                                        user.reload();
                                                        if (user.isEmailVerified()) {
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
                                                            Thread.sleep(2000); // Kiểm tra mỗi 3 giây
                                                        } catch (InterruptedException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }).start();

                                            } else {
                                                Toast.makeText(SignUp.this, "Failed to send verification email. Please try again.", Toast.LENGTH_SHORT).show();
                                                user.delete();
                                            }
                                        });
                            }
                        } else {
                            Toast.makeText(SignUp.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    private boolean isPasswordComplex(String password) {
        // Biểu thức chính quy kiểm tra độ phức tạp
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";
        return password.matches(passwordPattern);
    }

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
