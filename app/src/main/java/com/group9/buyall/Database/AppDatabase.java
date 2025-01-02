package com.group9.buyall.Database;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CartItemEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CartItemDao cartItemDao();
}