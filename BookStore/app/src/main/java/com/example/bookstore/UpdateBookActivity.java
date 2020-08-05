package com.example.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateBookActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText editID, editName, editAuthor, editLoca, editprice;
    Button btnQuery, btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);
        db = new DatabaseHelper(this);

        editID = findViewById(R.id.etUID);
        editName = findViewById(R.id.etUBookName);
        editAuthor = findViewById(R.id.etUAuthor);
        editLoca = findViewById(R.id.etULoca);
        editprice = findViewById(R.id.etUPrice);
        btnQuery = findViewById(R.id.btnUQuery);
        btnUpdate = findViewById(R.id.btnUUpdate);

        queryAllInfo();
        updateData();
    }

    private void updateData() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editID.getText().toString().trim();
                String name = editName.getText().toString().trim();
                String author = editAuthor.getText().toString().trim();
                String loca = editLoca.getText().toString().trim();
                String price = editprice.getText().toString().trim();
                if(id.isEmpty() || name.isEmpty() || author.isEmpty() || loca.isEmpty() || price.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please fill in all info.",Toast.LENGTH_LONG).show();
                    return;
                }

                boolean flag = db.updateBook(id, name, author, loca, price);
                if(flag){
                    Toast.makeText(getApplicationContext(),"Update book info. successfully!",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Error to update book info.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void queryAllInfo() {
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editID.getText().toString().trim();
                if(id.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please fill in the ID.",Toast.LENGTH_LONG).show();
                    return;
                }
                if(db.countItem(id) < 1){
                    editName.setText("");
                    editAuthor.setText("");
                    editLoca.setText("");
                    editprice.setText("");
                    Toast.makeText(getApplicationContext(), "No such item, please check!",Toast.LENGTH_LONG).show();

                }
                else{
                    //Toast.makeText(getApplicationContext(), String.valueOf(db.countItem(id)),Toast.LENGTH_LONG).show();
                    editName.setText(db.findBookName(id));
                    editAuthor.setText(db.findBookAuthor(id));
                    editLoca.setText(db.findBookLocation(id));
                    editprice.setText(db.findBookPrice(id));
                }



            }
        });
    }
}
