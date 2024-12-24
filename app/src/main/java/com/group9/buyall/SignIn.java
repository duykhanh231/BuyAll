package com.group9.buyall;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class SignIn extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private EditText passwordEditText;
    private ImageView showPasswordIconOff, showPasswordIconOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.signin);

        // Khởi tạo Firebase Database Reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Ánh xạ các trường giao diện
        EditText usernameOrEmailEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        Button logInButton = findViewById(R.id.login_button);
        CheckBox rememberPasswordCheckBox = findViewById(R.id.remember_password); // CheckBox Remember Password
        TextView forgotPasswordText = findViewById(R.id.forgot_password);
        TextView signUpText = findViewById(R.id.sign_up_text);

        // Các biểu tượng mắt (ẩn/mở mật khẩu)
        showPasswordIconOff = findViewById(R.id.show_password_icon_off);
        showPasswordIconOn = findViewById(R.id.show_password_icon_on);

        // Sự kiện khi nhấn vào mắt đóng (hiện mật khẩu)
        showPasswordIconOff.setOnClickListener(v -> {
            // Chuyển đổi input type để hiển thị mật khẩu
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            passwordEditText.setSelection(passwordEditText.getText().length()); // Đặt con trỏ vào cuối
            showPasswordIconOff.setVisibility(View.GONE);  // Ẩn biểu tượng mắt đóng
            showPasswordIconOn.setVisibility(View.VISIBLE); // Hiển thị biểu tượng mắt mở
        });

        // Sự kiện khi nhấn vào mắt mở (ẩn mật khẩu)
        showPasswordIconOn.setOnClickListener(v -> {
            // Chuyển đổi input type để ẩn mật khẩu
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            passwordEditText.setSelection(passwordEditText.getText().length()); // Đặt con trỏ vào cuối
            showPasswordIconOn.setVisibility(View.GONE);  // Ẩn biểu tượng mắt mở
            showPasswordIconOff.setVisibility(View.VISIBLE); // Hiển thị biểu tượng mắt đóng
        });

        // Lấy thông tin đã lưu trong SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        // Khi người dùng nhập username, kiểm tra mật khẩu đã lưu
        usernameOrEmailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String usernameOrEmail = charSequence.toString().trim();
                if (!TextUtils.isEmpty(usernameOrEmail)) {
                    // Kiểm tra trong SharedPreferences xem username này đã có mật khẩu lưu chưa
                    String savedPassword = sharedPreferences.getString(usernameOrEmail, "");
                    if (!TextUtils.isEmpty(savedPassword)) {
                        // Nếu có mật khẩu, gợi ý mật khẩu vào trường password
                        passwordEditText.setText(savedPassword);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // Sự kiện "Forgot password?"
        forgotPasswordText.setOnClickListener(v -> {
            Intent intent = new Intent(SignIn.this, ForgotPassword.class);
            startActivity(intent);
        });

        // Sự kiện "Don't have an account? Sign up"
        signUpText.setOnClickListener(v -> {
            Log.d("SignInMainActivity", "Sign up text clicked");
            Intent intent = new Intent(SignIn.this, SignUp.class);
            startActivity(intent);
        });

        // Sự kiện nút Login
        logInButton.setOnClickListener(v -> {
            String usernameOrEmail = usernameOrEmailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            // Kiểm tra các trường nhập liệu
            if (TextUtils.isEmpty(usernameOrEmail)) {
                usernameOrEmailEditText.setError("Username or Email is required!");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                passwordEditText.setError("Password is required!");
                return;
            }

            // Kiểm tra thông tin đăng nhập từ Firebase
            databaseReference.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    boolean isAuthenticated = false;

                    // Lặp qua các bản ghi trong Firebase
                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                        String dbUsername = snapshot.child("username").getValue(String.class);
                        String dbEmail = snapshot.child("email").getValue(String.class);
                        String dbPassword = snapshot.child("password").getValue(String.class);

                        if ((usernameOrEmail.equals(dbUsername) || usernameOrEmail.equals(dbEmail)) &&
                                password.equals(dbPassword)) {
                            isAuthenticated = true;
                            break;
                        }
                    }

                    // Kiểm tra nếu đăng nhập thành công
                    if (isAuthenticated) {
                        // Nếu "Remember Password" được chọn, lưu thông tin đăng nhập
                        if (rememberPasswordCheckBox.isChecked()) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(usernameOrEmail, password);  // Lưu mật khẩu theo username
                            editor.apply();
                        }

                        Toast.makeText(SignIn.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignIn.this, HomePageActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SignIn.this, "Invalid username/email or password.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignIn.this, "Failed to connect to the database.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
