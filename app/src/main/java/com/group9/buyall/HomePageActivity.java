package com.group9.buyall;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class HomePageActivity extends AppCompatActivity {

    private ImageView cartIcon;
    private TextView cartText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        cartIcon = findViewById(R.id.imageView11);
        cartText = findViewById(R.id.textView14);

        // Set up OnClickListener for cartIcon and cartText
        View.OnClickListener cartClickListener = v -> showCartFragment();
        cartIcon.setOnClickListener(cartClickListener);
        cartText.setOnClickListener(cartClickListener);
    }

    private void showCartFragment() {
        CartFragment cartFragment = new CartFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.cart_fragment_container, cartFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        findViewById(R.id.cart_fragment_container).setVisibility(View.VISIBLE);
    }
}
