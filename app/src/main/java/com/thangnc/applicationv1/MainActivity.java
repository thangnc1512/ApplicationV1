package com.thangnc.applicationv1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView edtMaSP;
    private TextView edtTenSP;
    private Button btnAdd;
    private Button btnEdit;
    private Button btnDelete;
    private ListView lvListSP;
    private ProductDAO productDAO;
    private ProductAdapter adapter;
    private DatabaseHelper databaseHelperl;
    private List<Product> listProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listProduct = new ArrayList<>();
        databaseHelperl = new DatabaseHelper(this);
        productDAO = new ProductDAO(databaseHelperl);

        edtMaSP = (TextView) findViewById(R.id.edtMaSP);
        edtTenSP = (TextView) findViewById(R.id.edtTenSP);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        lvListSP = (ListView) findViewById(R.id.lvListSP);
        listProduct.addAll(productDAO.getAllProduct());
        adapter = new ProductAdapter(this, R.layout.item_product, listProduct, new onDeleteListener() {
            @Override
            public void onDeleteListener(final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete");
                builder.setMessage("Do U want to delete?????");
                builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        productDAO.deleteProduct(listProduct.get(position).getMaSP());
                        listProduct.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.show();

            }
        });
        lvListSP.setAdapter(adapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String productID = edtMaSP.getText().toString();
                final String productName = edtTenSP.getText().toString();
                if (productID.equals("") || productName.equals("")){
                    Toast.makeText(MainActivity.this, "not not", Toast.LENGTH_SHORT).show();
                }else {
                    Log.e("productID", productID);
                    Log.e("producName", productName);
                    Product product = new Product(productID, productName);
                    long id = productDAO.insertProduct(product);
                    if (id==-1){
                        Toast.makeText(MainActivity.this, "trung", Toast.LENGTH_SHORT).show();
                    }else {
                        listProduct.add(product);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Add success", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
        lvListSP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Edit");
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View view1 = inflater.inflate(R.layout.dialog_edit, null);
                final EditText edtEditID = view1.findViewById(R.id.edtEditID);
                final EditText edtEditName = view1.findViewById(R.id.edtEditName);
                edtEditID.setText(listProduct.get(position).getMaSP());
                edtEditName.setText(listProduct.get(position).getTenSP());
                builder.setView(view1);
                builder.setNegativeButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                        String editID = edtEditID.getText().toString();
                        String editName = edtEditName.getText().toString();
//                        if (editID.equals(""))
                        Product product = listProduct.get(position);
                        product.setMaSP(editID);
                        product.setTenSP(editName);
                        productDAO.updateProduct(product);
                        adapter.notifyDataSetChanged();

                    }
                });

                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                Toast.makeText(MainActivity.this, position+"", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
