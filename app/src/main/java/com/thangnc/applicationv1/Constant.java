package com.thangnc.applicationv1;

public interface Constant {


    String TABLE_PRODUCT = "Product";
    String COLUMN_ID = "product_id";
    String COLUMN_NAME = "product_name";

    String CREATE_TABLE_PRODUCT = "CREATE TABLE "+TABLE_PRODUCT+"("
            + COLUMN_ID +" VARCHAR(10) PRIMARY KEY NOT NULL,"
            + COLUMN_NAME +" NVARHCAR(50) NOT NULL"
            + ")";

}
