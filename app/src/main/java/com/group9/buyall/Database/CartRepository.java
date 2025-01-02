package com.group9.buyall.Database;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import java.util.List;
import androidx.room.Room;

public class CartRepository {
    private CartItemDao cartItemDao;
    private LiveData<List<CartItemEntity>> allCartItems;

    public CartRepository(Application application) {
        AppDatabase db = Room.databaseBuilder(application, AppDatabase.class, "cart-db").build();
        cartItemDao = db.cartItemDao();
        allCartItems = cartItemDao.getCartItems();
    }

    public void insert(CartItemEntity item) {
        new InsertAsyncTask(cartItemDao).execute(item);
    }

    public void update(CartItemEntity item) {
        new UpdateAsyncTask(cartItemDao).execute(item);
    }

    public void delete(CartItemEntity item) {
        new DeleteAsyncTask(cartItemDao).execute(item);
    }

    public LiveData<List<CartItemEntity>> getAllCartItems() {
        return allCartItems;
    }

    private static class InsertAsyncTask extends AsyncTask<CartItemEntity, Void, Void> {
        private CartItemDao asyncTaskDao;

        InsertAsyncTask(CartItemDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CartItemEntity... params) {
            asyncTaskDao.addItem(params[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<CartItemEntity, Void, Void> {
        private CartItemDao asyncTaskDao;

        UpdateAsyncTask(CartItemDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CartItemEntity... params) {
            asyncTaskDao.updateItem(params[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<CartItemEntity, Void, Void> {
        private CartItemDao asyncTaskDao;

        DeleteAsyncTask(CartItemDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CartItemEntity... params) {
            asyncTaskDao.removeItem(params[0]);
            return null;
        }
    }
}
