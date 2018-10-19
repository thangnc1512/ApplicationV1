package com.thangnc.applicationv1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements Constant {
    DatabaseHelper databaseHelper;

    public ProductDAO(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public long insertProduct(Product product){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, product.getMaSP());
        contentValues.put(COLUMN_NAME, product.getTenSP());
        long id = sqLiteDatabase.insert(TABLE_PRODUCT, null, contentValues);
        sqLiteDatabase.close();
        return  id;

    }

    public long updateProduct(Product product){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, product.getMaSP());
        contentValues.put(COLUMN_NAME, product.getTenSP());
        long result = sqLiteDatabase.update(TABLE_PRODUCT, contentValues, COLUMN_ID + "=?",
                new String[]{String.valueOf(product.getMaSP())});
        return result;

    }

    public long deleteProduct(String productID){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        long result = sqLiteDatabase.delete(TABLE_PRODUCT, COLUMN_ID+"=?", new String[]{String.valueOf(productID)});
        return result;
    }

    public Product getProductByID(String productID){
        Product product = null;
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_PRODUCT, new String[]{COLUMN_ID, COLUMN_NAME},
                COLUMN_ID+"=?", new String[]{productID}, null, null, null);
        if (cursor != null && cursor.moveToFirst()){
            String productId = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
            String productName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            product = new Product(productId, productName);
        }
        return product;
    }
    public List<Product> getAllProduct(){
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        String SELECT_ALL_PRODUCT = "SELECT * FROM "+TABLE_PRODUCT;
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL_PRODUCT, null);
        if (cursor.moveToFirst()){
            do {
                String productId = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
                String productName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                Product product = new Product(productId, productName);
                productList.add(product);
            } while (cursor.moveToNext());
                cursor.close();
                sqLiteDatabase.close();
        }
        return productList;
    }

}
