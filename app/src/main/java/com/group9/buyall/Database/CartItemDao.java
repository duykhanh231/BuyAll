package com.group9.buyall.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CartItemDao {
    @Insert
    void addItem(CartItemEntity item);

    @Update
    void updateItem(CartItemEntity item);

    @Delete
    void removeItem(CartItemEntity item);

    @Query("SELECT * FROM cart_items")
    LiveData<List<CartItemEntity>> getCartItems();
}
