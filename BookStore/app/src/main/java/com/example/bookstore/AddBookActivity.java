package com.example.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.KeyStore;
import java.security.acl.LastOwnerException;

public class AddBookActivity extends AppCompatActivity {
    DatabaseHelper db; //database

    EditText editName, editAuthor, editLocation, editPrice;
    Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        db = new DatabaseHelper(this);

        //link with the widgets

        editName = findViewById(R.id.etAName);
        editAuthor = findViewById(R.id.etAAuthor);
        editLocation = findViewById(R.id.etALoc);
        editPrice = findViewById(R.id.etAPrice);
        buttonAdd = findViewById(R.id.btnAAdd);
        //add book method
        addBook();
    }

    private void addBook() {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //grab input from edit text
                String name = editName.getText().toString().trim();
                String author = editAuthor.getText().toString().trim();
                String loca = editLocation.getText().toString().trim();
                String price = editPrice.getText().toString().trim();

                //determine input is empty or not
                if(name.isEmpty() || author.isEmpty() || loca.isEmpty() || price.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please fill in all information.",Toast.LENGTH_LONG).show();
                    return;
                }
                boolean flag = db.insertBook(name,author,loca,price); //insert
                if(flag){
                    Toast.makeText(getApplicationContext(),"Add book successfully!",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Error to add book, please double check.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
