package com.group9.buyall;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.group9.buyall.Database.CartItemEntity;
import com.group9.buyall.Database.CartRepository;
import java.util.List;

public class CartViewModel extends AndroidViewModel {
    private CartRepository repository;
    private LiveData<List<CartItemEntity>> allCartItems;

    public CartViewModel(Application application) {
        super(application);
        repository = new CartRepository(application);
        allCartItems = repository.getAllCartItems();
    }

    public void insert(CartItemEntity item) {
        repository.insert(item);
    }

    public void update(CartItemEntity item) {
        repository.update(item);
    }

    public void delete(CartItemEntity item) {
        repository.delete(item);
    }

    public LiveData<List<CartItemEntity>> getAllCartItems() {
        return allCartItems;
    }
}