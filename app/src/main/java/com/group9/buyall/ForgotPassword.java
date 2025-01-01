package com.group9.buyall;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ForgotPassword extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private EditText usernameEditText, emailEditText, newPasswordEditText, confirmNewPasswordEditText;
    private Button resetPasswordButton;
    private ImageView showPasswordIconOff, showPasswordIconOn, showConfirmPasswordIconOff, showConfirmPasswordIconOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.forgotpassword);

        // Khởi tạo Firebase Database Reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Ánh xạ các trường giao diện
        usernameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.email);
        newPasswordEditText = findViewById(R.id.new_password);
        confirmNewPasswordEditText = findViewById(R.id.confirm_new_password);
        resetPasswordButton = findViewById(R.id.reset_password_button);

        // Ánh xạ các biểu tượng con mắt
        showPasswordIconOff = findViewById(R.id.show_password_icon_off);
        showPasswordIconOn = findViewById(R.id.show_password_icon_on);
        showConfirmPasswordIconOff = findViewById(R.id.show_confirm_password_icon_off);
        showConfirmPasswordIconOn = findViewById(R.id.show_confirm_password_icon_on);

        // Cài đặt padding cho bố cục chính
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Forgotpd), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Xử lý sự kiện hiển thị/ẩn mật khẩu trường "New Password"
        showPasswordIconOff.setOnClickListener(v -> {
            newPasswordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            showPasswordIconOff.setVisibility(View.GONE);
            showPasswordIconOn.setVisibility(View.VISIBLE);
        });

        showPasswordIconOn.setOnClickListener(v -> {
            newPasswordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            showPasswordIconOn.setVisibility(View.GONE);
            showPasswordIconOff.setVisibility(View.VISIBLE);
        });

        // Xử lý sự kiện hiển thị/ẩn mật khẩu trường "Confirm New Password"
        showConfirmPasswordIconOff.setOnClickListener(v -> {
            confirmNewPasswordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            showConfirmPasswordIconOff.setVisibility(View.GONE);
            showConfirmPasswordIconOn.setVisibility(View.VISIBLE);
        });

        showConfirmPasswordIconOn.setOnClickListener(v -> {
            confirmNewPasswordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            showConfirmPasswordIconOn.setVisibility(View.GONE);
            showConfirmPasswordIconOff.setVisibility(View.VISIBLE);
        });

        // Sự kiện khi nhấn vào "Back to Sign In"
        TextView signInText = findViewById(R.id.sign_in_text);
        signInText.setOnClickListener(v -> {
            Intent intent = new Intent(ForgotPassword.this, SignIn.class);
            startActivity(intent);
            finish(); // Đóng ForgotPasswordActivity
        });

        // Sự kiện khi nhấn nút "RESET PASSWORD"
        resetPasswordButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String newPassword = newPasswordEditText.getText().toString().trim();
            String confirmNewPassword = confirmNewPasswordEditText.getText().toString().trim();

            // Kiểm tra các trường nhập liệu
            if (TextUtils.isEmpty(username)) {
                usernameEditText.setError("Username is required!");
                return;
            }

            if (TextUtils.isEmpty(email)) {
                emailEditText.setError("Email is required!");
                return;
            }

            if (TextUtils.isEmpty(newPassword)) {
                newPasswordEditText.setError("New password is required!");
                return;
            }

            if (TextUtils.isEmpty(confirmNewPassword)) {
                confirmNewPasswordEditText.setError("Confirm new password is required!");
                return;
            }

            if (!newPassword.equals(confirmNewPassword)) {
                confirmNewPasswordEditText.setError("Passwords do not match!");
                return;
            }

            // Kiểm tra thông tin đăng nhập từ Firebase để xác thực username và email
            databaseReference.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    boolean isUserValid = false;
                    String userIdToUpdate = "";

                    // Lặp qua các bản ghi trong Firebase để kiểm tra username và email
                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                        String dbUsername = snapshot.child("username").getValue(String.class);
                        String dbEmail = snapshot.child("email").getValue(String.class);

                        if (username.equals(dbUsername) && email.equals(dbEmail)) {
                            isUserValid = true;
                            userIdToUpdate = snapshot.getKey(); // Lấy key (ID) của người dùng
                            break;
                        }
                    }

                    if (isUserValid) {
                        // Cập nhật mật khẩu mới trong Firebase
                        databaseReference.child(userIdToUpdate).child("password").setValue(newPassword)
                                .addOnCompleteListener(updateTask -> {
                                    if (updateTask.isSuccessful()) {
                                        Toast.makeText(ForgotPassword.this, "Password reset successfully!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ForgotPassword.this, SignIn.class);
                                        startActivity(intent);
                                        finish(); // Đóng ForgotPasswordActivity
                                    } else {
                                        Toast.makeText(ForgotPassword.this, "Failed to reset password. Please try again.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(ForgotPassword.this, "Invalid username or email.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ForgotPassword.this, "Failed to connect to the database.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
