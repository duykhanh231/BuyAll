package com.group9.buyall;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        // Xử lý sự kiện nhấn vào TextView (Đăng xuất)
        TextView tvLogout = findViewById(R.id.tv_logout);
        tvLogout.setOnClickListener(v -> showLogoutConfirmationDialog());

        // Xử lý sự kiện nhấn vào ImageView (Biểu tượng Đăng xuất)
        ImageView imgLogoutIcon = findViewById(R.id.img_logout_icon);
        imgLogoutIcon.setOnClickListener(v -> showLogoutConfirmationDialog());
    }

    // Hàm hiển thị hộp thoại xác nhận đăng xuất
    private void showLogoutConfirmationDialog() {
        // Tạo hộp thoại xác nhận đăng xuất
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setMessage("Bạn có chắc chắn muốn đăng xuất?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Nếu chọn Yes, chuyển về trang đăng nhập
                        Intent intent = new Intent(ProfileActivity.this, SignIn.class);
                        startActivity(intent);
                        finish(); // Đóng ProfileActivity
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Nếu chọn No, chỉ đóng hộp thoại
                        dialog.dismiss();
                    }
                });

        // Hiển thị hộp thoại
        AlertDialog alert = builder.create();
        alert.show();
    }
}
