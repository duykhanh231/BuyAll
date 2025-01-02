package com.group9.buyall;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private boolean isSubItemsVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        // Ánh xạ các TextViews
        TextView tvFullName = findViewById(R.id.sub_item_fullname);
        TextView tvPhone = findViewById(R.id.sub_item_phone);
        TextView tvEmail = findViewById(R.id.sub_item_email);

        // Lấy thông tin người dùng hiện tại
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Lấy dữ liệu từ Firebase Realtime Database
            fetchUserData(user.getUid(), tvFullName, tvPhone, tvEmail);
        } else {
            // Nếu không có người dùng hiện tại, hiển thị "N/A"
            tvFullName.setText("Fullname: N/A");
            tvPhone.setText("Phone: N/A");
            tvEmail.setText("Email: N/A");
        }

        // Xử lý sự kiện bật/tắt hiển thị thông tin chi tiết
        LinearLayout mainRow = findViewById(R.id.main_row);
        ImageView chevronIcon = findViewById(R.id.chevron_icon);
        LinearLayout subItemsContainer = findViewById(R.id.sub_items_container);

        mainRow.setOnClickListener(v -> toggleSubItems(subItemsContainer, chevronIcon));
        chevronIcon.setOnClickListener(v -> toggleSubItems(subItemsContainer, chevronIcon));
    }

    private void fetchUserData(String userId, TextView tvFullName, TextView tvPhone, TextView tvEmail) {
        // Truy cập đến Firebase Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Kiểm tra dữ liệu người dùng
                if (dataSnapshot.exists()) {
                    // Lấy thông tin người dùng từ Firebase
                    String fullName = dataSnapshot.child("fullname").getValue(String.class); // Chú ý tên trường 'fullname'
                    String phone = dataSnapshot.child("phoneNumber").getValue(String.class); // Chú ý tên trường 'phoneNumber'
                    String email = dataSnapshot.child("email").getValue(String.class);

                    // Nếu dữ liệu là null, thay bằng "N/A"
                    if (fullName == null) fullName = "N/A";
                    if (phone == null) phone = "N/A";
                    if (email == null) email = "N/A";

                    // Cập nhật giao diện với dữ liệu đã lấy được
                    tvFullName.setText("Fullname: " + fullName);
                    tvPhone.setText("Phone: " + phone);
                    tvEmail.setText("Email: " + email);

                    // Hiển thị thông báo thành công
                    Toast.makeText(ProfileActivity.this, "Data fetched successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // Nếu dữ liệu không tồn tại, hiển thị "N/A"
                    tvFullName.setText("Fullname: N/A");
                    tvPhone.setText("Phone: N/A");
                    tvEmail.setText("Email: N/A");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Nếu có lỗi khi truy xuất dữ liệu, thông báo lỗi
                tvFullName.setText("Fullname: Error loading");
                tvPhone.setText("Phone: Error loading");
                tvEmail.setText("Email: Error loading");
            }
        });
    }

    private void toggleSubItems(LinearLayout subItemsContainer, ImageView chevronIcon) {
        // Chuyển đổi trạng thái hiển thị thông tin chi tiết
        if (isSubItemsVisible) {
            subItemsContainer.setVisibility(View.GONE);
            chevronIcon.setImageResource(R.drawable.chevronright);
        } else {
            subItemsContainer.setVisibility(View.VISIBLE);
            chevronIcon.setImageResource(R.drawable.chevrondown);
        }
        isSubItemsVisible = !isSubItemsVisible;
    }
}
